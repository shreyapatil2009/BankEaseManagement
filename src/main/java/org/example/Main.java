import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n🏦 Banking Management System");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. View Transactions");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Name: ");
                        sc.nextLine();
                        String name = sc.nextLine();
                        System.out.print("Enter Initial Balance: ");
                        double balance = sc.nextDouble();
                        BankingDB.createAccount(name, balance);
                        break;

                    case 2:
                        System.out.print("Enter Account No: ");
                        int depAcc = sc.nextInt();
                        System.out.print("Enter Amount: ");
                        double depAmt = sc.nextDouble();
                        BankingDB.deposit(depAcc, depAmt);
                        break;

                    case 3:
                        System.out.print("Enter Account No: ");
                        int withAcc = sc.nextInt();
                        System.out.print("Enter Amount: ");
                        double withAmt = sc.nextDouble();
                        BankingDB.withdraw(withAcc, withAmt);
                        break;

                    case 4:
                        System.out.print("Enter Account No: ");
                        int balAcc = sc.nextInt();
                        BankingDB.checkBalance(balAcc);
                        break;

                    case 5:
                        System.out.print("Enter Account No: ");
                        int txnAcc = sc.nextInt();
                        BankingDB.viewTransactions(txnAcc);
                        break;

                    case 6:
                        System.out.println("👋 Exiting...");
                        sc.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("❌ Invalid choice!");
                }
            } catch (SQLException e) {
                System.out.println("⚠️ Database error: " + e.getMessage());
            }
        }
    }
}
