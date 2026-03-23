
package MotorPhPayrollSystem;

import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
        
public class MotorPhPayrollSystem 
{
    // Used for checking login
    static String EMPLOYEE_USERNAME = "employee";       // Username for employee access
    static String PAYROLL_USERNAME = "payroll_staff";   // Username for payroll staff access
    static String PASSWORD = "12345";                   // Default password for all users

    // FILE PATHS (dynamic)
    static String employeeFile = "MotorPH_Employee Data - Employee Details.csv";
    static String attendanceFile = "MotorPH_Employee Data - Attendance Record.csv";
  
    // COLUMN INDEXES FOR EMPLOYEE RECORD
    static int COL_EMP_NO = 0;
    static int COL_LAST_NAME = 1;
    static int COL_FIRST_NAME = 2;
    static int COL_BIRTHDAY = 3;
    static int COL_HOURLY_RATE = 18;
    
    // RESULT INDEXES FROM computeMonthlySalary method
    static int RES_FIRST_HOURS = 0;
    static int RES_SECOND_HOURS = 1;
    static int RES_FIRST_GROSS = 2;
    static int RES_SECOND_GROSS = 3;
    static int RES_TOTAL_GROSS = 4;
    static int RES_PAGIBIG = 5;
    static int RES_PHILHEALTH = 6;
    static int RES_SSS = 7;
    static int RES_TAXABLE_INCOME = 8;
    static int RES_TAX = 9;
    static int RES_TOTAL_DEDUCTIONS = 10;
    static int RES_SECOND_NET = 11;
    
    public static void main(String[] args)
    {       
         try (Scanner sc = new Scanner(System.in))
         {
            // Step 1: Ask for username and password
            System.out.print("Enter username: ");
            String username = sc.nextLine().trim();

            System.out.print("Enter password: ");
            String password = sc.nextLine().trim();

            // Step 2: Validate credentials
            if (!isValidLogin(username, password))
            {
                System.out.println("Incorrect username and/or password.");
                System.exit(0); // terminate program
            }

            // Step 3: Display menu based on username
            if (EMPLOYEE_USERNAME.equals(username))
            {
                handleEmployeeMenu(sc);
            }
            else if (PAYROLL_USERNAME.equals(username))
            {
                handlePayrollStaffMenu(sc);
            }

            System.out.println("Program terminated.");
        }
        catch (Exception e)
        {
            System.out.println("An error occurred. Program will exit.");
        }
    }
        
    // Checks if username and password are valid
    public static boolean isValidLogin(String username, String password)
    {
        return PASSWORD.equals(password)
                && (EMPLOYEE_USERNAME.equals(username)
                || PAYROLL_USERNAME.equals(username));
    }
    
    // Menu for employee role
    public static void handleEmployeeMenu(Scanner sc)
    {
        // Following are employee logic
        printNewLine();
        System.out.println("Options:");
        System.out.println("1. Enter your employee number");
        System.out.println("2. Exit the program");
        System.out.print("Choose an option: ");
        
        int choice = readChoice(sc); // reads the input and returns the value
        
        switch (choice) 
        {
            case 1 -> showOneEmployeeDetails(sc); // Show one employee details
            case 2 -> System.out.println("Program terminated.");
            default -> System.out.println("Invalid option. Try again.");
        }
        
        System.exit(0);         
    }
            
    // Menu for payroll staff
    public static void handlePayrollStaffMenu(Scanner sc)
    {
        // Following are payroll_staff logic
        printNewLine();
        System.out.println("Welcome, payroll staff!");     
        
        System.out.println("\nOptions:");
        System.out.println("1. Process payroll");
        System.out.println("2. Exit the program");
        System.out.print("Choose an option: ");
        
        int firstChoice = readChoice(sc); // reads the input and returns the value
        
        switch (firstChoice) 
        {
            case 1 -> {
                System.out.println("\nSub-options:");
                System.out.println("1. One employee");
                System.out.println("2. All employees");
                System.out.println("3. Exit the program");
                System.out.print("Choose an option: ");

                int secondChoice = readChoice(sc); // reads the input and returns the value

                switch (secondChoice) 
                {
                    case 1 -> {
                        // Show one employee Details and returns employee record
                        String[] employee = showOneEmployeeDetails(sc);
                        if(employee != null)
                        {                                               
                            // Loop through months June to December
                            for (int month = 6; month <= 12; month++) {
                                double[] payrollSummary = computeMonthlySalary(employee, month);
                                displayMonthlySalary(payrollSummary, month);
                            }
                        }
                        else
                        {
                            System.out.println("Employee number does not exist.");
                        }
                    }
                    case 2 -> showAllEmployeesPayroll();
                    case 3 -> System.out.println("Program terminated.");
                    default -> System.out.println("Invalid option. Program terminated.");
                }
            }
            case 2 -> System.out.println("Program terminated.");
            default -> System.out.println("Invalid option. Program terminated.");
        }

        System.exit(0);        
    }    
    
