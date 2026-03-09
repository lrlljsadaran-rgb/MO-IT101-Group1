# MO-IT101-Group1
## | Member | Contributions |

| Kayel Niccolo Astrera | Business Requirements and Project Plan, Employee Details Presentation |

| Maria Leorrie Dipus | Business Requirements and Project Plan, Payroll Calculation and Display |

| Jerymy Malaga | Use Case, Payroll Calculation and Display |

| Levi Ledesma Sadaran | Wireframe Design, Employee Details Presentation |

---

## PROGRAM DETAILS:

The MotorPH Payroll System is a simple payroll processing program that reads employee and attendance data from CSV/Excel files and calculates employee salaries based on hours worked.

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

---

## PROJECT PLAN LINK:

https://docs.google.com/spreadsheets/d/1ZcgHoZ2nyqqXSyVbdUa3h6pLU1KOtvXNNVhWx1taiTM/edit?usp=sharing

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.filehandling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



/**
 *
 * @author MARIA LEORRIE
 */


public class AllEmployee {
 public static void main(String[] args) {
     
//Display Basic Info (All Employee)
     String filePath = "Employee Data.csv";
     try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
         String line;
         br.readLine();
         while ((line = br.readLine()) != null) {
             String[] parts = line.split(",");
             String employeeId = parts[0];
             String fullName = parts[1] + ", " + parts [2];
             String birthday = parts[3];
          
             System.out.println("Employee #: " + employeeId);
             System.out.println("Full Name: " + fullName);
             System.out.println("Birthday: " + birthday);

               } 
     }  catch (IOException e){
              System.out.println("Error reading items.txt");    
                 }
}
