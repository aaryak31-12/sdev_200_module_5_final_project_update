
import java.time.LocalDate;

// Expense inherits common fields from Transaction
public class Expense extends Transaction {

    private String paymentMethod;
    private String merchantName;
    private boolean recurring;

    // Constructor
    public Expense(int transactionID, double amount,
                   LocalDate date, String description,
                   String category, String paymentMethod,
                   String merchantName, boolean recurring) {

        // Calls constructor of parent class
        super(transactionID, amount, date, description, category);

        setPaymentMethod(paymentMethod);
        setMerchantName(merchantName);
        setRecurring(recurring);
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        if (paymentMethod == null ||
            paymentMethod.trim().isEmpty() ||
            paymentMethod.contains("\n") ||
            paymentMethod.contains("\r")) {
            throw new IllegalArgumentException("Invalid payment method");
        }
        this.paymentMethod = paymentMethod;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        if (merchantName == null ||
            merchantName.contains("\n") ||
            merchantName.contains("\r")) {
            throw new IllegalArgumentException("Invalid merchant name");
        }
        this.merchantName = merchantName;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    // Returns type of transaction
    @Override
    public String getTransactionType() {
        return "Expense";
    }

    // Displays expense information
    @Override
    public String toString() {
        return super.toString()
                + ", Payment Method: " + paymentMethod
                + ", Merchant: " + merchantName
                + ", Recurring: " + recurring;
    }
}
