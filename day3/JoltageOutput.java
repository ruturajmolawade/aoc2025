package day3;

public class JoltageOutput {

    private static int findTotalJoltage(String[] banks) {
        int totalJoltage = 0;
        for (String bank : banks) {
            int maxJoltageSoFar = 0;
            // need the bank size >= 2
            if (bank.isEmpty() || bank.length() == 1) {
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

    public static void main(String[] args) {
        String[] banks = {
                "987654321111111",
                "811111111111119",
                "234234234234278",
                "818181911112111"
        };
        System.out.print(findTotalJoltage(banks));
    }
}
