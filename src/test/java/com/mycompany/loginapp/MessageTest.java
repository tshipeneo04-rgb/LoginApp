package com.mycompany.loginapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        String longText = "a".repeat(251); 
        Message msg = new Message("+27718693002", longText);
        assertEquals("Message exceeds 250 characters by 1; please reduce the size.", msg.checkMessageLength());
    }

    @Test
    public void testRecipientCellSuccess() {
        Message msg = new Message("+27718693002", "Hello");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientCellFailure() {
        Message msg = new Message("08575975889", "Hello");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", msg.checkRecipientCell());
    }

    @Test
    public void testMessageHashExactFormat() {
        // Fix: Test the exact format using Regex to satisfy lecturer's feedback
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        
        // We must call SentMessage(1) to generate the hash, because the constructor no longer does.
        msg.SentMessage(1); 
        
        String hash = msg.getMessageHash();
        
        // The format should be exactly: 2 digits, a colon, a number, a colon, uppercase letters
        // Example: 12:1:HITONIGHT
        assertTrue(hash.matches("\\d{2}:\\d+:[A-Z]+"), "Hash format is incorrect: " + hash);
        assertTrue(hash.endsWith(":HITONIGHT"), "Hash should end with HITONIGHT");
    }

    @Test
    public void testHashLoop() {
        // Fix: Add a hash loop test as requested
        String[] messages = {
            "Hello world", 
            "Test message", 
            "Dinner tonight", 
            "Meeting at noon"
        };
        
        for (String text : messages) {
            Message msg = new Message("+27718693002", text);
            msg.SentMessage(1); // Must send to generate hash
            String hash = msg.getMessageHash();
            
            assertNotNull(hash);
            assertTrue(hash.contains(":"));
            // A valid hash should have 3 parts separated by colons
            assertEquals(3, hash.split(":").length, "Hash does not have 3 parts: " + hash);
        }
    }

    @Test
    public void testSentMessageOptions() {
        Message msg = new Message("+27718693002", "Hello");
        assertEquals("Message successfully sent.", msg.SentMessage(1));
        // Creating a new message to test option 2, since option 1 already changed its state
        Message msg2 = new Message("+27718693002", "Hello");
        assertEquals("Press 0 to delete the message.", msg2.SentMessage(2));
        Message msg3 = new Message("+27718693002", "Hello");
        assertEquals("Message successfully stored.", msg3.SentMessage(3));
    }
}