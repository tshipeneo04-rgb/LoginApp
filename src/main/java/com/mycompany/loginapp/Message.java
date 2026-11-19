package com.mycompany.loginapp;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Message {
    // Variables (Not private so Test Data loader can access them)
    String messageID;
    int messageNumber;
    String recipient;
    String messageText;
    String messageHash;

    private static int totalMessagesSent = 0;
    private static ArrayList<Message> sentMessagesList = new ArrayList<>();

    // --- PART 3: PARALLEL ARRAYS ---
    public static ArrayList<String> sentMessagesArray = new ArrayList<>();
    public static ArrayList<String> disregardedMessagesArray = new ArrayList<>();
    public static ArrayList<String> storedMessagesArray = new ArrayList<>();
    public static ArrayList<String> messageHashesArray = new ArrayList<>();
    public static ArrayList<String> messageIDsArray = new ArrayList<>();

    // --- NEW: Reset method for Unit Tests ---
    public static void resetForTesting() {
        totalMessagesSent = 0;
        sentMessagesList.clear();
        sentMessagesArray.clear();
        disregardedMessagesArray.clear();
        storedMessagesArray.clear();
        messageHashesArray.clear();
        messageIDsArray.clear();
    }

    public Message(String recipient, String messageText) {
        this.recipient = recipient;
        this.messageText = messageText;
        Random rand = new Random();
        this.messageID = String.format("%010d", rand.nextInt(1000000000));
    }

    // --- PART 2 METHODS ---
    public boolean checkMessageID() { return this.messageID.length() <= 10; }
    
    public String checkRecipientCell() {
        return this.recipient.matches("^\\+27\\d{9}$") || this.recipient.matches("^0\\d{9}$") ? 
               "Cell phone number successfully captured." : 
               "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }
    
    public String createMessageHash() {
        String firstTwoID = this.messageID.substring(0, 2);
        String[] words = this.messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0].replaceAll("[^a-zA-Z]", "").toUpperCase() : "";
        String lastWord = words.length > 1 ? words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase() : firstWord;
        return firstTwoID + ":" + this.messageNumber + ":" + firstWord + lastWord;
    }
    
    public String checkMessageLength() {
        return this.messageText.length() > 250 ? "Message exceeds 250 characters by " + (this.messageText.length() - 250) + "; please reduce the size." : "Message ready to send.";
    }
    
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                this.messageNumber = totalMessagesSent;
                this.messageHash = createMessageHash();
                sentMessagesList.add(this); 
                populateArrays("sent");
                return "Message successfully sent.";
            case 2:
                populateArrays("disregard");
                return "Press 0 to delete the message.";
            case 3:
                totalMessagesSent++;
                this.messageNumber = totalMessagesSent;
                this.messageHash = createMessageHash();
                storeMessage(); 
                populateArrays("stored");
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }
    
    public static int returnTotalMessagess() { return totalMessagesSent; }

    // --- JSON HANDLING ---
    public void storeMessage() {
        JSONObject json = new JSONObject();
        json.put("messageID", this.messageID);
        json.put("messageNumber", this.messageNumber);
        json.put("recipient", this.recipient);
        json.put("messageText", this.messageText);
        json.put("messageHash", this.messageHash);

        JSONArray jsonArray = new JSONArray();
        try (FileReader reader = new FileReader("messages.json")) {
            jsonArray = new JSONArray(new JSONTokener(reader));
        } catch (Exception e) { /* File doesn't exist yet */ }
        
        jsonArray.put(json);
        try (FileWriter file = new FileWriter("messages.json")) {
            file.write(jsonArray.toString(4));
        } catch (IOException e) { System.out.println("Error saving to JSON: " + e.getMessage()); }
    }

    public static void loadFromJson() {
        try (FileReader reader = new FileReader("messages.json")) {
            JSONArray jsonArray = new JSONArray(new JSONTokener(reader));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String id = obj.getString("messageID");
                String hash = obj.getString("messageHash");
                String rec = obj.getString("recipient");
                String txt = obj.getString("messageText");
                
                String fullMessage = "ID: " + id + " | Hash: " + hash + " | To: " + rec + " | Msg: " + txt;
                
                if(!messageIDsArray.contains(id)) {
                    storedMessagesArray.add(fullMessage);
                    messageIDsArray.add(id);
                    messageHashesArray.add(hash);
                }
            }
        } catch (Exception e) {
            System.out.println("No previous JSON file found or error reading it.");
        }
    }

    // --- PART 3 METHODS ---
    public void populateArrays(String flag) {
        String fullMessage = "ID: " + this.messageID + " | Hash: " + this.messageHash + 
                             " | To: " + this.recipient + " | Msg: " + this.messageText;
        
        messageIDsArray.add(this.messageID);
        messageHashesArray.add(this.messageHash);

        if (flag.equals("sent")) sentMessagesArray.add(fullMessage);
        else if (flag.equals("stored")) storedMessagesArray.add(fullMessage);
        else if (flag.equals("disregard")) disregardedMessagesArray.add(fullMessage);
    }

    public static String displayStoredSenderRecipient() {
        StringBuilder sb = new StringBuilder("--- Sender and Recipient of Stored Messages ---\n");
        for (String msg : storedMessagesArray) {
            sb.append(msg).append("\n");
        }
        return sb.toString();
    }

    public static String displayLongestStoredMessage() {
        if (storedMessagesArray.isEmpty()) return "No stored messages.";
        String longest = "";
        for (String msg : storedMessagesArray) {
            if (msg.length() > longest.length()) longest = msg;
        }
        return "Longest Stored Message:\n" + longest;
    }

    public static String searchByMessageID(String id) {
        int index = messageIDsArray.indexOf(id);
        
        if (index != -1) {
            for (String msg : storedMessagesArray) {
                if (msg.contains("ID: " + id)) {
                    return "Found: " + msg;
                }
            }
        }
        return "Message ID not found.";
    }

    public static String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder("Messages for " + recipient + ":\n");
        boolean found = false;
        for (String msg : storedMessagesArray) {
            if (msg.contains(recipient)) {
                sb.append(msg).append("\n");
                found = true;
            }
        }
        return found ? sb.toString() : "No stored messages found for this recipient.";
    }

    public static String deleteByHash(String hash) {
        int hashIndex = messageHashesArray.indexOf(hash);
        
        if (hashIndex != -1) {
            storedMessagesArray.removeIf(msg -> msg.contains("Hash: " + hash));
            messageHashesArray.remove(hashIndex);
            messageIDsArray.remove(hashIndex);
            
            return "Message with hash " + hash + " successfully deleted.";
        }
        return "Message hash not found.";
    }

    public static String displayFullReport() {
        StringBuilder sb = new StringBuilder("--- FULL STORED MESSAGES REPORT ---\n");
        if (storedMessagesArray.isEmpty()) return "No stored messages.";
        for (String msg : storedMessagesArray) {
            sb.append(msg).append("\n-------------------\n");
        }
        return sb.toString();
    }

    // Getters
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getMessageText() { return messageText; }
    public String getRecipient() { return recipient; }
}