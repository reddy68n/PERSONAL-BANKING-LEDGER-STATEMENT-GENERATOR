# PERSONAL-BANKING-LEDGER-STATEMENT-GENERATOR
Developing by using JAVA program
💳 Personal Bank Ledger and Statement Generator

A console-based Java application designed to simplify personal financial record management by tracking deposits, withdrawals, transaction history, account balances, and generating formatted financial statements.

📌 Project Overview

The Personal Bank Ledger and Statement Generator helps users maintain organized records of their personal financial transactions. Instead of calculating balances and preparing statements manually, the application automatically updates the balance, validates transactions, records transaction details, and generates a statement that can be saved as a text file.

Each successful transaction receives a unique transaction ID starting from 100001, making it easier to identify and search for individual transactions.

This project demonstrates the practical application of Java programming concepts through a simple, interactive, menu-driven system.

✨ Features

Account Initialization: Enter the account holder's name and opening balance.

Deposit Management: Record deposits with descriptions and payment methods.

Withdrawal Management: Record withdrawals while preventing transactions that exceed the available balance.

Automatic Balance Calculation: Update the balance after every successful transaction.

Transaction Identification: Assign unique transaction IDs starting from 100001.

Transaction History: View recorded transactions with dates, descriptions, payment methods, amounts, and balances.

Transaction Search: Find a transaction using its unique ID.

Financial Summary: Calculate total deposits, total withdrawals, and closing balance.

Transaction Analysis: Filter and sort transactions and view summaries by type or description.

Statement Generation: Generate a formatted personal ledger statement.

File Export: Save the generated statement as ledger_statement.txt.

Input Validation: Handle invalid numeric inputs and reject invalid transaction amounts.

🛠️ Technologies Used

Programming Language: Java

Input/Output: Scanner, System.out.printf()

Object-Oriented Programming: Classes, objects, constructors, encapsulation, interfaces, and method overriding

Collections Framework: ArrayList, HashSet, HashMap, and TreeMap

Functional Programming: Lambda expressions, Predicate, and Streams API

Date and Time: LocalDateTime, DateTimeFormatter

File Handling: Files, Path, and IOException

Exception Handling: try-catch and NumberFormatException

📚 Java Concepts Demonstrated

Module

Concepts Applied

Module 1 – Java Foundations

Data types, variables, constants, operators, console input, and formatted output

Module 2 – Control Flow

if-else, switch, while, do-while, and input validation

Module 3 – Methods and Recursion

Modular programming, method overloading, recursion, and transaction searching

Module 4 – OOP and Exceptions

Classes, objects, constructors, encapsulation, interfaces, overriding, and exception handling

Module 5 – Strings and File I/O

String manipulation, StringBuilder, sorting, date formatting, and file writing

Module 6 – Collections and Streams

Lists, sets, maps, lambda expressions, predicates, and stream operations
