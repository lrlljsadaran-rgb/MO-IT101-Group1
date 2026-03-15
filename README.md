# MO-IT101-Group1
## | Member | Contributions |

| Kayel Niccolo Astrera | Business Requirements and Project Plan, Employee Details Presentation, log in & log out |

| Maria Leorrie Dipus | Business Requirements and Project Plan, Payroll Calculation and Display, QA Testing |

| Jerymy Malaga | Use Case, Payroll Calculation and Display, Basic Payroll System |

| Levi Ledesma Sadaran | Wireframe Design, Employee Details Presentation, Semi-Monthly Salary |

---

## PROGRAM DETAILS:

The MotorPH Payroll System is a simple payroll processing program that reads employee and attendance data from CSV/Excel files and calculates employee salaries based on hours worked.

The system performs the following functions:

1. Login authentication 

2. Employee record lookup 

3. Attendance processing 

4. Gross salary calculation

5. Government deductions computation

6. Net salary calculation 

7. The program processes payroll from June to December.

### Program Structure:

The program contains several main methods that perform specific payroll tasks:
 
    MotorPhPayrollSystem.java

### Key methods include:

1. main()

2. convertToHours()

3. getOneEmployee()

4. oneEmployeeSalary()

5. calculateNet2()

6. getMonth()

7. allMonthSalary()

## 1. Login Authentication (Main Method)

The program starts by requesting a username and password.
     
    System.out.print("Enter username: ");
    String username = sc.nextLine();

    System.out.print("Enter password: ");
    String password = sc.nextLine();

### The program validates the login credentials.    

 Valid users:

    employee
    payroll_staff

 Password:  

    12345

If the credentials are incorrect, the program terminates.

    System.out.println("Incorrect username and/or password.");
    System.exit(0);

## Payroll Staff Menu

If the user logs in as payroll_staff, the following menu appears:

    1. One Employee
    2. All employee
    3. Exit the program

### Option 1

Displays salary computation for a specific employee.

    String empNumber = getOneEmployee();
    for(int month=6; month<=12; month++)
    {
      oneEmployeeSalary(empNumber, month);
    }

### Option 2

Displays salary reports for all employees.

    allMonthSalary();

##  4. Reading Employee Data   

Employee data is read from a CSV file using BufferedReader.
    
    BufferedReader reader =
    new BufferedReader(new FileReader(employeeFile));

The program extracts the following information:

Employee Number

Name

Birthday

Hourly Rate

Example code:

    String[] employeeRecord = line.split(",");
    hourlyRate = Double.parseDouble(data[data.length - 1]);

## 5. Reading Attendance Records

Attendance records are stored in another CSV file.

Fields used:

    Employee ID
    Date
    Log In
    Log Out

Example code:

    BufferedReader attReader =
    new BufferedReader(new FileReader(attendanceFile));    

## 6. Time Conversion Method

The program converts time into decimal hours.

Example:

    08:30 → 8.5

Method used:

    public static double convertToHours(String time)
    {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
    
        return hours + (minutes / 60.0);
    }    
## 7. Working Hour Rules

The system applies the following company rules:

    if (timeIn < 8.0) timeIn = 8.0;
    if (timeOut > 17.0) timeOut = 17.0;

Meaning:
<pre>
Rule                                 Description

Earliest log-in	                       8:00 AM

Latest log-out	                       5:00 PM</pre>

Hours worked are calculated using:

    hoursWorked = timeOut - timeIn

## 8. Payroll Cut-Off System

Salary is divided into two payroll periods.

<pre>
Cut-Off                                 Days
1st Cut-off                            	Day 1 – 15
2nd Cut-off	                            Day 16 – End of Month</pre>

Code logic:

    if (day <= 15)
    {
        firstCutoff += dailyPay;
    }
    else
    {
        secondCutoff += dailyPay;
    }

## 9. Gross Salary Calculation

Daily salary is computed using:

    dailyPay = hoursWorked * hourlyRate;

First cut-off:

    firstCutoff += dailyPay;

Second cut-off:

    secondCutoff += dailyPay;

## 10. Government Deductions

### The program calculates the following deductions.

### Pag-IBIG

  1% – 2% of salary
  Maximum contribution: 100

Example code:

    pagibig = totalGross * 0.02;
    if (pagibig > 100)
    {
        pagibig = 100;
    }

### PhilHealth

3% of salary

Example code:

    philhealth = (totalGross * 0.03) / 2;
   
 ### SSS
    
SSS contributions are calculated using salary brackets.

Example logic:
    
    if (totalGross < 3250)
    {
        sssDeduction = 135;
    }
    else
    {
        bracket computation
    }

### Withholding Tax

Tax is calculated using BIR tax brackets.

Example:

    if (taxable < 20832)
    {
        tax = 0;
    }
    else
    {
        tax = taxable * taxRate;
    }    

## 11. Net Salary Calculation

The program calculates the total deductions.

    totalDeductions = pagibig + philhealth + sss + tax;

Net salary:

    netPay = grossSalary - totalDeductions;

The system displays:

    Total Gross
    Taxable Income
    Pagibig
    Philhealth
    SSS
    Tax
    Total Deductions
    Net Pay

## 12. Monthly Salary Report

Payroll staff can generate reports for all employees from June to December.

Example code:

    for(int month=6; month<=12; month++)
    {
        oneEmployeeSalary(employeeID, month);
    }

## This produces a monthly payroll summary.

How to Run the Program

Compile the program:

    javac MotorPhPayrollSystem.java

Run the program:

    java MotorPhPayrollSystem
    







## PROJECT PLAN LINK: [GROUP 1 Payroll System Project Plan ](https://docs.google.com/spreadsheets/d/1ZcgHoZ2nyqqXSyVbdUa3h6pLU1KOtvXNNVhWx1taiTM/edit?usp=sharing)
