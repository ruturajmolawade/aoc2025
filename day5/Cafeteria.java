package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Cafeteria {
    private static List<long[]> ranges;
    private static List<Long> ingredients;
    private static List<long[]> mergedRanges;

    private static void processInput(List<String> lines) {
        int index = 0;
        int totalSize = lines.size();
        String line = "";
        ranges = new ArrayList<>();
        // read ranges first and stop when a new line is encountered
        while (true) {
            line = lines.get(index++);
            if (line.isEmpty())
                break;
            String[] range = line.split("-");
            ranges.add(new long[] { Long.parseLong(range[0]), Long.parseLong(range[1]) });
        }

        // read ingredients
        ingredients = new ArrayList<>();
        while (index < totalSize) {
            ingredients.add(Long.parseLong(lines.get(index++)));
        }
    }

    private static void mergeRanges() {
        mergedRanges = new ArrayList<>();
        ranges.sort(new Comparator<long[]>() {
            @Override
            public int compare(long[] l1, long[] l2) {
                return (int) (l1[0] == l2[0] ? l1[1] - l2[1] : l1[0] - l2[0]);
            }
        });

        int index = 0;
        mergedRanges.add(ranges.get(index++));
        while (index < ranges.size()) {
            long[] current = ranges.get(index);
            long[] previous = mergedRanges.get(mergedRanges.size() - 1);
            // overlap
            if (current[0] <= previous[1]) {
                long[] mergedRange = new long[2];
                mergedRange[0] = Math.min(current[0], previous[0]);
                mergedRange[1] = Math.max(current[1], previous[1]);
                mergedRanges.remove(mergedRanges.size() - 1);
                mergedRanges.add(mergedRange);
            } else {
                mergedRanges.add(current);
            }
            index++;
        }
        return;
    }

    private static int countNumberOfIngredientsAvailable() {

        int ingredientsAvailable = 0;
        for (long ingredient : ingredients) {
            if (binarySearch(ingredient))
                ingredientsAvailable++;
        }
        return ingredientsAvailable;
    }

    private static boolean binarySearch(long ingredient) {

        int left = 0;
        int right = mergedRanges.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long[] mid_range = mergedRanges.get(mid);
            if (ingredient >= mid_range[0] && ingredient <= mid_range[1])
                return true;
            else if (ingredient > mid_range[1])
                left = mid + 1;
            else
                right = mid - 1;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        // Read input from file
        List<String> lines = Files.readAllLines(Paths.get("day5/input.txt"));
        processInput(lines);
        mergeRanges();
        System.out.println(countNumberOfIngredientsAvailable());

    }

}
