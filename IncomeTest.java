
import java.time.LocalDate;

// testing the Income class
public class IncomeTest {

    public static void main(String[] args) {

        Income income = new Income(
                3, 800.00, LocalDate.of(2026, 11, 12),
                "Weekly paycheck", "Work",
                "Job", "Salary");

        // testing getters
        check("Amount", income.getAmount() == 800.00);
        check("Source", income.getSource().equals("Job"));
        check("Income type",
                income.getIncomeType().equals("Salary"));
        check("Transaction type",
                income.getTransactionType().equals("Income"));

        // testing setters
        income.setSource("Freelance Work");
        income.setIncomeType("Side Income");

        check("Set source",
                income.getSource().equals("Freelance Work"));
        check("Set income type",
                income.getIncomeType().equals("Side Income"));

        check("toString",
                income.toString().contains("Side Income"));

        // testing invalid source
        try {
            income.setSource("");
            check("Reject blank source", false);
        } catch (IllegalArgumentException e) {
            check("Reject blank source", true);
        }

        // testing invalid income type
        try {
            income.setIncomeType(" ");
            check("Reject blank income type", false);
        } catch (IllegalArgumentException e) {
            check("Reject blank income type", true);
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
