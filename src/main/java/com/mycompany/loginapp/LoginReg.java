
package com.mycompany.loginapp;
import java.util.Scanner;


/**
 *
 * @author 27835
 */
public class LoginReg {
    
    public static void main(String[] args) {
       LoginApp app = new LoginApp();
    Scanner input = new Scanner(System.in);

    // --- PART 1: Registration & Login ---
    System.out.println("--- REGISTRATION ---");
    System.out.print("Enter First Name: "); String fName = input.nextLine();
    System.out.print("Enter Last Name: "); String lName = input.nextLine();
    System.out.print("Enter Username: "); String uName = input.nextLine();
    System.out.print("Enter Password: "); String pass = input.nextLine();
    System.out.print("Enter Cell Phone Number (e.g., +27831234567): "); String cell = input.nextLine();

    String regMsg = app.registerUser(uName, pass, cell, fName, lName);
    System.out.println(regMsg);

    boolean isLoggedIn = false;
    if (regMsg.equals("User registered successfully.")) {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Enter username: "); String loginUser = input.nextLine();
        System.out.print("Enter password: "); String loginPass = input.nextLine();
        isLoggedIn = app.loginUser(loginUser, loginPass, cell); 
    }

    // --- PART 2: QuickChat Messaging ---
    if (isLoggedIn) {
        System.out.println("\nWelcome to QuickChat.");
        System.out.print("How many messages do you wish to enter? ");
        int numMessages = input.nextInt();
        input.nextLine(); // Clear buffer
        
        int messagesProcessed = 0;
        boolean running = true;

        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose: ");
            int menuChoice = input.nextInt();
            input.nextLine();

            switch (menuChoice) {
                case 1:
                    if (messagesProcessed >= numMessages) {
                        System.out.println("You have reached your message limit.");
                        break;
                    }
                    
                    System.out.print("Enter recipient cell (+27...): ");
                    String recipient = input.nextLine();
                    System.out.print("Enter message: ");
                    String text = input.nextLine();
                    
                    Message msg = new Message(recipient, text);
                    
                    // Check rules before asking user what to do
                    String cellCheck = msg.checkRecipientCell();
                    String lengthCheck = msg.checkMessageLength();
                    System.out.println(cellCheck);
                    System.out.println(lengthCheck);
                    
                    if (cellCheck.equals("Cell phone number successfully captured.") && 
                        lengthCheck.equals("Message ready to send.")) {
                        
                        System.out.println("1) Send | 2) Disregard | 3) Store");
                        int action = input.nextInt();
                        input.nextLine();
                        
                        System.out.println(msg.SentMessage(action));
                        
                        if (action == 1) {
                            System.out.println("\n--- Details ---");
                            System.out.println("ID: " + msg.getMessageID());
                            System.out.println("Hash: " + msg.getMessageHash());
                            System.out.println("Recipient: " + msg.getRecipient());
                            System.out.println("Message: " + msg.getMessageText());
                            messagesProcessed++;
                        }
                    }
                    break;
                    
                case 2:
                    System.out.println("Coming Soon.");
                    break;
                    
                case 3:
                    Message dummy = new Message("+27000000000", "dummy");
                    System.out.println("Total messages sent: " + dummy.returnTotalMessagess());
                    System.out.println("Goodbye!");
                    running = false;
                    break;
            }
        }
    }
    input.close();
    }
}