    // Reads user choice and if invalid integer, program will exit
    public static int readChoice(Scanner sc)
    {
        if (!sc.hasNextInt()) {
            System.out.println("Invalid option. Program will exit.");
            System.exit(0);
        }

        int choice = sc.nextInt();
        sc.nextLine(); // clear newline

        return choice;
    }

    // Ask user for employee number
    public static String askEmployeeNumber(Scanner sc)
    {
        System.out.print("Enter Employee #: ");
        return sc.nextLine();
    }
    
    // Shows employee details
    // Gets the employee number, finds and then prints the details
    public static String[] showOneEmployeeDetails(Scanner sc)
    {
        // Gets the employee number
        String employeeNumber = askEmployeeNumber(sc);

        // Find employee record
        String[] employee = findEmployeeByNumber(employeeNumber);

        if (employee == null) {
            System.out.println("Employee number does not exist.");
            return null; // ends method if null
        }
        
        // Display employee details
        displayOneEmployeeDetails(employee);
            
        return employee; 
    }
    
    // Displays the employee details
    public static void displayOneEmployeeDetails(String[] emp)
    {
        System.out.println();
        printSeparator();
        printText("Employee #", emp[COL_EMP_NO]);
        printText("Employee Name", emp[COL_LAST_NAME] + ", " + emp[COL_FIRST_NAME]);
        printText("Birthday", emp[COL_BIRTHDAY]);   
        printSeparator();
    }
    
    // Format text value and print
    public static void printText(String label, String value)
    {
        System.out.printf("%-20s : %s%n", label, value);
    }

    // Format double value and print
    public static void printDouble(String label, double value)
    {
        System.out.printf("%-20s : %.2f%n", label, value);
    }
    
    // Prints separator line
    public static void printSeparator()
    {
        System.out.println("========================================");
    }
    
        // Prints separator line
    public static void printNewLine()
    {
        System.out.println();
    }
    
    // Get month name
    public static String getMonthName(int month)
    {
        String[] months = {"", "", "", "", "", "", "June", "July", "August", "September", "October", "November", "December"};
        return months[month];
    }

    // Extract month
    public static int getMonthFromDate(String date)
    {
        return Integer.parseInt(date.split("/")[0]);
    }

    // Extract day
    public static int getDayFromDate(String date) {
        return Integer.parseInt(date.split("/")[1]);
    }
    
    // Gets year from date
    public static int getYearFromDate(String date) {
        return Integer.parseInt(date.split("/")[2]);
    }

    // Finds employee by number
    public static String[] findEmployeeByNumber(String employeeNumber)
    {
        ArrayList<String[]> list = readCsvFile(employeeFile);

        for (String[] employee : list) {
            if (employee[COL_EMP_NO].equals(employeeNumber)) {
                return employee;
            }
        }

        return null;
    }
    
