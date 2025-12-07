package day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrashCompactor {

    private static List<List<String>> problems;

    private static void processInput(List<String> lines) {
        problems = new ArrayList<List<String>>();
        for (String line : lines) {
            line = line.trim();
            List<String> listOfOperandsAndOperators = Arrays.asList(line.split("\\s+"));
            problems.add(listOfOperandsAndOperators);
        }
    }

    private static long calculateSumOfOperations() {
        long totalSum = 0;
        int totalOperations = problems.get(0).size();
        for (int i = 0; i < totalOperations; i++) {
            String operator = problems.get(problems.size() - 1).get(i);
            long ans = operator.equalsIgnoreCase("*") ? 1 : 0;
            for (int j = 0; j < problems.size() - 1; j++) {
                if (operator.equalsIgnoreCase("*")) {
                    ans *= Long.parseLong(problems.get(j).get(i));
                } else {
                    ans += Long.parseLong(problems.get(j).get(i));
                }
            }
            totalSum += ans;
        }
        return totalSum;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("day6/input.txt"));
        processInput(lines);
        System.out.println(calculateSumOfOperations());
    }

}
