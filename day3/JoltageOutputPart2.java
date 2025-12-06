package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JoltageOutputPart2 {

    private static int findTotalJoltage(String[] banks) {
        int totalJoltage = 0;
        for (String bank : banks) {
            int maxJoltageSoFar = 0;
            // need the bank size >= 2
            if (bank.isEmpty() || bank.length() == 11) {
                continue;
            }
            int firstMaxJoltageBattery = Character.getNumericValue(bank.charAt(0));
            for (int i = 1; i < bank.length(); i++) {
                if (i != bank.length() - 1 && Character.getNumericValue(bank.charAt(i)) > firstMaxJoltageBattery) {
                    firstMaxJoltageBattery = Character.getNumericValue(bank.charAt(i));
                } else {
                    int currentJoltage = firstMaxJoltageBattery * 10 + Character.getNumericValue(bank.charAt(i));
                    maxJoltageSoFar = Math.max(currentJoltage, maxJoltageSoFar);
                }
            }
            totalJoltage += maxJoltageSoFar;
        }
        return totalJoltage;

    }

    public static void main(String[] args) throws IOException {
        // Read input from file
        List<String> lines = Files.readAllLines(Paths.get("day3/input.txt"));

        // Create banks array from input
        String[] banks = lines.toArray(new String[0]);

        System.out.println(findTotalJoltage(banks));
    }
}