    // Reads any CSV file
    public static ArrayList<String[]> readCsvFile(String fileName)
    {
        ArrayList<String[]> list = new ArrayList<>();

        // Creates file path with the current folder
        String filePath = getFilePath(fileName);

        // Reads the file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {

                // skip first line since header
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] data = parseCsvLine(line);
                list.add(data);
            }
        }
        catch (IOException e)
        {
            System.out.println("Error reading file: " + fileName);
        }

        return list;
    }
    
    // Finds the file in common project locations inside NetBeans
    public static String getFilePath(String fileName)
    {
        File file1 = new File(fileName);
        if (file1.exists()) {
            return file1.getPath();
        }

        File file2 = new File("src/" + fileName);
        if (file2.exists()) {
            return file2.getPath();
        }

        File file3 = new File("src/MotorPhPayrollSystem/" + fileName);
        if (file3.exists()) {
            return file3.getPath();
        }

        return "";
    }
    
    // Splits CSV line into values while handling the commas inside quotes
    public static String[] parseCsvLine(String line)
    {
        ArrayList<String> values = new ArrayList<>();
        String current = ""; // Store the current value while reading characters.
        boolean inQuotes = false; // Checks if current value is inside quotes

        // Read the line one character at a time.
        for (int i = 0; i < line.length(); i++) {

            char c = line.charAt(i);

            // check if it's inside quotes
            if (c == '"') {
                inQuotes = !inQuotes;
            }
            // split only if it's comma and not inside the quotes
            else if (c == ',' && !inQuotes) {
                values.add(current);
                current = "";
            }
            // add the character to the current value
            else {
                current += c;
            }
        }

        // add the last value
        values.add(current);

        // Converts the ArrayList to a String array
        return values.toArray(new String[0]);
    }
    
    // Computes salary for one employee for one month in 2024 only
    public static double[] computeMonthlySalary(String[] employee, int month)
    {
        ArrayList<String[]> attendanceList = readCsvFile(attendanceFile);
        
        // Get the employee number from employee record
        String employeeNumber = employee[COL_EMP_NO];
         
        double firstCutoffHours = 0;
        double secondCutoffHours = 0;
        double firstCutoffGross = 0;
        double secondCutoffGross = 0;

        // Get the hourly rate from the employee record
        double hourlyRate = Double.parseDouble(employee[COL_HOURLY_RATE].replace(",", "").replace("\"", ""));

        for (String[] attendance : attendanceList) {

            // Check if attendance record belongs to the employee
            if (!attendance[0].equals(employeeNumber)) {
                continue;
            }

            int recordMonth = getMonthFromDate(attendance[3]);
            int recordDay = getDayFromDate(attendance[3]);
            int recordYear = getYearFromDate(attendance[3]);

            // Check if record is only for 2024
            if (recordYear != 2024) {
                continue;
            }
        
            // Check if attendance record is for the given month
            if (recordMonth != month) {
                continue;
            }

            double workedHours = computeWorkedHours(attendance[4], attendance[5]);
            double grossPay = workedHours * hourlyRate;

            // add values to 1st or 2nd cutoff
            if (recordDay <= 15) {
                firstCutoffHours += workedHours;
                firstCutoffGross += grossPay;
            } else {
                secondCutoffHours += workedHours;
                secondCutoffGross += grossPay;
            }                    
        }

        double totalGross = firstCutoffGross + secondCutoffGross; // total monthly gross is the gross from first cut-off and second cut-off
        double pagibig = calculatePagibig(totalGross); // computes the pag-ibig contribution for deduction
        double philhealth = calculatePhilhealth(totalGross); // computes the Philhealth contribution for deduction
        double sss = calculateSss(totalGross); // computes the SSS contribution for deduction
        
        double taxableIncome = totalGross - ( pagibig + philhealth + sss ); // Taxable income is gross salary minus mandatory contributions. 
        
        double tax = calculateTax(taxableIncome); // computes the Tax withheld from the taxable Income
        
        double totalDeductions = pagibig + philhealth + sss + tax; // Total deductions is the sum of all contributions and tax.
        double secondCutoffNet = secondCutoffGross - totalDeductions; // Second cut-off net pay is subtracting second cut-off gross with the total deductions.”

        // return all computed values in one array
        return new double[] {
            firstCutoffHours,
            secondCutoffHours,
            firstCutoffGross,
            secondCutoffGross,
            totalGross,
            pagibig,
            philhealth,
            sss,
            taxableIncome,
            tax,
            totalDeductions,
            secondCutoffNet
        };       
    }

    // Display monthly salary of one employee
    public static void displayMonthlySalary(double[] payrollSummary, int month)
    {
        printNewLine();
        
        printSeparator();
        // cutoff details
        printText("Cutoff Date", getMonthName(month) + " 1 to " + getMonthName(month) + " 15");
        printDouble("Total Hours Worked", payrollSummary[RES_FIRST_HOURS]);
        printDouble("Gross Salary", payrollSummary[RES_FIRST_GROSS]);
        printDouble("Net Salary", payrollSummary[RES_FIRST_GROSS]); // First cut-off gross and net are the same since no deductions

        printText("Cutoff Date", getMonthName(month) + " 16 to " + getMonthName(month) + " 30 (Second payout includes all deductions)");
        printDouble("Total Hours Worked",payrollSummary[RES_SECOND_HOURS]);
        printDouble("Gross Salary", payrollSummary[RES_SECOND_GROSS]);

        
        // deductions
        printText("Each Deduction","");        
        printDouble("\tSSS", payrollSummary[RES_SSS]);
        printDouble("\tPhilHealth", payrollSummary[RES_PHILHEALTH]);
        printDouble("\tPag-IBIG", payrollSummary[RES_PAGIBIG]);
        printDouble("\tTax", payrollSummary[RES_TAX]);
        
        // final output
        printDouble("Total Deductions", payrollSummary[RES_TOTAL_DEDUCTIONS]);
        printDouble("Net Salary", payrollSummary[RES_SECOND_NET]);
        printSeparator();
    }
        
    // Shows payroll of all employees
    public static void showAllEmployeesPayroll()
    {
        ArrayList<String[]> employeeList = readCsvFile(employeeFile);

        // check if file has data
        if (employeeList.isEmpty()) {
            System.out.println("No employee records found.");
            return;
        }

        for (String[] employee : employeeList)
        {
            displayOneEmployeeDetails(employee);

            // show payroll from June to December
            for (int month = 6; month <= 12; month++) {
                double[] payrollSummary = computeMonthlySalary(employee, month);
                displayMonthlySalary(payrollSummary, month);
            }
        }
    }
    // Computes worked hours using company time rule
    public static double computeWorkedHours(String logIn, String logOut)
    {
        double timeIn = convertToHours(logIn);
        double timeOut = convertToHours(logOut);

        // Company work starts at 8:00 AM
        if (timeIn < 8.0) {
            timeIn = 8.0;
        }

        // Company work ends at 5:00 PM
        if (timeOut > 17.0) {
            timeOut = 17.0;
        }

        // Apply 30-minute rule to time in
        timeIn = applyThirtyMinuteRule(timeIn);

        double hoursWorked = timeOut - timeIn;

        // Deduct 1 hour break
        hoursWorked = hoursWorked - 1.0;

        // Do not allow negative hours
        if (hoursWorked < 0) {
            hoursWorked = 0;
        }

        return hoursWorked;
    }

    // Converts time to decimal hours
    public static double convertToHours(String time)
    {
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0].trim());
        int minute = Integer.parseInt(parts[1].trim());

        return hour + (minute / 60.0);
    }
    
    // Applies company 30-minute rule to login time
    public static double applyThirtyMinuteRule(double timeIn)
    {
        int hour = (int) timeIn;
        double minutes = timeIn - hour;

        // If minutes are less than 30, use whole hour
        if (minutes < 0.5) {
            return hour;
        }

        // If minutes are 30 or more, use half hour
        return hour + 0.5;
    }

    // Pag-IBIG computation
    public static double calculatePagibig(double gross)
    {      
        double pagibigContribution;
        
        // Computes the Pag-IBIG contribution
        if (gross >= 1000 && gross <= 1500) {
            pagibigContribution = gross * 0.01;
        } 
        else if (gross > 1500) {
            pagibigContribution = gross * 0.02;
        } 
        else {
            pagibigContribution = 0;
        }

        // Cap at 100
        if (pagibigContribution > 100) {
            pagibigContribution = 100;
        }
        
        return pagibigContribution;
    }
    
    // PhilHealth computation
    public static double calculatePhilhealth(double gross)
    {      
        double philhealthContribution;        
                
        // Computes the PhilHealth contribution
        if (gross >= 10000 && gross < 60000) {
            philhealthContribution = (gross * 0.03) / 2;
        } 
        else if (gross >= 60000) {
            philhealthContribution = gross * 0.03;
        } 
        else {
            philhealthContribution = 0;
        }
        
        return philhealthContribution;
    }

    // SSS computation 
    public static double calculateSss(double gross)
    {     
        double sssContribution;
        
        // Computes SSS based on salary bracket
        if (gross < 3250) {
            sssContribution = 135;
        } 
        else if (gross >= 24750) {
            sssContribution = 1125;
        } 
        else {
            int bracket = (int)((gross - 3250) / 500);
            sssContribution = 157.5 + (bracket * 22.5);
        }
        
        return sssContribution;
    }

    // Tax computation
    public static double calculateTax(double income)
    {
        double tax;
        // Compute tax based on taxable income
        if (income < 20833) {
            tax = 0;
        } 
        else if (income < 33333) {
            tax = (income - 20833) * 0.20;
        } 
        else if (income < 66667) {
            tax = 2500 + ((income - 33333) * 0.20);
        } 
        else if (income < 166667) {
            tax = 10833 + ((income - 66667) * 0.30);
        } 
        else if (income < 666667) {
            tax = 40833.33 + ((income - 166667) * 0.32);
        } 
        else {
            tax = 200833.33 + ((income - 666667) * 0.35);
        }

        return tax;
    }
}

