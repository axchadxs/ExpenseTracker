public class ExpenseValidator {
    
    public static void validateExpense(String category, String amount, String description) 
            throws ValidationException {
        if (category == null || category.trim().isEmpty()) {
            throw new ValidationException("Category cannot be empty");
        }
        
        if (amount == null || amount.trim().isEmpty()) {
            throw new ValidationException("Amount cannot be empty");
        }
        
        try {
            double amountValue = Double.parseDouble(amount);
            if (amountValue <= 0) {
                throw new ValidationException("Amount must be greater than 0");
            }
            if (amountValue > 1000000) {
                throw new ValidationException("Amount cannot exceed $1,000,000");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Amount must be a valid number");
        }
        
        if (description != null && description.length() > 200) {
            throw new ValidationException("Description cannot exceed 200 characters");
        }
    }
}


