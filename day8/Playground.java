package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Playground {

    private static long[][] boxes;
    private static long[][][] pairs;
    private static Map<String, String> boxToRegionMapping;
    private static Map<String, Integer> regionSize;
    private static int totalRegionCount = 0;
    private static PriorityQueue<Integer> largestJunctions = new PriorityQueue<>((a, b) -> a - b);

    public static void processInput(List<String> lines) {
        boxes = new long[lines.size()][3];
        int i = 0;
        for (String line : lines) {
            boxes[i++] = Arrays.stream(line.split(",")).mapToLong(s -> {
                try {
                    return Long.parseLong(s);
                } catch (NumberFormatException e) {
                    return 0L;
                }
            })
                    .toArray();
        }
    }

    public static void createPairs() {
        pairs = new long[boxes.length][2][3];

        for (int i = 0; i < boxes.length; i++) {
            double minDistance = Double.MAX_VALUE;
            int nearestIndex = -1;

            for (int j = 0; j < boxes.length; j++) {
                if (i != j) {
                    double distance = findDistance(boxes[i], boxes[j]);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestIndex = j;
                    }
                }
            }

            pairs[i][0] = boxes[i];
            pairs[i][1] = boxes[nearestIndex];
        }

        Arrays.sort(pairs, (p1, p2) -> {
            double dist1 = findDistance(p1[0], p1[1]);
            double dist2 = findDistance(p2[0], p2[1]);
            return Double.compare(dist1, dist2);
        });
    }

    private static void connectBoxes(int numberOfConnection) {

        int i = 0;
        boxToRegionMapping = new HashMap<>();
        regionSize = new HashMap<>();
        while (i < numberOfConnection) {
            long[] box1 = pairs[i][0];
            long[] box2 = pairs[i][1];
            String box1Id = createId(box1);
            String box2Id = createId(box2);
            // duplicate pair
            if (boxToRegionMapping.containsKey(box1Id) && boxToRegionMapping.containsKey(box2Id)
                    && boxToRegionMapping.get(box1Id).equalsIgnoreCase(boxToRegionMapping.get(box2Id))) {
                i++;
                continue;
            }
            // one box(box1) is already part of some region
            if (boxToRegionMapping.containsKey(box1Id) && !boxToRegionMapping.containsKey(box2Id)) {
                String region = boxToRegionMapping.get(box1Id);
                boxToRegionMapping.put(box2Id, region);
                regionSize.put(region, regionSize.get(region) + 1);
                i++;
                continue;
            }
            // one box(box2) is already part of some region
            if (!boxToRegionMapping.containsKey(box1Id) && boxToRegionMapping.containsKey(box2Id)) {
                String region = boxToRegionMapping.get(box2Id);
                boxToRegionMapping.put(box1Id, region);
                regionSize.put(region, regionSize.get(region) + 1);
                i++;
                continue;
            }
            // both boxes are not part of any region, create new region
            if (!boxToRegionMapping.containsKey(box1Id) && !boxToRegionMapping.containsKey(box2Id)) {
                String newRegion = String.valueOf(totalRegionCount);
                boxToRegionMapping.put(box1Id, newRegion);
                boxToRegionMapping.put(box2Id, newRegion);
                regionSize.put(newRegion, 2);
                totalRegionCount++;
                i++;
                continue;
            }
            // both boxes are in different regions, need to merge
            if (boxToRegionMapping.containsKey(box1Id) && boxToRegionMapping.containsKey(box2Id)
                    && !boxToRegionMapping.get(box1Id).equalsIgnoreCase(boxToRegionMapping.get(box2Id))) {
                String region1 = boxToRegionMapping.get(box1Id);
                String region2 = boxToRegionMapping.get(box2Id);

                // Merge region2 into region1
                int newSize = regionSize.get(region1) + regionSize.get(region2);
                regionSize.put(region1, newSize);
                regionSize.remove(region2);

                // Update all boxes in region2 to point to region1
                for (Map.Entry<String, String> entry : boxToRegionMapping.entrySet()) {
                    if (entry.getValue().equals(region2)) {
                        entry.setValue(region1);
                    }
                }

                i++;
                continue;
            }

        }
        for (int size : regionSize.values()) {
            addRegionSizeToHeap(size);
        }

    }

    private static void addRegionSizeToHeap(int regionSize) {
        largestJunctions.add(regionSize);
        if (largestJunctions.size() > 3)
            largestJunctions.poll();
    }

    private static int productOfThreeLargestJunctions() {
        int product = 1;
        while (!largestJunctions.isEmpty())
            product *= largestJunctions.poll();
        return product;
    }

    private static String createId(long[] box) {
        return box[0] + "," + box[1] + "," + box[2];
    }

    public static double findDistance(long[] b1, long[] b2) {
        return Math.sqrt(Math.pow(b1[0] - b2[0], 2) + Math.pow(b1[1] - b2[1], 2) + Math.pow(b1[2] - b2[2], 2));
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("day8/input.txt"));
        processInput(lines);
        createPairs();
        connectBoxes(10);

        System.out.println("Largest junctions: " + largestJunctions);
        System.out.println(productOfThreeLargestJunctions());
    }

}
