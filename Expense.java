import java.time.LocalDate;

public class Expense {
    private int id;
    private String category;
    private double amount;
    private String description;
    private LocalDate date;

    public Expense(int id, String category, double amount, String description, LocalDate date) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Expense(String category, double amount, String description, LocalDate date) {
        this(-1, category, amount, description, date);
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | $%.2f | %s | %s", 
            id, category, amount, description, date);
    }
}