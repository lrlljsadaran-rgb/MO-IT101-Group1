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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semimonthlysalary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author levil
 */

public class SemiMonthlySalary {

    public static void main(String[] args) {

        String employeeFile = "Employee with Hourly Rate.csv";
        String attendanceFile = "attendance.csv";

        String[] employeeID = new String[40];
        String[] employeeName = new String[40];
        double[] hourlyRate = new double[40];

        double[] firstCutoff = new double[40];
        double[] secondCutoff = new double[40];
        double firstCutOffNetpay = 0;

        int count = 0;

        try {

            // READ EMPLOYEE FILE
            BufferedReader empReader = new BufferedReader(new FileReader(employeeFile));
            empReader.readLine();

            String line;

            while ((line = empReader.readLine()) != null) {

                String[] data = line.split(",");

                employeeID[count] = data[0];
                employeeName[count] = data[2] + " " + data[1];

                // Always take the LAST column for hourly rate
                hourlyRate[count] = Double.parseDouble(data[data.length - 1]);

                count++;
            }

            empReader.close();

            // READ ATTENDANCE FILE
            BufferedReader attReader = new BufferedReader(new FileReader(attendanceFile));
            attReader.readLine();

            while ((line = attReader.readLine()) != null) {

                String[] data = line.split(",");

                if(data.length < 6) continue;
                
                String empID = data[0];
                String date = data[3];
                String logIn = data[4];
                String logOut = data[5];

                double timeIn = convertToHours(logIn);
                double timeOut = convertToHours(logOut);

                // Apply company rule
                if (timeIn < 8.0) timeIn = 8.0;
                if (timeOut > 17.0) timeOut = 17.0;

                double hoursWorked = timeOut - timeIn;
                if (hoursWorked < 0) hoursWorked = 0;

                String[] dateParts = date.split("/");
                int month = Integer.parseInt(dateParts[0]);
                int day = Integer.parseInt(dateParts[1]);
                
                if(month != 6) continue;

                for (int i = 0; i < count; i++) {

                    if (empID.equals(employeeID[i])) {

                        double dailyPay = hoursWorked * hourlyRate[i];

                        if (day <= 15) {
                            firstCutoff[i] += dailyPay;
                        } else {
                            secondCutoff[i] += dailyPay;
                        }

                        break;
                    }
                }
            }

            attReader.close();

            // DISPLAY RESULTS
            for (int i = 0; i < count; i++) {

                System.out.println("Employee #: " + employeeID[i]);
                System.out.println("Name: " + employeeName[i]);
                System.out.printf("1st Cutoff Gross Salary: %.2f\n", firstCutoff[i]);
                firstCutOffNetpay=firstCutoff[i];
                System.out.printf("1st Cutoff Net Salary: %.2f\n", firstCutOffNetpay);
                
                System.out.printf("2nd Cutoff Gross Salary: %.2f\n", secondCutoff[i]);
                calculateNet2(firstCutoff[i], secondCutoff[i]);
                System.out.println("----------------------------");
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    public static double convertToHours(String time) {

        String[] parts = time.split(":");

        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        return hours + (minutes / 60.0);
    }
    
        //Netpay2Method
    public static double calculateNet2(double gross1, double gross2)
    {
        double netpay2;
        double deductions;
        double totalDeductions;
        double pagibig;
        double philhealth; 
        double totalGross = gross1+gross2;
        double sssDeduction; 
        double  tax;
        double taxable;
      
        //Pagibig Deductions
     if(totalGross>=1000 && totalGross<=1500)
     {
        if((totalGross*.01)>=100)
           {
              pagibig= 100;
            }
         else pagibig= totalGross*.01;
        }
     else if (totalGross>1501)
        {
         if((totalGross*.02)>=100)
           {
             pagibig= 100;
            }
         else pagibig= totalGross*.02;
        }
        else pagibig=0;
        
        //Philhealth Deductions 
        if(totalGross==10000){philhealth= (totalGross*.03)/2;}
        else if (totalGross>=10000.01 && totalGross<=59999.99){philhealth=(totalGross*.03)/2;}
        else if (totalGross>=60000){philhealth= totalGross*.03;}
        else philhealth= 0;
        
        
//SSS Deduction
         if (totalGross < 3250) {
    sssDeduction = 135;
} else if (totalGross >= 24750) {
    sssDeduction = 1125;
} else {
    int bracket = (int)((totalGross - 3250) / 500);
    sssDeduction = 157.5 + (bracket * 22.5);
}
           //combined deduction of pagibig,philhealth and sss
        deductions=pagibig+philhealth+sssDeduction; 
     
          //taxable income after the total gross deductions    
        taxable= totalGross-deductions;

           //Tax   
         if(taxable<20832)
        {
        tax= 0;
        }
        else if (taxable>=20833 && taxable<33333)
        {
            tax= (taxable-2833)*.20;
        }
        else if (taxable>=33333 && taxable<66667)
        {
            tax= 2500+((taxable-3333)*.20);
        }
        else if (taxable>=66667 && taxable<166667)
        {
            tax= 10833+((taxable-66667)*.30);
        }
        else if (taxable>=166667 && taxable<666667)
        {
            tax= 40833.33+((taxable-166667)*.32);
        } 
        else 
            tax= 2008333.33+((taxable-666667)*.35);
         
        
            //2nd Cut-off Netpay   
          totalDeductions=deductions+tax;
          netpay2=gross2-( totalDeductions);   
        
          //print out
        System.out.println("Total Gross:"+String.format("%.2f",totalGross));
        System.out.println("Taxable Income:"+String.format("%.2f",taxable));
        System.out.println("Deductions:");
        System.out.println("\tPagibig:"+String.format("%.2f",pagibig));
        System.out.println("\tPhilhealth:"+String.format("%.2f",philhealth));
        System.out.println("\tSSS:"+String.format("%.2f",sssDeduction));
        System.out.println("\tTax:"+String.format("%.2f",tax));
        System.out.println("Total Deductions:"+String.format("%.2f",totalDeductions));  
        System.out.println("Net pay 2nd Cut-off:"+String.format("%.2f",netpay2));
        
         

      
        return netpay2;   
    }
}
