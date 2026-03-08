# MO-IT101-Group1
Members and Contributions:

Kayel Niccolo Astrera-

Maria Leorrie Dipus-

Jerymy Malaga-

Levi Ledesma Sadaran-

PROGRAM DETAILS:

The MotorPH Payroll System is a simple Java-based payroll processing program that reads employee and attendance data from CSV/Excel files and calculates employee salaries based on hours worked.

The system performs the following functions:

**1. Login Authentication**
-The program asks the user to enter a username and password.

Valid usernames are:

-employee

-payroll_staff

Password: 12345

If the credentials are incorrect, the program displays "Incorrect username and/or password" and terminates.

**2. Employee Data Reading**
-The system reads employee information from a provided CSV/Excel file.

The employee information includes:

-Employee Number

-Employee Name

-Birthday

-Hourly Rate

**3. Attendance Processing**

-Attendance records are read from the attendance file.

-Only records from June to December are processed.

**4. Hours Worked Calculation**

-Only working hours between 8:00 AM and 5:00 PM are counted.

-Extra hours outside this range are not included in the total hours worked.

**5. Salary Computation**

-Total Hours Worked = Time Out − Time In (based on rules provided)

-Gross Salary = Total Hours Worked × Hourly Rate

-Net Salary = Gross Salary − Total Deductions

**6. Payroll Output**

-The system displays employee details along with their computed payroll information.

PROJECT PLAN LINK:

https://docs.google.com/spreadsheets/d/1ZcgHoZ2nyqqXSyVbdUa3h6pLU1KOtvXNNVhWx1taiTM/edit?usp=sharing
