package day2;

import java.io.*;
import java.util.*;

public class InvalidIDFinderPart1 {

    private static List<long[]> parseRanges(String[] inputRanges) {
        List<long[]> ranges = new ArrayList<>();
        for (String range : inputRanges) {
            String[] parts = range.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);
            ranges.add(new long[] { start, end });
        }
        return ranges;
    }

    // find the maximum number of digits from given range
    // for e.g. (1,100), (200, 50000) => function returns value 5
    private static int findMaxDigits(List<long[]> ranges) {
        int maxDigits = 0;
        for (long[] range : ranges) {
            maxDigits = Math.max(maxDigits, String.valueOf(range[1]).length());
        }
        return maxDigits;
    }

    // function to collect all invalid IDs
    private static Set<Long> findInvalidIds(int maxDigits) {
        Set<Long> invalidIds = new HashSet<>();
        for (int digits = 2; digits <= maxDigits; digits += 2) {
            int halfLen = digits / 2;
            long minHalf = 1;
            for (int i = 1; i < halfLen; i++) {
                minHalf *= 10;
            }
            long maxHalf = minHalf * 10 - 1;
            for (long half = minHalf; half <= maxHalf; half++) {
                String halfStr = String.valueOf(half);
                String idStr = halfStr + halfStr;
                long id = Long.parseLong(idStr);
                invalidIds.add(id);
            }
        }
        return invalidIds;
    }

    // Check if an ID is within any of the given ranges
    private static boolean idInRange(long id, List<long[]> ranges) {
        for (long[] range : ranges) {
            if (range[0] <= id && id <= range[1]) {
                return true;
            }
        }
        return false;
    }

    // function to sum all invalid IDs in the ranges
    public static long sumInvalidIDs(String[] inputRanges) {
        List<long[]> ranges = parseRanges(inputRanges);
        int maxDigits = findMaxDigits(ranges);
        Set<Long> invalidIds = findInvalidIds(maxDigits);
        long sum = 0;
        for (long id : invalidIds) {
            if (idInRange(id, ranges)) {
                sum += id;
            }
        }
        return sum;
    }

    private static String[] readInputRanges(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        reader.close();
        return content.toString().trim().split(",");
    }

    // Test
    public static void main(String[] args) {
        try {
            String[] inputRanges = readInputRanges("day2/input.txt");

            long startTime = System.nanoTime();
            long result = sumInvalidIDs(inputRanges);
            long endTime = System.nanoTime();

            long durationNanos = endTime - startTime;
            double durationMillis = durationNanos / 1_000_000.0;

            System.out.println("Sum of invalid IDs: " + result);
            System.out.println("Execution time: " + durationMillis + " ms");
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
