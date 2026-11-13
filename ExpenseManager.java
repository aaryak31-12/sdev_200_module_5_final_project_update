
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

// This class manages the list of all transactions
public class ExpenseManager {

    private ArrayList<Transaction> transactions;
    private String fileName;

    // Constructor
    public ExpenseManager(String fileName) {
        transactions = new ArrayList<Transaction>();
        setFileName(fileName);
    }

    // Returns a copy of the transaction list
    public ArrayList<Transaction> getTransactions() {
        return new ArrayList<Transaction>(transactions);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid file name");
        }
        this.fileName = fileName;
    }

    // Adds a new transaction
    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        // Do not allow duplicate IDs
        if (findTransaction(transaction.getTransactionID()) != null) {
            throw new IllegalArgumentException("Duplicate transaction ID");
        }

        transactions.add(transaction);
    }

    // Updates an existing transaction
    public boolean updateTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getTransactionID()
                    == transaction.getTransactionID()) {
                transactions.set(i, transaction);
                return true;
            }
        }
        return false;
    }

    // Removes a transaction using its ID
    public boolean removeTransaction(int transactionID) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getTransactionID() == transactionID) {
                transactions.remove(i);
                return true;
            }
        }
        return false;
    }

    // Searches for a transaction using its ID
    public Transaction findTransaction(int transactionID) {
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionID() == transactionID) {
                return transaction;
            }
        }
        return null;
    }

    // Calculates total expenses
    public double getTotalExpenses() {
        double total = 0;

        for (Transaction transaction : transactions) {
            if (transaction instanceof Expense) {
                total = total + transaction.getAmount();
            }
        }
        return total;
    }

    // Calculates total income
    public double getTotalIncome() {
        double total = 0;

        for (Transaction transaction : transactions) {
            if (transaction instanceof Income) {
                total = total + transaction.getAmount();
            }
        }
        return total;
    }

    // Saves all transactions to a text file
    public void saveTransactions() throws IOException {

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

            for (Transaction transaction : transactions) {

                // Saves the common transaction information
                writer.println(transaction.getTransactionType());
                writer.println(transaction.getTransactionID());
                writer.println(transaction.getAmount());
                writer.println(transaction.getDate());
                writer.println(transaction.getDescription());
                writer.println(transaction.getCategory());

                // Saves additional expense information
                if (transaction instanceof Expense) {
                    Expense expense = (Expense) transaction;

                    writer.println(expense.getPaymentMethod());
                    writer.println(expense.getMerchantName());
                    writer.println(expense.isRecurring());
                }

                // Saves additional income information
                else if (transaction instanceof Income) {
                    Income income = (Income) transaction;

                    writer.println(income.getSource());
                    writer.println(income.getIncomeType());
                }
            }

            if (writer.checkError()) {
                throw new IOException("Could not save transactions");
            }
        }
    }

    // Loads transactions from the saved text file
    public void loadTransactions() throws IOException {

        ArrayList<Transaction> loaded = new ArrayList<Transaction>();
        File file = new File(fileName);

        // If file does not exist, start with an empty list
        if (!file.exists()) {
            transactions = loaded;
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String type;

            while ((type = reader.readLine()) != null) {

                // Read common fields
                int id = Integer.parseInt(readSavedLine(reader));
                double amount = Double.parseDouble(readSavedLine(reader));
                LocalDate date = LocalDate.parse(readSavedLine(reader));
                String description = readSavedLine(reader);
                String category = readSavedLine(reader);

                Transaction transaction;

                if (type.equals("Expense")) {

                    String paymentMethod = readSavedLine(reader);
                    String merchantName = readSavedLine(reader);
                    String recurringText = readSavedLine(reader);

                    if (!recurringText.equals("true")
                            && !recurringText.equals("false")) {
                        throw new IllegalArgumentException("Invalid recurring value");
                    }

                    boolean recurring = Boolean.parseBoolean(recurringText);

                    transaction = new Expense(id, amount, date,
                            description, category, paymentMethod,
                            merchantName, recurring);

                } else if (type.equals("Income")) {

                    String source = readSavedLine(reader);
                    String incomeType = readSavedLine(reader);

                    transaction = new Income(id, amount, date,
                            description, category, source, incomeType);

                } else {
                    throw new IllegalArgumentException("Unknown transaction type");
                }

                // Prevent duplicate IDs in saved data
                for (Transaction old : loaded) {
                    if (old.getTransactionID() == transaction.getTransactionID()) {
                        throw new IllegalArgumentException("Duplicate saved ID");
                    }
                }

                loaded.add(transaction);
            }

        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Invalid saved data: " + exception.getMessage(),
                    exception);
        }

        // Replaces the old list with loaded transactions
        transactions = loaded;
    }

    // Reads one line from the saved file
    private String readSavedLine(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        if (line == null) {
            throw new IllegalArgumentException("Incomplete saved file");
        }

        return line;
    }
}
