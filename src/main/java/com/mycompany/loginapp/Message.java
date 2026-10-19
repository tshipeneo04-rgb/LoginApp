package com.mycompany.loginapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import org.json.JSONObject;

public class Message {
     
    // Variables
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    
    // Static variables to track all messages across the app
    private static int totalMessagesSent = 0;
    private static ArrayList<Message> sentMessagesList = new ArrayList<>();

    // Constructor
    public Message(String recipient, String messageText) {
        this.recipient = recipient;
        this.messageText = messageText;
        
        // 1. Generate a random 10-digit ID
        Random rand = new Random();
        long id = (long) (rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        this.messageID = String.valueOf(id);
        
        // 2. Auto-increment message number
        totalMessagesSent++;
        this.messageNumber = totalMessagesSent;
        
        // 3. Generate the hash
        this.messageHash = createMessageHash();
    }

    // Check Message ID length
    public boolean checkMessageID() {
        return this.messageID.length() <= 10;
    }

    // Check Recipient Cell
    public String checkRecipientCell() {
        if (this.recipient.matches("^\\+27\\d{9}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Create Message Hash
    public String createMessageHash() {
        String firstTwoID = this.messageID.substring(0, 2);
        
        String[] words = this.messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        
        firstWord = firstWord.replaceAll("[^a-zA-Z]", "").toUpperCase();
        lastWord = lastWord.replaceAll("[^a-zA-Z]", "").toUpperCase();
        
        // Format: 00:0:HITONIGHT
        return firstTwoID + ":" + this.messageNumber + ":" + firstWord + lastWord;
    }

    // Check Message Length
    public String checkMessageLength() {
        if (this.messageText.length() > 250) {
            int excess = this.messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
        return "Message ready to send.";
    }

    // Handle User Choice (Send, Disregard, Store)
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessagesList.add(this); // Save to memory
                return "Message successfully sent.";
            case 2:
                totalMessagesSent--; // Remove from count
                return "Press 0 to delete the message.";
            case 3:
                storeMessage(); // Save to JSON file
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    // Print Messages (For Menu Option 2)
    public String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message msg : sentMessagesList) {
            sb.append("ID: ").append(msg.messageID)
              .append(" | Hash: ").append(msg.messageHash)
              .append(" | To: ").append(msg.recipient)
              .append(" | Msg: ").append(msg.messageText).append("\n");
        }
        return sb.toString();
    }

    // Return Total Count
    public int returnTotalMessagess() { 
        return totalMessagesSent;
    }

    // Store in JSON File
    public void storeMessage() {
        JSONObject json = new JSONObject();
        json.put("messageID", this.messageID);
        json.put("messageNumber", this.messageNumber);
        json.put("recipient", this.recipient);
        json.put("messageText", this.messageText);
        json.put("messageHash", this.messageHash);

        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(json.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving to JSON: " + e.getMessage());
        }
    }
    
    // Getters for Unit Testing
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getMessageText() { return messageText; }
    public String getRecipient() { return recipient; }
}