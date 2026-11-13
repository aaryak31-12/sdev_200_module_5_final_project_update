
import java.time.LocalDate;

// Tests methods inherited from Transaction
public class TransactionTest {

    public static void main(String[] args) {

        // Abstract Transaction cannot be created directly so we create an expense and store it as transaction
        Transaction transaction = new Expense(
                1, 20.00, LocalDate.of(2026, 11, 12),
                "Lunch", "Food", "Cash", "Cafe", false);

        check("Transaction ID", transaction.getTransactionID() == 1);
        check("Amount", transaction.getAmount() == 20.00);
        check("Date", transaction.getDate().equals(
                LocalDate.of(2026, 11, 12)));
        check("Description", transaction.getDescription().equals("Lunch"));
        check("Category", transaction.getCategory().equals("Food"));

        // testing setters
        transaction.setAmount(25.00);
        transaction.setDescription("Dinner");
        transaction.setCategory("Restaurant");
        transaction.setDate(LocalDate.of(2026, 11, 13));

        check("Set amount", transaction.getAmount() == 25.00);
        check("Set description", transaction.getDescription().equals("Dinner"));
        check("Set category", transaction.getCategory().equals("Restaurant"));
        check("Set date", transaction.getDate().equals(
                LocalDate.of(2026, 11, 13)));

        check("Transaction type",
                transaction.getTransactionType().equals("Expense"));
        check("toString", transaction.toString().contains("Dinner"));

        // testing error handling
        try {
            transaction.setAmount(-5);
            check("Reject negative amount", false);
        } catch (IllegalArgumentException e) {
            check("Reject negative amount", true);
        }

        try {
            transaction.setTransactionID(0);
            check("Reject invalid ID", false);
        } catch (IllegalArgumentException e) {
            check("Reject invalid ID", true);
        }

        try {
            transaction.setDate(null);
            check("Reject null date", false);
        } catch (IllegalArgumentException e) {
            check("Reject null date", true);
        }

        try {
            transaction.setDescription(" ");
            check("Reject empty description", false);
        } catch (IllegalArgumentException e) {
            check("Reject empty description", true);
        }

        try {
            transaction.setCategory("");
            check("Reject empty category", false);
        } catch (IllegalArgumentException e) {
            check("Reject empty category", true);
        }
    }

    // printing the test result
    public static void check(String name, boolean passed) {
        if (passed) {
            System.out.println("PASS: " + name);
        } else {
            System.out.println("FAIL: " + name);
        }
    }
}
