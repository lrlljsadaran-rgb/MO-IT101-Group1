# MO-IT101-Group1

## | Member | Contributions |

| Kayel Niccolo Astrera | Business Requirements and Project Plan, Employee Details Presentation, Login System |
| Maria Leorrie Dipus | Business Requirements and Project Plan, Payroll Calculation and Display, QA Testing |
| Jerymy Malaga | Use Case, Payroll Calculation and Display, Core Payroll System |
| Levi Ledesma Sadaran | Wireframe Design, Employee Details Presentation, Semi-Monthly Salary |

---

## PROGRAM DETAILS:

The **MotorPH Payroll System** is a Java-based console application that reads employee and attendance data from CSV files and computes payroll based on company rules and government deductions.

The system performs the following functions:

1. Login authentication  
2. Employee record lookup  
3. Attendance processing  
4. Gross salary computation  
5. Government deductions computation  
6. Net salary computation  
7. Payroll processing from June to December (2024)

---

## Program Structure:

    MotorPhPayrollSystem.java

---

## Key Methods Include:

1. main()  
2. isValidLogin()  
3. handleEmployeeMenu()  
4. handlePayrollStaffMenu()  
5. readCsvFile()  
6. parseCsvLine()  
7. computeMonthlySalary()  
8. computeWorkedHours()  
9. calculatePagibig()  
10. calculatePhilhealth()  
11. calculateSss()  
12. calculateTax()  

---

## 1. Login Authentication (Main Method)

The program starts by requesting a username and password.

    System.out.print("Enter username: ");
    String username = sc.nextLine().trim();

    System.out.print("Enter password: ");
    String password = sc.nextLine().trim();

### Valid users:

    employee
    payroll_staff

Password:

    12345

If invalid:

    System.out.println("Incorrect username and/or password.");
    System.exit(0);

---

## 2. Employee Menu

If the user logs in as **employee**, the following options appear:

    1. Enter your employee number
    2. Exit the program

### Option 1:
Displays employee details:

- Employee Number  
- Name  
- Birthday  

---

## 3. Payroll Staff Menu

If the user logs in as **payroll_staff**, the following menu appears:

    1. Process payroll
    2. Exit the program

### Sub-options:

    1. One employee
    2. All employees
    3. Exit

---

## 4. Reading Employee Data

Employee data is read from a CSV file using:

    readCsvFile(employeeFile);

Fields extracted:

- Employee Number  
- Last Name  
- First Name  
- Birthday  
- Hourly Rate  

---

## 5. Reading Attendance Records

Attendance records are read from another CSV file:

    readCsvFile(attendanceFile);

Fields used:

- Employee Number  
- Date (MM/DD/YYYY)  
- Log In  
- Log Out  

---

## 6. CSV Parsing (Improved)

The system handles commas inside quotes using:

    parseCsvLine(String line)

This ensures correct data extraction even with complex CSV formats.

---

## 7. Time Conversion Method

The program converts time into decimal format.

Example:

    08:30 → 8.5

Method:

    convertToHours(String time)

---

## 8. Working Hour Rules

The system applies the following rules:

- Work starts at **8:00 AM**
- Work ends at **5:00 PM**
- 1-hour break deducted
- 30-minute rounding rule applied

Example logic:

    if (timeIn < 8.0) timeIn = 8.0;
    if (timeOut > 17.0) timeOut = 17.0;

---

## 9. Payroll Cut-Off System

Salary is divided into two periods:

    1st Cut-Off  → Day 1 – 15
    2nd Cut-Off  → Day 16 – End of Month

- First cut-off has **no deductions**
- Second cut-off includes **all deductions**

---

## 10. Gross Salary Calculation

Computed per day:

    grossPay = workedHours * hourlyRate;

Accumulated into:

- First Cut-Off  
- Second Cut-Off  

---

## 11. Government Deductions

### Pag-IBIG

- 1%–2% of salary  
- Maximum: 100  

### PhilHealth

- 3% contribution (varies based on salary)

### SSS

- Based on salary bracket

### Withholding Tax

- Based on taxable income using BIR brackets

---

## 12. Net Salary Calculation

    totalDeductions = pagibig + philhealth + sss + tax;

    netSalary = secondCutoffGross - totalDeductions;

Displayed values:

- Total Gross  
- Taxable Income  
- Pag-IBIG  
- PhilHealth  
- SSS  
- Tax  
- Total Deductions  
- Net Salary  

---

## 13. Monthly Salary Report

Payroll is processed from **June to December (2024)**:

    for (int month = 6; month <= 12; month++)

Supports:

- Single employee payroll  
- All employees payroll  

---

## 14. Additional Features

- Login authentication system  
- Dynamic file path detection  
- Employee search by ID  
- Structured payroll display  
- Error handling for invalid input  
- Automatic CSV file parsing  

## This produces a monthly payroll summary.


    







## PROJECT PLAN LINK: [GROUP 1 Payroll System Project Plan ](https://docs.google.com/spreadsheets/d/1ZcgHoZ2nyqqXSyVbdUa3h6pLU1KOtvXNNVhWx1taiTM/edit?usp=sharing)
