package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class BeamSplitter {
    private static char[][] grid;
    private static int rows;
    private static int cols;
    private static long[][] dp;

    private static int findNumberOfSplits() {
        int numberOfSplits = 0;
        Queue<int[]> queue = new LinkedList<>();
        // find location of S
        for (int i = 0; i < cols; i++) {
            if (grid[0][i] == 'S') {
                queue.add(new int[] { 0, i });
                break;
            }
        }
        Set<String> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            int[] beamLocation = queue.poll();
            int row = beamLocation[0];
            int col = beamLocation[1];
            // move beam downward only when there is a place to move downward
            if (row + 1 < rows) {
                // time to split
                if (grid[row + 1][col] == '^') {

                    boolean isSplitPossible = false;
                    // split on both sides; left(row, col-1) and right(row, col + 1)
                    if (col - 1 >= 0 && grid[row][col - 1] == '.') {
                        if (!visited.contains(row + "," + (col - 1))) {
                            queue.add(new int[] { row, col - 1 });
                            visited.add(row + "," + (col - 1));
                        }
                        isSplitPossible = true;
                    }
                    if (col + 1 < cols && grid[row][col + 1] == '.') {
                        if (!visited.contains(row + "," + (col + 1))) {
                            queue.add(new int[] { row, col + 1 });
                            visited.add(row + "," + (col + 1));
                        }
                        isSplitPossible = true;
                    }
                    if (isSplitPossible)
                        numberOfSplits++;
                } else {
                    if (!visited.contains((row + 1) + "," + col)) {
                        queue.add(new int[] { row + 1, col });
                        visited.add((row + 1) + "," + col);
                    }

                }
            }

        }
        return numberOfSplits;
    }

    public static long numberOfTimelines() {
        int col = -1;
        for (int i = 0; i < cols; i++) {
            if (grid[0][i] == 'S') {
                col = i;
                break;
            }
        }
        dp = new long[rows][cols];
        grid[0][col] = '.';
        return dfs(0, col);

    }

    public static long dfs(int row, int col) {
        if (col < 0 || col >= cols)
            return 0;
        if (row == rows - 1) {
            dp[row][col] = 1;
            return 1;
        }

        if (dp[row][col] != 0)
            return dp[row][col];
        long count = 0;
        if (grid[row][col] == '.')
            count += dfs(row + 1, col);

        if (grid[row][col] == '^') {
            count += dfs(row, col - 1);
            count += dfs(row, col + 1);
        }
        dp[row][col] = count;
        return dp[row][col];
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("day7/input.txt"));

        rows = lines.size();
        cols = lines.get(0).length();

        grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = lines.get(i).toCharArray();
        }

        System.out.println("number of splits happened: " + findNumberOfSplits());
        System.out.println("number of different timelines: " +
                numberOfTimelines());
    }

}
