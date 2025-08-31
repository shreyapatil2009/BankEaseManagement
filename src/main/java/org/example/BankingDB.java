import java.sql.*;

public class BankingDB {
    private static final String URL = "jdbc:mysql://localhost:3306/bankease";
    private static final String USER = "root"; // Your MySQL username
    private static final String PASS = "Shreya@2009"; // Your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void createAccount(String name, double initialBalance) throws SQLException {
        String query = "INSERT INTO accounts(name, balance) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setDouble(2, initialBalance);
            stmt.executeUpdate();
            System.out.println("✅ Account created successfully!");
        }
    }

    public static void deposit(int accNo, double amount) throws SQLException {
        String updateBalance = "UPDATE accounts SET balance = balance + ? WHERE account_no = ?";
        String insertTransaction = "INSERT INTO transactions(account_no, type, amount) VALUES (?, 'Deposit', ?)";
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(updateBalance);
                 PreparedStatement stmt2 = conn.prepareStatement(insertTransaction)) {

                stmt1.setDouble(1, amount);
                stmt1.setInt(2, accNo);
                stmt1.executeUpdate();

                stmt2.setInt(1, accNo);
                stmt2.setDouble(2, amount);
                stmt2.executeUpdate();

                conn.commit();
                System.out.println("✅ Deposit successful!");
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public static void withdraw(int accNo, double amount) throws SQLException {
        String checkBalance = "SELECT balance FROM accounts WHERE account_no = ?";
        try (Connection conn = getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkBalance)) {
            checkStmt.setInt(1, accNo);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance >= amount) {
                    String updateBalance = "UPDATE accounts SET balance = balance - ? WHERE account_no = ?";
                    String insertTransaction = "INSERT INTO transactions(account_no, type, amount) VALUES (?, 'Withdraw', ?)";
                    conn.setAutoCommit(false);

                    try (PreparedStatement stmt1 = conn.prepareStatement(updateBalance);
                         PreparedStatement stmt2 = conn.prepareStatement(insertTransaction)) {

                        stmt1.setDouble(1, amount);
                        stmt1.setInt(2, accNo);
                        stmt1.executeUpdate();

                        stmt2.setInt(1, accNo);
                        stmt2.setDouble(2, amount);
                        stmt2.executeUpdate();

                        conn.commit();
                        System.out.println("✅ Withdrawal successful!");
                    } catch (SQLException e) {
                        conn.rollback();
                        throw e;
                    }
                } else {
                    System.out.println("❌ Insufficient balance!");
                }
            } else {
                System.out.println("❌ Account not found!");
            }
        }
    }

    public static void checkBalance(int accNo) throws SQLException {
        String query = "SELECT balance FROM accounts WHERE account_no = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, accNo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("💰 Balance: " + rs.getDouble("balance"));
            } else {
                System.out.println("❌ Account not found!");
            }
        }
    }

    public static void viewTransactions(int accNo) throws SQLException {
        String query = "SELECT * FROM transactions WHERE account_no = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, accNo);
            ResultSet rs = stmt.executeQuery();
            System.out.println("\n📜 Transaction History:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " + rs.getString("type") +
                        " | " + rs.getDouble("amount") +
                        " | " + rs.getTimestamp("date"));
            }
        }
    }
}
