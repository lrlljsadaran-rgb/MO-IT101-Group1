/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package motorph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author levil
 */
public class EmployeeDetailsPresentation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String filePath = "employees.csv";
        
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                
                // Skip header row
                br.readLine();
                
                System.out.println("===== EMPLOYEE DETAILS =====\n");
                
                while ((line = br.readLine()) != null) {
                    
                    String[] data = line.split(",");
                    
                    String empNumber = data[0];
                    String lastName = data[1];
                    String firstname = data[2];
                    String birthday = data[3];
                    
                    String fullName = firstname + " " + lastName;
                    
                    System.out.println("Employee Number  :" + empNumber);
                    System.out.println("Employee Name    :" + fullName);
                    System.out.println("Birthday         :" + birthday);
                    System.out.println("-----------------------------");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
    
}
