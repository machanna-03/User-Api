package com.getmyschool.common.utils;

import java.security.SecureRandom;
import java.util.UUID;

public class RandomGeneratorUtil {

	public static String getRandomUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Get 3 digit unique number for folder arrangement.
	 * 
	 * @return
	 */
	public static int getRandomDigit() {
		int min = 100;
		int max = 999;
		int randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);
		return randomInt;
	}

	/**
	 * Generated a password with length 10 and 1 char,1 digit, 1 UpperCase letter
	 * and 1 LowerCase letter
	 * 
	 * @return
	 */
	public static String getRandomPassword() {
		SecureRandom random = new SecureRandom();
		String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowercase = "abcdefghijklmnopqrstuvwxyz";
		String digits = "0123456789";

		// Ensure at least one character from each category
		StringBuilder password = new StringBuilder();
		password.append(uppercase.charAt(random.nextInt(uppercase.length())));
		password.append(lowercase.charAt(random.nextInt(lowercase.length())));
		password.append(digits.charAt(random.nextInt(digits.length())));

		String allCharacters = uppercase + lowercase + digits;

		// Generate the remaining characters
		for (int i = 4; i < 9; i++) {
			int randomIndex = random.nextInt(allCharacters.length());
			password.append(allCharacters.charAt(randomIndex));
		}

		// Shuffle the password to randomize the character order
		return shuffleString(password.toString());
	}

	private static String shuffleString(String input) {
		char[] characters = input.toCharArray();
		for (int i = characters.length - 1; i > 0; i--) {
			int randomIndex = new SecureRandom().nextInt(i + 1);
			char temp = characters[i];
			characters[i] = characters[randomIndex];
			characters[randomIndex] = temp;
		}
		return new String(characters);
	}

}
