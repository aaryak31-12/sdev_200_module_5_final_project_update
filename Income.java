
import java.time.LocalDate;

// Income also inherits from Transaction
public class Income extends Transaction {

    private String source;
    private String incomeType;

    // Constructor
    public Income(int transactionID, double amount,
                  LocalDate date, String description,
                  String category, String source,
                  String incomeType) {

        super(transactionID, amount, date, description, category);

        setSource(source);
        setIncomeType(incomeType);
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        if (source == null ||
            source.trim().isEmpty() ||
            source.contains("\n") ||
            source.contains("\r")) {
            throw new IllegalArgumentException("Invalid source");
        }
        this.source = source;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        if (incomeType == null ||
            incomeType.trim().isEmpty() ||
            incomeType.contains("\n") ||
            incomeType.contains("\r")) {
            throw new IllegalArgumentException("Invalid income type");
        }
        this.incomeType = incomeType;
    }

    @Override
    public String getTransactionType() {
        return "Income";
    }

    @Override
    public String toString() {
        return super.toString()
                + ", Source: " + source
                + ", Income Type: " + incomeType;
    }
}
