package day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrashCompactorP2 {

    private static List<List<String>> operands;
    private static List<String> operators;

    private static void collectOperators(List<String> lines) {
        String operatorsLine = lines.get(lines.size() - 1);
        operatorsLine = operatorsLine.trim();
        operators = Arrays.asList(operatorsLine.split("\\s+"));

    }

    private static void processInput(List<String> lines) {
        operands = new ArrayList<>();
        List<String> operand = new ArrayList<>();
        for (int j = lines.get(0).length() - 1; j >= 0; j--) {
            StringBuilder sb = new StringBuilder();
            boolean allSpaces = true;
            for (int i = 0; i < lines.size() - 1; i++) {
                char ch = lines.get(i).charAt(j);
                if (!Character.isSpaceChar(ch)) {
                    sb.append(ch);
                    allSpaces = false;
                }
            }
            if (allSpaces) {
                operands.add(0, new ArrayList<>(operand));
                operand.clear();
            } else {
                operand.add(sb.toString());
            }
        }
        operands.add(0, new ArrayList<>(operand));

    }

    private static long calculateSumOfOperations() {
        long totalSum = 0;
        for (int i = 0; i < operands.size(); i++) {
            long ans = operators.get(i).equalsIgnoreCase("*") ? 1 : 0;
            for (String num : operands.get(i)) {
                if (operators.get(i).equalsIgnoreCase("*")) {
                    ans *= Long.parseLong(num);
                } else {
                    ans += Long.parseLong(num);
                }
            }
            totalSum += ans;
        }
        return totalSum;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("day6/input.txt"));
        collectOperators(lines);
        processInput(lines);

        operands.forEach(operand -> System.out.print(operand));
        System.out.print("\n");
        operators.forEach(operator -> System.out.print(operator));
        System.out.println(calculateSumOfOperations());
    }

}
