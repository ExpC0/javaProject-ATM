# ATM System  
A Simple Java Desktop Application  

This project simulates an Automated Teller Machine (ATM) system, providing essential banking functionalities such as deposits, withdrawals, balance inquiries, and money transfers in a user-friendly desktop environment.  

---

## **Objective**  
- To develop a robust and interactive Java desktop application.  
- To learn and implement database management systems.  
- To provide quick self-service banking transactions such as deposits, withdrawals, and account management.  

---

## **Features**  
### **Core Functionalities**  
- **User Authentication**:  
  - Secure login system with account verification and password recovery.  
- **Account Management**:  
  - Create, view, and manage account details.  
  - Password reset functionality via security questions.  
- **Banking Transactions**:  
  - Deposit: Only amounts greater than and multiples of 500 BDT are accepted.  
  - Withdrawal: Ensures sufficient balance and amount in multiples of 500 BDT.  
  - Balance Transfer: Allows transfers within the same bank for amounts greater than 500 BDT.  
  - Recent Transactions: View the last 10 transaction records with timestamps.  

---

## **Technologies Used**  
- **Programming Language**: Java  
- **GUI Framework**: Swing  
- **Database**: SQLite  

---

## **Project Structure**  
1. **Login Page**: Secure access with options for new account creation and password recovery.  
2. **Main Menu**: Access to key features:  
   - Deposit, Withdrawal, Transfer Balance  
   - Account Details, Recent Transactions, Change PIN  
3. **Account Management**: Create and maintain user account details, balance, and transaction records.  
4. **Database Design**:  
   - Tables for account details, balance, and transaction history.  

---

## **Setup Instructions**  

### **Prerequisites**  
- Install [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html) (version 8 or above).  
- Install SQLite or any compatible SQL database management tool.  

### **Steps to Run**  
1. Clone this repository:  
   ```bash
   git clone https://github.com/yourusername/ATM-System.git
   cd ATM-System
