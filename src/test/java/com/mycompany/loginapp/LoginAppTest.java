package com.mycompany.loginapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginAppTest {

    //  Tests for checkUserName 
    // Rule: Must contain "_" and length <= 5

    @Test
    public void testUsernameValid() {
        LoginApp app = new LoginApp(); // No BeforeEach, instantiated locally
        assertTrue(app.checkUserName("k_yl"), "k_yl should be valid");
    }

    @Test
    public void testUsernameValidEdgeCaseLength5() {
        LoginApp app = new LoginApp();
        assertTrue(app.checkUserName("a_bcd"), "a_bcd should be valid (exactly 5 chars)");
    }

    @Test
    public void testUsernameMissingUnderscore() {
        LoginApp app = new LoginApp();
        assertFalse(app.checkUserName("kyle"), "kyle should be invalid (no underscore)");
    }

    @Test
    public void testUsernameTooLong() {
        LoginApp app = new LoginApp();
        assertFalse(app.checkUserName("k_yle_long"), "k_yle_long should be invalid (too long)");
    }

    @Test
    public void testUsernameNull() {
        LoginApp app = new LoginApp();
        assertFalse(app.checkUserName(null), "null should be invalid");
    }

    // --- Tests for checkPasswordComplexity (5 tests) ---
    // Rule: length >= 8, has uppercase, has number, has special char

    @Test
    public void testPasswordValid() {
        LoginApp app = new LoginApp();
        assertTrue(app.checkPasswordComplexity("Password1!"), "Password1! should be valid");
    }

    @Test
    public void testPasswordTooShort() {
        LoginApp app = new LoginApp();
        assertFalse(app.checkPasswordComplexity("Pw1!"), "Pw1! should be invalid (too short)");
    }

    @Test
    public void testPasswordMissingUppercase() {
        LoginApp app = new LoginApp();
        assertFalse(app.checkPasswordComplexity("password1!"), "password1! should be invalid (no uppercase)");
    }

    @Test
    public void testPasswordMissingNumber() {
        LoginApp app = new LoginApp();
        assertFalse(app.checkPasswordComplexity("Password!"), "Password! should be invalid (no number)");
    }

    @Test
    public void testPasswordMissingSpecialChar() {
        LoginApp app = new LoginApp();
        assertFalse(app.checkPasswordComplexity("Password1"), "Password1 should be invalid (no special char)");
    }

    //  Tests for checkCellPhoneNumber 
    // Rule: Must match "+27" followed by 9 digits

    @Test
    public void testCellPhoneValid() {
        LoginApp app = new LoginApp();
        assertTrue(app.checkCellPhoneNumber("+27831234567"), "+27831234567 should be valid");
    }

    @Test
    public void testCellPhoneValidEdgeCase() {
        LoginApp app = new LoginApp();
        assertTrue(app.checkCellPhoneNumber("+27123456789"), "+27123456789 should be valid");
    }

    @Test
    public void testCellPhoneMissingPlus() {
        LoginApp app = new LoginApp();
        assertFalse(app.checkCellPhoneNumber("27831234567"), "27831234567 should be invalid (missing +)");
    }

    @Test
    public void testCellPhoneTooShort() {
        LoginApp app = new LoginApp();
        assertFalse(app.checkCellPhoneNumber("+278312345"), "+278312345 should be invalid (too short)");
    }

    @Test
    public void testCellPhoneNull() {
        LoginApp app = new LoginApp();
        assertFalse(app.checkCellPhoneNumber(null), "null should be invalid");
    }
}