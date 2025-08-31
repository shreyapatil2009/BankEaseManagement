# 🏦 Banking Management System (Java + MySQL)

A simple **console-based Banking Management System** built in **Java** using **JDBC** and **MySQL**.  
This project allows users to **create bank accounts, deposit money, withdraw money, check balances, and view transaction history**.  
It’s a great starter project for learning **Java + MySQL integration**.

---

## ✨ Features
- Create new bank accounts with an initial balance.
- Deposit money into an account.
- Withdraw money with balance validation.
- Check account balance.
- View full transaction history for an account.
- Data stored persistently in a MySQL database.
- Clean **menu-driven console interface**.

---

## 🛠️ Tech Stack
| Technology      | Purpose                     |
|-----------------|---------------------------|
| **Java 17+**    | Core programming language |
| **MySQL**       | Database for storing data |
| **JDBC**        | Java Database Connector   |
| **Maven**       | Dependency management     |
| **IntelliJ IDEA** | Recommended IDE        |

---

## 🗄️ Database Setup
Run the following SQL in MySQL to set up the database:

```sql
CREATE DATABASE bankdb;
USE bankdb;

CREATE TABLE accounts (
    account_no INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    balance DOUBLE
);

CREATE TABLE transactions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    account_no INT,
    type VARCHAR(50),
    amount DOUBLE,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_no) REFERENCES accounts(account_no)
);
