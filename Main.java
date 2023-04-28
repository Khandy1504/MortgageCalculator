import java.text.NumberFormat;
import java.util.Scanner;

public class Main {
    final static int MONTHS_IN_YEAR = 12;
    final static byte PERCENT = 100;

    public static void main(String[] args) {

        final byte MAX_PERIOD = 30;
        final byte MIN_PERIOD = 1;
        final int MIN_PRINCIPAL = 1_000;
        final int MAX_PRINCIPAL = 1_000_000;
        final byte MIN_INTEREST_RATE = 0;
        final byte MAX_INTEREST_RATE = 30;

        int principal = (int) readNumber("Principal ($1K - $1M): ", MIN_PRINCIPAL, MAX_PRINCIPAL);
        double interestRate = readNumber("Annual Interest Rate: ", MIN_INTEREST_RATE, MAX_INTEREST_RATE);
        int period = (int) readNumber("Period (Years): ", MIN_PERIOD, MAX_PERIOD);
        printMortgage(principal, interestRate, period);
        printPaymentSchedule(principal, interestRate, period);
    }

    private static void printMortgage(int principal, double interestRate, int period) {
        double mortgage = calculateMortgage(principal, interestRate, period);
        String mortgageFormatted = NumberFormat.getCurrencyInstance().format(mortgage);
        System.out.println();
        System.out.println("MORTGAGE");
        System.out.println("________");
        System.out.println("Monthly Payments: " + mortgageFormatted);
    }

    private static void printPaymentSchedule(int principal, double interestRate, int period) {
        System.out.println();
        System.out.println("PAYMENT SCHEDULE");
        System.out.println("________________");
        for (short month = 1; month <= period * MONTHS_IN_YEAR; month++) {
            double balance = paymentSchedule(principal, interestRate, period, month);
            System.out.println(NumberFormat.getCurrencyInstance().format(balance));
        }
    }

    public static double readNumber(String prompt, double min, double max) {
        Scanner scanner = new Scanner(System.in);
        double value;

        while (true) {
            System.out.print(prompt);
            value = scanner.nextInt();

            if (value < min || value > max) {
                System.out.println("Enter a number between " + min + " and " + max + ".");
                continue;
            }
            break;
        }
        return value;
    }

    public static double calculateMortgage(int principal,
            double interestRate,
            int period) {

        int numberOfPayments = period * MONTHS_IN_YEAR;
        double monthlyInterestRate = (interestRate / PERCENT) / MONTHS_IN_YEAR;

        double mortgage = principal * ((monthlyInterestRate * (Math.pow(1 + monthlyInterestRate, numberOfPayments)))
                / (Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1));

        return mortgage;
    }

    public static double paymentSchedule(int principal, double interestRate, int period, short numberOfPaymentsMade) {

        double monthlyInterestRate = (interestRate / PERCENT) / MONTHS_IN_YEAR;
        int totalPayments = period * MONTHS_IN_YEAR;

        double balance = principal
                * (Math.pow(1 + monthlyInterestRate, totalPayments)
                        - Math.pow(1 + monthlyInterestRate, numberOfPaymentsMade))
                / (Math.pow(1 + monthlyInterestRate, totalPayments) - 1);

        return balance;
    }
}