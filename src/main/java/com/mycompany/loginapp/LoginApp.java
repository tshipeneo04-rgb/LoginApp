package com.mycompany.loginapp;


public class LoginApp {

    
    // Class variables 
    private String registeredUsername;
    private String registeredPassword;
    private String registeredCellnum;
    private String registeredFirstName;
    private String registeredLastName;

    //  Validation Methods 

    // Username must contain an underscore and be no more than 5 characters long
    public boolean checkUserName(String username) {
        if (username == null) {
            return false;
        }
        return username.contains("_") && username.length() <= 5;
    }

    // Password must be at least 8 characters, contain a capital letter, a number, and a special character
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        // Using standard regex to check for requirements
        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        // This checks for any character that is NOT a letter or a number (i.e., a special character)
        boolean hasSpecial = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*"); 

        return hasUppercase && hasNumber && hasSpecial;
    }

    // Cell phone must contain international code like +27 and be 10 digits long after
    public boolean checkCellPhoneNumber(String cellNumber) {
        if (cellNumber == null) {
            return false;
        }
        // This regex looks for "+27" followed by exactly 9 digits  
        // A common requirement is "+27" followed by 9 digits.
        return cellNumber.matches("^\\+27\\d{9}$");
    }

    //  Registration and Login Logic 

    public String registerUser(String username, String password, String cellNumber, String firstName, String lastName) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell phone number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
        
        // If all validations pass, register the user
        this.registeredUsername = username;
        this.registeredPassword = password;
        this.registeredCellnum = cellNumber;
        this.registeredFirstName = firstName;
        this.registeredLastName = lastName;
        
        return "User registered successfully.";
    }

    public boolean loginUser(String username, String password, String cellNumber) {
        if (username == null || password == null || cellNumber == null) {
            return false;
        }
        return username.equals(this.registeredUsername) && password.equals(this.registeredPassword) && cellNumber.equals(this.registeredCellnum);
    }

    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + this.registeredFirstName + " " + this.registeredLastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}