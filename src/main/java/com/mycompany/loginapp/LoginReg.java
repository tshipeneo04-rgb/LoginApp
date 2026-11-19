package com.mycompany.loginapp;

import java.util.Scanner;

public class LoginReg {

    public static void loadTestData() {
        // Reset everything to 0 before loading!
        Message.resetForTesting(); 

        Message m1 = new Message("+27834557896", "Did you get the cake?");
        m1.messageNumber = 1; m1.messageID = "1111111111"; m1.messageHash = m1.createMessageHash();
        m1.populateArrays("sent");
        
        Message m2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        m2.messageNumber = 2; m2.messageID = "2222222222"; m2.messageHash = m2.createMessageHash();
        m2.populateArrays("stored");
        
        Message m3 = new Message("+27834484567", "Yohoooo, I am at your gate.");
        m3.messageNumber = 3; m3.messageID = "3333333333"; m3.messageHash = m3.createMessageHash();
        m3.populateArrays("disregard");
        
        Message m4 = new Message("0838884567", "It is dinner time !");
        m4.messageNumber = 4; m4.messageID = "4444444444"; m4.messageHash = m4.createMessageHash();
        m4.populateArrays("sent");
        
        Message m5 = new Message("+27838884567", "Ok, I am leaving without you.");
        m5.messageNumber = 5; m5.messageID = "5555555555"; m5.messageHash = m5.createMessageHash();
        m5.populateArrays("stored");
    }

    public static void main(String[] args) {
        LoginApp app = new LoginApp(); 
        Scanner input = new Scanner(System.in);

        // --- PART 1: Registration & Login ---
        // (Assuming your existing code is here and works)
        // ... 
        
        boolean isLoggedIn = true; // For testing purposes, assume login works

        if (isLoggedIn) {
            loadTestData(); // Load the assignment test data
            Message.loadFromJson(); // RUBRIC REQUIREMENT: Load previous stored messages from file
            
            System.out.println("\nWelcome to QuickChat.");
            System.out.print("How many messages do you wish to enter? ");
            int numMessages = input.nextInt();
            input.nextLine(); 

            int messagesProcessed = 0;
            boolean running = true;

            while (running) {
                System.out.println("\n--- MAIN MENU ---");
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Stored Messages Menu (Part 3)");
                System.out.println("4) Quit"); 
                System.out.print("Choose: ");
                int menuChoice = input.nextInt();
                input.nextLine();

                switch (menuChoice) {
                    case 1:
                        // (Your existing Part 2 for loop message sending logic)
                        break;

                    case 2:
                        System.out.println("Coming Soon.");
                        break;
                        
                    case 3:
                        boolean subMenu = true;
                        while (subMenu) {
                            System.out.println("\n--- STORED MESSAGES ---");
                            System.out.println("a) Display sender & recipient");
                            System.out.println("b) Display longest message");
                            System.out.println("c) Search by Message ID");
                            System.out.println("d) Search by Recipient");
                            System.out.println("e) Delete by Message Hash");
                            System.out.println("f) Display Full Report");
                            System.out.println("g) Back to Main Menu");
                            System.out.print("Choice: ");
                            String sub = input.nextLine().toLowerCase();

                            switch (sub) {
                                case "a": System.out.println(Message.displayStoredSenderRecipient()); break;
                                case "b": System.out.println(Message.displayLongestStoredMessage()); break;
                                case "c": 
                                    System.out.print("Enter ID: "); 
                                    System.out.println(Message.searchByMessageID(input.nextLine())); break;
                                case "d": 
                                    System.out.print("Enter Recipient (+27...): "); 
                                    System.out.println(Message.searchByRecipient(input.nextLine())); break;
                                case "e": 
                                    System.out.print("Enter Hash: "); 
                                    System.out.println(Message.deleteByHash(input.nextLine())); break;
                                case "f": System.out.println(Message.displayFullReport()); break;
                                case "g": subMenu = false; break;
                                default: System.out.println("Invalid option.");
                            }
                        }
                        break;

                    case 4:
                        System.out.println("Goodbye!");
                        running = false;
                        break;
                }
            }
        }
        input.close();
    }
}