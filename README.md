# Expense Tracker Application 💰

A Java-based desktop application for tracking personal expenses with an intuitive GUI. Manage your spending with categories, descriptions, and automatic total calculations, all stored in a local SQLite database.

## ✨ Features

- **Add Expenses** - Record expenses with category, amount, and description
- **Update & Delete** - Edit or remove existing expense entries
- **Category Management** - Predefined categories (Food, Transport, Entertainment, Utilities, Healthcare, Other)
- **Real-Time Totals** - Automatic calculation of total expenses
- **Data Validation** - Input validation to ensure data integrity
- **Persistent Storage** - SQLite database for reliable data storage
- **User-Friendly Interface** - Clean Swing-based GUI with table view
- **Date Tracking** - Automatic date recording for each expense

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- SQLite JDBC Driver (included in most Java distributions or downloadable)

### Installation

1. Clone the repository
2. Open the project in IDE
3. Add the SQLite JDBC JAR to the project libraries
4. Run `ExpenseTrackerGUI.java`

**Note:** The SQLite database file (`expenses.db`) will be created automatically in the same directory on first run.

### Adding SQLite JDBC Driver (if needed)
If SQLite JDBC is not included, download it from [SQLite JDBC](https://github.com/xerial/sqlite-jdbc) and add to classpath:

##  How to Use

### Adding an Expense
1. Select a category from the dropdown menu
2. Enter the amount (must be greater than $0, max $1,000,000)
3. Add an optional description (max 200 characters)
4. Click "Add Expense"

### Updating an Expense
1. Click on an expense in the table to select it
2. Modify the fields in the form
3. Click "Update Selected"

### Deleting an Expense
1. Select an expense from the table
2. Click "Delete Selected"
3. Confirm the deletion

### Other Actions
- **Clear Fields** - Reset the form without affecting the database
- **Total Display** - View cumulative expenses at the bottom of the window

##  Technical Details

### Architecture

**Design Patterns**
- **Singleton Pattern** - DatabaseManager ensures single database connection
- **MVC-inspired Structure** - Separation of data model, business logic, and UI

**Project Structure**
```
expense-tracker/
│
├── Expense.java              # Data model for expense entries
├── ExpenseTrackerGUI.java    # Main GUI and application logic
├── DatabaseManager.java      # Database operations and connection
├── ExpenseValidator.java     # Input validation logic
├── ValidationException.java  # Custom exception for validation errors
└── expenses.db               # SQLite database (auto-generated)
```

### Technologies
- **Java Swing** - GUI framework for desktop interface
- **SQLite** - Lightweight embedded database
- **JDBC** - Database connectivity
- **Java Time API** - Date handling with LocalDate

### Database Schema

**expenses table**
| Column      | Type    | Description                    |
|-------------|---------|--------------------------------|
| id          | INTEGER | Primary key (auto-increment)   |
| category    | TEXT    | Expense category               |
| amount      | REAL    | Expense amount in dollars      |
| description | TEXT    | Optional expense description   |
| date        | TEXT    | Date in ISO format (YYYY-MM-DD)|

### Validation Rules
- **Category**: Cannot be empty
- **Amount**: Must be numeric, greater than $0, max $1,000,000
- **Description**: Optional, max 200 characters

## 🔮 Future Enhancements

- [ ] Date range filtering for expense reports
- [ ] Export data to CSV/PDF
- [ ] Budget tracking and alerts
- [ ] Expense charts and visualizations
- [ ] Multiple currency support
- [ ] Custom category creation
- [ ] Search and filter functionality
- [ ] Recurring expense templates
- [ ] Dark mode theme

## 🤝 Contributing

Contributions are welcome! Feel free to:
- Report bugs or issues
- Suggest new features
- Submit pull requests
- Improve documentation

**Created by Alex** | Java Swing Desktop Application
