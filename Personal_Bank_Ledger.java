import java.util.*;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;
public class Personal_Bank_Ledger {
    static final Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "ledger_statement.txt";
    static final int START_ID = 26200;
    interface Reportable {
        void generateStatement();
    }
    static class Transaction implements Comparable<Transaction> {
        private final int id;
        private final String date;
        private final String description;
        private final String paymentType;
        private final String type;
        private final double amount;
        private final double balanceAfter;
        public Transaction(int id, String description,String paymentType, String type, double amount, double balanceAfter) {
            this.id = id;
            this.date = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            this.description = description;
            this.paymentType = paymentType;
            this.type = type;
            this.amount = amount;
            this.balanceAfter = balanceAfter;
        }
        public int getId() {
            return id;
        }
        public String getDate() {
            return date;
        }
        public String getDescription() {
            return description;
        }
        public String getPaymentType() {
            return paymentType;
        }
        public String getType() {
            return type;
        }
        public double getAmount() {
            return amount;
        }
        public double getBalanceAfter() {
            return balanceAfter;
        }
        @Override
        public int compareTo(Transaction other) {
            return Integer.compare(this.id, other.id);
        }
        @Override
        public String toString() {
            return String.format(
                "%-10d %-17s %-20s %-12s %-12s %10.2f %12.2f",
                id, date, description, paymentType, type,
                amount, balanceAfter);
        }
    }
    static class BankAccount implements Reportable {
        private final String accountHolder;
        private final double openingBalance;
        private double balance;
        private double totalDeposits;
        private double totalWithdrawals;
        private int nextId = START_ID;
        private final List<Transaction> transactions =
            new ArrayList<>();
        private final Set<Integer> transactionIds =
            new HashSet<>();
        private final Map<String, Double> categoryTotals =
            new TreeMap<>();
        private final Map<String, Integer> transactionCounts =
            new HashMap<>();
        public BankAccount(String accountHolder,
                           double openingBalance) {
            this.accountHolder = accountHolder;
            this.openingBalance = openingBalance;
            this.balance = openingBalance;
        }
        public void deposit(double amount) {
            deposit(amount, "General deposit", "Cash");
        }
        public void deposit(double amount,
                            String description,
                            String paymentType) {
            if (!validAmount(amount)) {
                System.out.println("Invalid deposit amount.");
                return;
            }
            balance += amount;
            totalDeposits += amount;
            addTransaction(
                description, paymentType, "DEPOSIT", amount
            );
            System.out.println("Deposit successful!");
        }
        public void withdraw(double amount,
                             String description,
                             String paymentType) {
            if (!validAmount(amount)) {
                System.out.println("Invalid withdrawal amount.");
                return;
            }
            if (amount > balance) {
                System.out.println("Transaction declined: insufficient balance.");
                return;
            }
            balance -= amount;
            totalWithdrawals += amount;
            addTransaction(description, paymentType, "WITHDRAWAL", amount);
            System.out.println("Withdrawal successful!");
            }
        private boolean validAmount(double amount) {
            return Double.isFinite(amount) && amount > 0;
        }
        private void addTransaction(String description,String paymentType,String type,double amount) {
            Transaction t = new Transaction( nextId++,description,paymentType,type,amount,balance);
            transactions.add(t);
            transactionIds.add(t.getId());
            categoryTotals.merge(description, amount, Double::sum);
            transactionCounts.merge(type, 1, Integer::sum);
        }
        public void showBalance() {
            System.out.printf(
"%nCurrent balance: Rs. %.2f%n", balance);
        }
        private Transaction searchById(int id, int index) {
            if (index >= transactions.size()) {
                return null;
            }
            if (transactions.get(index).getId() == id) {
                return transactions.get(index);
            }
            return searchById(id, index + 1);
        }
        public void searchTransaction(int id) {
            Transaction result = searchById(id, 0);
            if (result == null) {
                System.out.println("Transaction not found.");
            } else {
                System.out.println("\nTransaction found:");
                printTableHeader();
                System.out.println(result);
            }
        }
        public void showTransactions() {
            if (transactions.isEmpty()) {
                System.out.println("No transactions recorded.");
                return;
            }
            System.out.println("\n========== TRANSACTION HISTORY ==========");
            printTableHeader();
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
        private void printTableHeader() {
            System.out.printf(
                "%-10s %-17s %-20s %-12s %-12s %10s %12s%n",
                "ID", "Date", "Description", "Payment",
                "Type", "Amount", "Balance" );
            System.out.println("-".repeat(105));
        }
        public void showStatistics() {
            System.out.println("\n========== LEDGER SUMMARY ==========");
            System.out.println("Account holder    : " + accountHolder);
            System.out.printf( "Opening balance   : Rs. %.2f%n", openingBalance);
            System.out.printf("Total deposits    : Rs. %.2f%n", totalDeposits);
            System.out.printf("Total withdrawals : Rs. %.2f%n", totalWithdrawals);
            System.out.printf("Closing balance   : Rs. %.2f%n", balance );
            System.out.println("Total transactions: " + transactions.size());
            System.out.println("\nTransaction counts by type:");
            transactionCounts.forEach((type, count) ->System.out.println(type + ": " + count));
            System.out.println("\nAmounts by description:");
            categoryTotals.forEach((description, amount) ->
                System.out.printf("%s: Rs. %.2f%n", description, amount) );
            Predicate<Transaction> largeTransaction =
                t -> t.getAmount() >= 1000;
            double largeTotal = transactions.stream()
                .filter(largeTransaction)
                .mapToDouble(Transaction::getAmount)
                .sum();
            long largeCount = transactions.stream()
                .filter(largeTransaction)
                .count();
            System.out.printf("%nTransactions of Rs. 1000 or more: %d%n",largeCount);
            System.out.printf("Combined amount: Rs. %.2f%n", largeTotal);
            System.out.println("\nTransactions sorted by amount:");
            transactions.stream()
                .sorted(Comparator.comparingDouble(
                    Transaction::getAmount))
                .forEach(System.out::println);
        }
        @Override
        public void generateStatement() {
            StringBuilder report = new StringBuilder();
            report.append( "============================================\n" );
            report.append( "       PERSONAL LEDGER STATEMENT\n");
            report.append("============================================\n");
            report.append("Account holder: ")
                  .append(accountHolder).append('\n');
            report.append("Statement date: ")
                  .append(LocalDateTime.now().format(
                      DateTimeFormatter.ofPattern(
                          "dd-MM-yyyy HH:mm")))
                  .append("\n\n");
            report.append(String.format("Opening balance   : Rs. %.2f%n", openingBalance));
            report.append(String.format("Total deposits    : Rs. %.2f%n", totalDeposits ));
            report.append(String.format(
                "Total withdrawals : Rs. %.2f%n",
                totalWithdrawals));
            report.append(String.format("Closing balance   : Rs. %.2f%n%n", balance));
            report.append(String.format(
                "%-10s %-17s %-20s %-12s %-12s %10s %12s%n",
                "ID", "Date", "Description", "Payment",
                "Type", "Amount", "Balance"));
            report.append("-".repeat(105)).append('\n');
            List<Transaction> sorted =
                new ArrayList<>(transactions);
            Collections.sort(sorted);
            for (Transaction t : sorted) {
                report.append(t).append('\n');
            }
            report.append("\n--- TRANSACTION SUMMARY ---\n");
            report.append("Number of transactions: ")
                  .append(transactions.size()).append('\n');
            transactionCounts.forEach((type, count) ->
                report.append(type)
                      .append(": ")
                      .append(count)
                      .append('\n')
            );
            report.append(
                "\nGenerated by Personal Ledger.\n"
            );
            System.out.println("\n" + report);
            try {
                Files.writeString(
                    Path.of(FILE_NAME),
                    report.toString(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);
                System.out.println("Statement saved to: " + FILE_NAME);
            } catch (IOException e) {
                System.out.println(
                    "Could not save statement: " + e.getMessage());
            }
        }
        @Override
        public String toString() {
            return "PersonalLedger{holder='" + accountHolder
                + "', balance=" + balance + "}";
        }
    }
    static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty.");
        }
    }
    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }
    static double readAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double amount =
                    Double.parseDouble(sc.nextLine().trim());
                if (Double.isFinite(amount) && amount > 0) {
                    return amount;
                }
                System.out.println(
                    "Enter an amount greater than zero." );
            } catch (NumberFormatException e) {
                System.out.println(
                    "Please enter a valid numeric amount.");
            }
        }
    }
    static void showMenu() {
        System.out.println( "\n========== PERSONAL BANK LEDGER ==========");
        System.out.println("1. Deposit money");
        System.out.println("2. Withdraw money");
        System.out.println("3. Check balance");
        System.out.println("4. View transaction history");
        System.out.println("5. Search transaction by ID");
        System.out.println("6. View ledger statistics");
        System.out.println("7. Generate and save statement");
        System.out.println("8. Exit");
        System.out.println(
            "==========================================="
        );
    }    public static void main(String[] args) {
        System.out.println(
            "PERSONAL LEDGER AND STATEMENT GENERATOR"
        );
        String holder = readText("Enter account holder name: ");
        double openingBalance;
        while (true) {
            System.out.print("Enter opening balance (Rs.): ");
            try {
                openingBalance =
                    Double.parseDouble(sc.nextLine().trim());
                if (Double.isFinite(openingBalance)
                    && openingBalance >= 0) {
                    break;
                }
                System.out.println( "Opening balance cannot be negative." );
            } catch (NumberFormatException e) {
                System.out.println( "Enter a valid numeric balance.");
            }
        }
        BankAccount account =
            new BankAccount(holder, openingBalance);
        int choice;
        do {
            showMenu();
            choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1: {
                    double amount =
                        readAmount("Enter deposit amount (Rs.): ");
                    String description =
                        readText("Enter description: ");
                    String payment =
                        readText("Payment method (Cash/UPI/Card/Transfer): ");
                    account.deposit(amount, description, payment);
                    break;
                }
                case 2: {
                    double amount =
                        readAmount("Enter withdrawal amount (Rs.): ");
                    String description =
                        readText("Enter description: ");
                    String payment =
                        readText("Payment method (Cash/UPI/Card/Transfer): ");
                    account.withdraw(amount, description, payment);
                    break;
                }
                case 3:
                    account.showBalance();
                    break;
                case 4:
                    account.showTransactions();
                    break;
                case 5: {
                    int id = readInt("Enter transaction ID: ");
                    account.searchTransaction(id);
                    break;
                }
                case 6:
                    account.showStatistics();
                    break;
                case 7:
                    account.generateStatement();
                    break;

                case 8:
                    System.out.println(
                        "Thank you for using the personal ledger."
                    );
                    break;
                default:
                    System.out.println("Invalid choice. Select 1 to 8.");
            }
        } 
        while (choice != 8);
        sc.close();
    }
}