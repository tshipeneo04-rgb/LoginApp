/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginapp;
import java.util.Scanner;


/**
 *
 * @author 27835
 */
public class LoginReg {
    
    public static void main(String[] args) {
        // Create an instance of LoginApp to use its methods
        LoginApp app = new LoginApp();
        Scanner input = new Scanner(System.in);

        System.out.println("--- REGISTRATION ---");
        
        System.out.println("Enter First Name: ");
        String fName = input.nextLine();
        
        System.out.println("Enter Last Name: ");
        String lName = input.nextLine();
        
        System.out.println("Enter Username: ");
        String uName = input.nextLine();
        
        System.out.println("Enter Password: ");
        String pass = input.nextLine();
        
        System.out.println("Enter Cell Phone Number (e.g., +27831234567/0831234567): ");
        String cell = input.nextLine();

        // Call the registration method
        String registrationMessage = app.registerUser(uName, pass, cell, fName, lName);
        System.out.println(registrationMessage);

        // Only proceed to login if registration was successful
        if (registrationMessage.equals("User registered successfully.")) {
            System.out.println("\n--- LOGIN ---");
            
            System.out.print("Enter username to login: ");
            String loginUsername = input.nextLine();
            
            System.out.print("Enter cell phone number to login: ");
            String loginCellnum = input.nextLine();
            
            System.out.print("Enter password to login: ");
            String loginPassword = input.nextLine();
            
            boolean isLoggedIn = app.loginUser(loginUsername, loginPassword, loginCellnum);
            System.out.println("\nLogin Status: \n" + app.returnLoginStatus(isLoggedIn));
        }

        input.close();
    }
}

