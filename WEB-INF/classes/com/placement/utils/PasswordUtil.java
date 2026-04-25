package com.placement.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for BCrypt password hashing and verification.
 */
public class PasswordUtil {

    private static final int LOG_ROUNDS = 12;

    /** Hashes a plain-text password using BCrypt. */
    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(LOG_ROUNDS));
    }

    /** Verifies a plain-text password against a BCrypt hash. */
    public static boolean verify(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}