
import java.time.LocalDate;

// abstract parent class for all transactions
public abstract class Transaction {

    private int transactionID;
    private double amount;
    private LocalDate date;
    private String description;
    private String category;

    // Constructor
    public Transaction(int transactionID, double amount,
                       LocalDate date, String description,
                       String category) {
        setTransactionID(transactionID);
        setAmount(amount);
        setDate(date);
        setDescription(description);
        setCategory(category);
    }

    //getters and setters
    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        if (transactionID <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        this.transactionID = transactionID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount <= 0 || Double.isNaN(amount)
                || Double.isInfinite(amount)) {
            throw new IllegalArgumentException("Invalid amount");
        }
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Invalid date");
        }
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null ||
            description.trim().isEmpty() ||
            description.contains("\n") ||
            description.contains("\r")) {
            throw new IllegalArgumentException("Invalid description");
        }
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (category == null ||
            category.trim().isEmpty() ||
            category.contains("\n") ||
            category.contains("\r")) {
            throw new IllegalArgumentException("Invalid category");
        }
        this.category = category;
    }

    // child classes should provide this method
    public abstract String getTransactionType();

    @Override
    public String toString() {
        return "Type: " + getTransactionType()
                + ", ID: " + transactionID
                + ", Amount: " + amount
                + ", Date: " + date
                + ", Description: " + description
                + ", Category: " + category;
    }
}
