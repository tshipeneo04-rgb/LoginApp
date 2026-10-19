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
    public void testMessageHashCreation() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.getMessageHash().contains("HITONIGHT"));
    }

    @Test
    public void testSentMessageOptions() {
        Message msg = new Message("+27718693002", "Hello");
        assertEquals("Message successfully sent.", msg.SentMessage(1));
        assertEquals("Press 0 to delete the message.", msg.SentMessage(2));
        assertEquals("Message successfully stored.", msg.SentMessage(3));
    }
}