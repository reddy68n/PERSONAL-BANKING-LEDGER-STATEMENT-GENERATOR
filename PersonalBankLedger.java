import java.util.Scanner;

public class PersonalBankLedger {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Account details
        System.out.println("==============================================");
        System.out.println("          PERSONAL BANK LEDGER");
        System.out.println("==============================================");

        System.out.print("Enter Account Holder Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Account Number: ");
        long accountNumber = sc.nextLong();

        System.out.print("Enter Opening Balance: ");
        double openingBalance = sc.nextDouble();

        double balance = openingBalance;

        System.out.print("Enter number of transactions: ");
        int n = sc.nextInt();
        sc.nextLine();

        // Arrays for transaction details
        String[] description = new String[n];
        String[] paymentType = new String[n];
        String[] transactionType = new String[n];

        double[] amount = new double[n];
        double[] balanceAfter = new double[n];

        double totalDeposit = 0;
        double totalWithdrawal = 0;

        // Enter transactions
        for (int i = 0; i < n; i++) {

            System.out.println("\nTransaction " + (i + 1));
            System.out.println("----------------------------------------------");

            System.out.print("Enter Description: ");
            description[i] = sc.nextLine();

            System.out.println("Payment Type:");
            System.out.println("1. Cash");
            System.out.println("2. UPI");
            System.out.println("3. Debit Card");
            System.out.println("4. Credit Card");
            System.out.println("5. Bank Transfer");

            System.out.print("Enter Payment Type (1-5): ");
            int paymentChoice = sc.nextInt();
            sc.nextLine();

            if (paymentChoice == 1) {
                paymentType[i] = "Cash";
            } else if (paymentChoice == 2) {
                paymentType[i] = "UPI";
            } else if (paymentChoice == 3) {
                paymentType[i] = "Debit Card";
            } else if (paymentChoice == 4) {
                paymentType[i] = "Credit Card";
            } else if (paymentChoice == 5) {
                paymentType[i] = "Bank Transfer";
            } else {
                System.out.println("Invalid payment type!");
                i--;
                continue;
            }

            System.out.println("\nTransaction Type:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdrawal");

            System.out.print("Enter Transaction Type (1/2): ");
            int transactionChoice = sc.nextInt();

            System.out.print("Enter Amount: ");
            double transactionAmount = sc.nextDouble();
            sc.nextLine();

            if (transactionChoice == 1) {

                balance = balance + transactionAmount;
                totalDeposit = totalDeposit + transactionAmount;
                transactionType[i] = "Deposit";

            } else if (transactionChoice == 2) {

                if (transactionAmount <= balance) {

                    balance = balance - transactionAmount;
                    totalWithdrawal = totalWithdrawal + transactionAmount;
                    transactionType[i] = "Withdrawal";

                } else {

                    System.out.println("Insufficient balance!");
                    System.out.println("Please enter the transaction again.");

                    i--;
                    continue;
                }

            } else {

                System.out.println("Invalid transaction type!");
                i--;
                continue;
            }

            amount[i] = transactionAmount;
            balanceAfter[i] = balance;
        }

        // Generate Bank Statement
        System.out.println("\n\n==============================================================");
        System.out.println("                     BANK STATEMENT");
        System.out.println("==============================================================");

        System.out.println("Account Holder : " + name);
        System.out.println("Account Number : " + accountNumber);

        System.out.printf("Opening Balance: Rs. %.2f%n", openingBalance);

        System.out.println("--------------------------------------------------------------");

        System.out.printf("%-4s %-18s %-15s %-15s %-12s %-12s%n",
                "No.", "Description", "Payment Type",
                "Transaction", "Amount", "Balance");

        System.out.println("--------------------------------------------------------------");

        // Display transactions
        for (int i = 0; i < n; i++) {

            System.out.printf("%-4d %-18s %-15s %-15s Rs.%-9.2f Rs.%-9.2f%n",
                    (i + 1),
                    description[i],
                    paymentType[i],
                    transactionType[i],
                    amount[i],
                    balanceAfter[i]);
        }

        System.out.println("--------------------------------------------------------------");

        System.out.printf("Total Deposits    : Rs. %.2f%n", totalDeposit);
        System.out.printf("Total Withdrawals : Rs. %.2f%n", totalWithdrawal);
        System.out.printf("Closing Balance   : Rs. %.2f%n", balance);

        // Ternary operator
        String status = (balance > 0) ? "Account Active" : "Zero Balance";

        System.out.println("Account Status    : " + status);

        System.out.println("==============================================================");
        System.out.println("              THANK YOU FOR USING OUR SOFTWARE                ");
        System.out.println("==============================================================");

        sc.close();
    }
}
/**
 * ==============================================
          PERSONAL BANK LEDGER
==============================================
Enter Account Holder Name: RAVI
Enter Account Number: 13235516321
Enter Opening Balance: 1000
Enter number of transactions: 3

Transaction 1
----------------------------------------------
Enter Description: SALRY
Payment Type:
1. Cash
2. UPI
3. Debit Card
4. Credit Card
5. Bank Transfer
Enter Payment Type (1-5): 5

Transaction Type:
1. Deposit
2. Withdrawal
Enter Transaction Type (1/2): 1
Enter Amount: 50000

Transaction 2
----------------------------------------------
Enter Description: UTILITIES
Payment Type:
1. Cash
2. UPI
3. Debit Card
4. Credit Card
5. Bank Transfer
Enter Payment Type (1-5): 2

Transaction Type:
1. Deposit
2. Withdrawal
Enter Transaction Type (1/2): 2
Enter Amount: 3000

Transaction 3
----------------------------------------------
Enter Description: SHOPPING
Payment Type:
1. Cash
2. UPI
3. Debit Card
4. Credit Card
5. Bank Transfer
Enter Payment Type (1-5): 2

Transaction Type:
1. Deposit
2. Withdrawal
Enter Transaction Type (1/2): 2
Enter Amount: 2000


==============================================================
                     BANK STATEMENT
==============================================================
Account Holder : RAVI
Account Number : 13235516321
Opening Balance: Rs. 1000.00
--------------------------------------------------------------
No.  Description        Payment Type    Transaction     Amount       Balance     
--------------------------------------------------------------
1    SALRY              Bank Transfer   Deposit         Rs.50000.00  Rs.51000.00 
2    UTILITIES          UPI             Withdrawal      Rs.3000.00   Rs.48000.00 
3    SHOPPING           UPI             Withdrawal      Rs.2000.00   Rs.46000.00 
--------------------------------------------------------------
Total Deposits    : Rs. 50000.00
Total Withdrawals : Rs. 5000.00
Closing Balance   : Rs. 46000.00
Account Status    : Account Active
==============================================================
              THANK YOU FOR USING OUR SOFTWARE
==============================================================
 */