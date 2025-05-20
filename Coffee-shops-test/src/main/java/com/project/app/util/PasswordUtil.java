package com.project.app.util;

// TODO: Implement proper password hashing using a library like BCrypt

public class PasswordUtil {

    /**
     * Hashes a plain text password.
     * @param plainPassword The plain text password.
     * @return The hashed password.
     */
    public static String hashPassword(String plainPassword) {
        // Placeholder implementation: return the plain password
        System.out.println("WARNING: Using placeholder password hashing!");
        return plainPassword;
    }

    /**
     * Verifies a plain text password against a hashed password.
     * @param plainPassword The plain text password.
     * @param hashedPassword The hashed password.
     * @return true if the password matches, false otherwise.
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        // Placeholder implementation: compare plain passwords
        System.out.println("WARNING: Using placeholder password verification!");
        return plainPassword.equals(hashedPassword);
    }
}
