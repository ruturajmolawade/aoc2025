import java.util.*;

public class InvalidIDFinder {
 
    private static List<long[]> parseRanges(String[] inputRanges) {
        List<long[]> ranges = new ArrayList<>();
        for (String range : inputRanges) {
            String[] parts = range.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);
            ranges.add(new long[] {start, end});
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

    // Test 
    public static void main(String[] args) {
        String[] inputRanges = {
            "11-22",
            "95-115",
            "998-1012",
            "1188511880-1188511890",
            "222220-222224",
            "1698522-1698528",
            "446443-446449",
            "38593856-38593862",
            "565653-565659",
            "824824821-824824827",
            "2121212118-2121212124"
        };
        System.out.println("Sum of invalid IDs: " + sumInvalidIDs(inputRanges));
    }
}
