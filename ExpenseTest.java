
import java.time.LocalDate;

// Tests the Expense class
public class ExpenseTest {

    public static void main(String[] args) {

        Expense expense = new Expense(
                2, 45.00, LocalDate.of(2026, 11, 12),
                "Groceries", "Food", "Debit Card",
                "Walmart", false);

        // Test getters and inheritance
        check("Expense ID", expense.getTransactionID() == 2);
        check("Payment method",
                expense.getPaymentMethod().equals("Debit Card"));
        check("Merchant", expense.getMerchantName().equals("Walmart"));
        check("Recurring", !expense.isRecurring());
        check("Expense type",
                expense.getTransactionType().equals("Expense"));

        // Test setters
        expense.setPaymentMethod("Cash");
        expense.setMerchantName("Local Store");
        expense.setRecurring(true);

        check("Set payment method",
                expense.getPaymentMethod().equals("Cash"));
        check("Set merchant",
                expense.getMerchantName().equals("Local Store"));
        check("Set recurring", expense.isRecurring());

        check("toString", expense.toString().contains("Local Store"));

        // Merchant name is allowed to be blank
        expense.setMerchantName("");
        check("Blank merchant allowed",
                expense.getMerchantName().equals(""));

        // Test invalid payment method
        try {
            expense.setPaymentMethod(" ");
            check("Reject blank payment method", false);
        } catch (IllegalArgumentException e) {
            check("Reject blank payment method", true);
        }

        // Test invalid merchant
        try {
            expense.setMerchantName(null);
            check("Reject null merchant", false);
        } catch (IllegalArgumentException e) {
            check("Reject null merchant", true);
        }
    }

    public static void check(String name, boolean passed) {
        if (passed) {
            System.out.println("PASS: " + name);
        } else {
            System.out.println("FAIL: " + name);
        }
    }
}
