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

package motorphnet;
import java.io.*;
import java.time.Duration;
import java.util.Scanner;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class MotorPhNet 
{
    
   
    public static void main(String[] args) 
    {
        
        String fileEmployee = "C:\\Users\\malag\\OneDrive\\Documents\\NetBeansProjects\\MotorPhGross\\src\\motorphnet\\MotorPH_Employee Data - Employee Details.csv";
        String fileAttendance = "C:\\Users\\malag\\OneDrive\\Documents\\NetBeansProjects\\MotorPhGross\\src\\motorphnet\\MotorPH_Employee Data - Attendance Record.csv";
        
        String employeeNumber=getOneEmployee(fileEmployee);
        
        double rate=0;
        double dailyWage=0;
//daily rate
            try (BufferedReader reader = new BufferedReader(new FileReader(fileEmployee))) 
            {
             reader.readLine(); // skip header
                
                String line;
                while((line = reader.readLine())!= null)
                {
                    String[] dailyRate=line.split(",(?=(?:[^\"]\"[^\"]\")[^\"]$)");
                    if(dailyRate[0].equals(employeeNumber))
                    {
                        rate = Double.parseDouble(dailyRate[18]);
                        System.out.println("hourly rate:"+dailyRate[18]); 

                    }
                }
            } 
            catch(IOException e)
            {
            System.out.println("Error reading file");
            }
        
        
        
             //Hour worked
      
       try {
            BufferedReader reader = new BufferedReader(new FileReader(fileAttendance));
            

            String line;

            while((line = reader.readLine())!= null)
            {
                String[] employeeWorkingHours=line.split(",");
                if(employeeWorkingHours[0].equals(employeeNumber))
                {
                 System.out.println("\n******************");
                 System.out.println("Date:"+employeeWorkingHours[3]);
                 System.out.println( "Time in:"+employeeWorkingHours[4]+"   "+"Time out:"+employeeWorkingHours[5]);
                 double wH=workedHours(employeeWorkingHours[4],employeeWorkingHours[5]);
                  //dailywage
                 dailyWage = wH*rate;
                 System.out.println("Total Working Hours:"+wH);
                 System.out.println("Daily Wage"+dailyWage);
                 
                 
                 }
            }   

            reader.close();

        } catch(IOException e){
            System.out.println("Error reading file");
        }
        
       
      
     
        
       double gross1=10000; //gross1 variable for 1st cut off of the month
       
       double gross2=15000; //gross2 variable for 2nd cut off of the month
       
       calculateNet2(gross1,gross2);    
    }
    
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
    
    public static String getOneEmployee(String EmployeeFile){
          //Employee Scaner    
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Employee #: ");
        String employeeNumber = sc.nextLine(); 
        
         //Employee Record
      try {
            BufferedReader reader = new BufferedReader(new FileReader(EmployeeFile));

            String line;

            while((line = reader.readLine())!= null)
            {
                String[] employeeRecord=line.split(",");
                if(employeeRecord[0].equals(employeeNumber))
                {
                 System.out.println("******************");
                 System.out.println("Employee No.:"+employeeRecord[0]);
                 System.out.println( "Employee Name:"+employeeRecord[1]+", "+employeeRecord[2]);
                 System.out.println("Birthday:"+employeeRecord[3]);
                }
            }   

            reader.close();

        } catch(IOException e){
            System.out.println("Error reading file");
        }
      
 
         
           
     return employeeNumber; 
    }
     //No of Hours Worked
    public static double workedHours(String timeInString, String timeOutString){
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");
        LocalTime timeIn=LocalTime.parse(timeInString,timeFormat);
        LocalTime timeOut=LocalTime.parse(timeOutString,timeFormat);
       
        LocalTime maxTimeOut=LocalTime.of(17, 0);
        if(timeOut.isAfter(maxTimeOut)){
        timeOut=maxTimeOut;
        }
        
       double workedHours=Duration.between(timeIn, timeOut).toMinutes();
        double wh;
        if(workedHours>60){return wh=(workedHours-60)/60;}
        else return wh=workedHours/60;
        

     
    }
    
    
}
