package bullscows;

import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input the length of the secret code:");
        String lengthStr = scanner.nextLine();
        if (!lengthStr.matches("\\d+")) {
            System.out.println("Error: \"" + lengthStr + "\" isn't a valid number.");
            return;
        }
        int length = Integer.parseInt(lengthStr);

        System.out.println("Input the number of possible symbols in the code:");
        String symbolsStr = scanner.nextLine();
        if (!symbolsStr.matches("\\d+")) {
            System.out.println("Error: \"" + symbolsStr + "\" isn't a valid number.");
            return;
        }
        int symbols = Integer.parseInt(symbolsStr);

        if (length > 36 || length <= 0) {
            System.out.println("Error: maximum length of the secret code is 36");
            return;
        }

        if (symbols < length || symbols < 1 || symbols > 36) {
            System.out.println("Error: it's not possible to generate code with a length of " + length + " with " + symbols + " unique symbols.");
            return;
        }

        String possibleSymbols = "0123456789abcdefghijklmnopqrstuvwxyz".substring(0, symbols);

        StringBuilder asterisks = new StringBuilder();
        for (int i = 0; i < length; i++) {
            asterisks.append("*");
        }

        String secretCode = generateSecretCode(length, symbols);
        String range = getRange(symbols);
        System.out.println("The secret is prepared: " + asterisks + " (" + range + ").");
        System.out.println("Okay, let's start a game!");

        int turn = 1;

        while (true) {
            System.out.println("Turn " + turn + ":");
            String guess = scanner.nextLine();

            if (guess.length() != length) {
                System.out.println("Error: incorrect length of guess.");
                return;
            }

            for (char ch : guess.toCharArray()) {
                if (possibleSymbols.indexOf(ch) == -1) {
                    System.out.println("Error: incorrect symbol in the guess.");
                    return;
                }
            }

            int bulls = 0;
            int cows = 0;

            for (int i = 0; i < length; i++) {
                char guessDigit = guess.charAt(i);
                if (secretCode.charAt(i) == guessDigit) {
                    bulls ++;
                } else if (secretCode.indexOf(guessDigit) != -1) {
                    cows++;
                }
            }

            StringBuilder gradeMessage = new StringBuilder("Grade: ");
            if (bulls > 0 && cows > 0) {
                gradeMessage.append(bulls).append(" bull(s) and ").append(cows).append(" cow(s)");
            } else if (bulls > 0) {
                gradeMessage.append(bulls).append(" bull(s)");
            } else if (cows > 0) {
                gradeMessage.append(cows).append(" cow(s)");
            } else {
                gradeMessage.append("None");
            }
            System.out.println(gradeMessage);

            if (bulls == length) {
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }

            turn++;
        }
    }

    public static String getRange(int symbols) {
        StringBuilder sb = new StringBuilder();
        if (symbols <= 10) {
            sb.append("0-").append(symbols - 1);
        } else {
            sb.append("0-9");
            if (symbols > 10) {
                sb.append(", a-").append((char) ('a' + symbols - 11));
            }
        }
        return sb.toString();
    }
    public static String generateSecretCode(int length, int symbols) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String possibleSymbols = "0123456789abcdefghijklmnopqrstuvwxyz".substring(0, symbols);

        while (sb.length() < length) {
            char ch = possibleSymbols.charAt(random.nextInt(symbols));
            if (sb.indexOf(String.valueOf(ch)) == -1) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
