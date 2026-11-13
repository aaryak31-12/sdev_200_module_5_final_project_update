
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

// Tests the ExpenseManager class
public class ExpenseManagerTest {

    public static void main(String[] args) {

        File testFile = null;

        try {
            // Create a temporary test file
            testFile = File.createTempFile("expense_test_", ".txt");

            ExpenseManager manager =
                    new ExpenseManager(testFile.getAbsolutePath());

            // Create example transactions
            Expense expense = new Expense(
                    1, 20.00, LocalDate.of(2026, 11, 12),
                    "Lunch", "Food", "Cash", "Cafe", false);

            Income income = new Income(
                    2, 100.00, LocalDate.of(2026, 11, 12),
                    "Pay", "Work", "Employer", "Salary");

            // Test adding
            manager.addTransaction(expense);
            manager.addTransaction(income);

            check("Add transactions",
                    manager.getTransactions().size() == 2);

            // Test searching
            check("Find expense",
                    manager.findTransaction(1) == expense);
            check("Find missing transaction",
                    manager.findTransaction(99) == null);

            // Test calculations
            check("Total expenses",
                    manager.getTotalExpenses() == 20.00);
            check("Total income",
                    manager.getTotalIncome() == 100.00);

            // Test the file name
            check("File name",
                    manager.getFileName().equals(
                            testFile.getAbsolutePath()));

            // Test that the returned list is a copy
            ArrayList<Transaction> copy = manager.getTransactions();
            copy.clear();

            check("Original list is protected",
                    manager.getTransactions().size() == 2);

            // Test duplicate ID validation
            try {
                manager.addTransaction(expense);
                check("Reject duplicate ID", false);
            } catch (IllegalArgumentException e) {
                check("Reject duplicate ID", true);
            }

            // Test null validation
            try {
                manager.addTransaction(null);
                check("Reject null transaction", false);
            } catch (IllegalArgumentException e) {
                check("Reject null transaction", true);
            }

            // Test editing an expense
            Expense updated = new Expense(
                    1, 30.00, LocalDate.of(2026, 11, 12),
                    "Dinner", "Food", "Card", "Restaurant", false);

            check("Update transaction",
                    manager.updateTransaction(updated));

            check("Updated total",
                    manager.getTotalExpenses() == 30.00);

            // Test updating a nonexistent transaction
            Expense missing = new Expense(
                    99, 5.00, LocalDate.of(2026, 11, 12),
                    "Other", "Other", "Cash", "", false);

            check("Update missing transaction",
                    !manager.updateTransaction(missing));

            // Test saving data
            manager.saveTransactions();

            // Create another manager and load the saved data
            ExpenseManager loaded =
                    new ExpenseManager(testFile.getAbsolutePath());

            loaded.loadTransactions();

            check("Load saved transactions",
                    loaded.getTransactions().size() == 2);
            check("Load expense",
                    loaded.findTransaction(1) instanceof Expense);
            check("Load income",
                    loaded.findTransaction(2) instanceof Income);
            check("Loaded expense total",
                    loaded.getTotalExpenses() == 30.00);
            check("Loaded income total",
                    loaded.getTotalIncome() == 100.00);

            // Loading twice should not duplicate records
            loaded.loadTransactions();

            check("No duplicate records after loading",
                    loaded.getTransactions().size() == 2);

            // Test removal
            check("Remove expense",
                    loaded.removeTransaction(1));
            check("Expense removed",
                    loaded.findTransaction(1) == null);

            check("Remove nonexistent transaction",
                    !loaded.removeTransaction(99));

            // Test invalid file name
            try {
                new ExpenseManager(" ");
                check("Reject blank file name", false);
            } catch (IllegalArgumentException e) {
                check("Reject blank file name", true);
            }

            // Test what happens when no saved file exists
            if (testFile.delete()) {
                loaded.loadTransactions();

                check("Missing file starts empty",
                        loaded.getTransactions().isEmpty());
            } else {
                System.out.println("SKIP: Could not delete temporary file");
            }

        } catch (IOException e) {
            System.out.println("FAIL: File test - " + e.getMessage());

        } finally {
            // Remove temporary file after testing
            if (testFile != null) {
                testFile.delete();
            }
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
