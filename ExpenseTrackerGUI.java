import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class ExpenseTrackerGUI extends JFrame {
    private DatabaseManager dbManager;
    private JTable expenseTable;
    private DefaultTableModel tableModel;
    private JTextField categoryField, amountField, descriptionField;
    private JLabel totalLabel;
    private String[] categories = {"Food", "Transport", "Entertainment", "Utilities", "Healthcare", "Other"};

    public ExpenseTrackerGUI() {
        dbManager = DatabaseManager.getInstance();
        initializeUI();
        loadExpenses();
    }

    private void initializeUI() {
        setTitle("Expense Tracker Application");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Input Panel
        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Add/Update Expense"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Category
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        categoryField = new JTextField(15);
        categoryCombo.addActionListener(e -> 
            categoryField.setText((String) categoryCombo.getSelectedItem()));
        categoryField.setText(categories[0]);
        panel.add(categoryCombo, gbc);

        // Amount
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 3;
        amountField = new JTextField(10);
        panel.add(amountField, gbc);

        // Description
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        descriptionField = new JTextField(30);
        panel.add(descriptionField, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(e -> addExpense());
        panel.add(addButton, gbc);

        gbc.gridx = 1;
        JButton updateButton = new JButton("Update Selected");
        updateButton.addActionListener(e -> updateExpense());
        panel.add(updateButton, gbc);

        gbc.gridx = 2;
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(e -> deleteExpense());
        panel.add(deleteButton, gbc);

        gbc.gridx = 3;
        JButton clearButton = new JButton("Clear Fields");
        clearButton.addActionListener(e -> clearFields());
        panel.add(clearButton, gbc);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Expense List"));

        String[] columns = {"ID", "Category", "Amount", "Description", "Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        expenseTable = new JTable(tableModel);
        expenseTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && expenseTable.getSelectedRow() != -1) {
                loadSelectedExpense();
            }
        });

        JScrollPane scrollPane = new JScrollPane(expenseTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Total Expenses: $0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(totalLabel);
        return panel;
    }

    private void addExpense() {
        try {
            ExpenseValidator.validateExpense(
                categoryField.getText(),
                amountField.getText(),
                descriptionField.getText()
            );

            Expense expense = new Expense(
                categoryField.getText().trim(),
                Double.parseDouble(amountField.getText().trim()),
                descriptionField.getText().trim(),
                LocalDate.now()
            );

            dbManager.addExpense(expense);
            JOptionPane.showMessageDialog(this, "Expense added successfully!");
            clearFields();
            loadExpenses();
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateExpense() {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an expense to update");
            return;
        }

        try {
            ExpenseValidator.validateExpense(
                categoryField.getText(),
                amountField.getText(),
                descriptionField.getText()
            );

            int id = (int) tableModel.getValueAt(selectedRow, 0);
            Expense expense = new Expense(
                id,
                categoryField.getText().trim(),
                Double.parseDouble(amountField.getText().trim()),
                descriptionField.getText().trim(),
                LocalDate.parse((String) tableModel.getValueAt(selectedRow, 4))
            );

            dbManager.updateExpense(expense);
            JOptionPane.showMessageDialog(this, "Expense updated successfully!");
            clearFields();
            loadExpenses();
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteExpense() {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an expense to delete");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this expense?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                dbManager.deleteExpense(id);
                JOptionPane.showMessageDialog(this, "Expense deleted successfully!");
                clearFields();
                loadExpenses();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadExpenses() {
        try {
            tableModel.setRowCount(0);
            for (Expense expense : dbManager.getAllExpenses()) {
                tableModel.addRow(new Object[]{
                    expense.getId(),
                    expense.getCategory(),
                    String.format("$%.2f", expense.getAmount()),
                    expense.getDescription(),
                    expense.getDate()
                });
            }
            updateTotal();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading expenses: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSelectedExpense() {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow != -1) {
            categoryField.setText((String) tableModel.getValueAt(selectedRow, 1));
            String amount = (String) tableModel.getValueAt(selectedRow, 2);
            amountField.setText(amount.replace("$", ""));
            descriptionField.setText((String) tableModel.getValueAt(selectedRow, 3));
        }
    }

    private void updateTotal() {
        try {
            double total = dbManager.getTotalExpenses();
            totalLabel.setText(String.format("Total Expenses: $%.2f", total));
        } catch (SQLException e) {
            totalLabel.setText("Total Expenses: Error");
        }
    }

    private void clearFields() {
        categoryField.setText(categories[0]);
        amountField.setText("");
        descriptionField.setText("");
        expenseTable.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new ExpenseTrackerGUI().setVisible(true);
        });
    }
}