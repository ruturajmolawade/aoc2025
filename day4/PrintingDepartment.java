package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PrintingDepartment {

    static char[][] grid;

    // Eight directions: up, down, left, right, and four diagonals
    static int[][] directions = {
            { -1, 0 }, // up
            { 1, 0 }, // down
            { 0, -1 }, // left
            { 0, 1 }, // right
            { -1, -1 }, // up-left
            { -1, 1 }, // up-right
            { 1, -1 }, // down-left
            { 1, 1 } // down-right
    };

    public static int findTotalNumberOfRolls() {
        int totalNumberOfRollsAccessible = 0;
        int totalRows = grid.length;
        int totalColumns = grid[0].length;
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                if (grid[i][j] == '@' && totalNumberOfRollsAround(i, j) < 4) {
                    totalNumberOfRollsAccessible++;
                }
            }
        }
        return totalNumberOfRollsAccessible;
    }

    public static int totalNumberOfRollsAround(int row, int column) {
        int rollsAround = 0;
        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newColumn = column + direction[1];
            if (newRow >= 0 && newRow < grid.length && newColumn >= 0 && newColumn < grid[0].length
                    && grid[newRow][newColumn] == '@')
                rollsAround++;
        }
        return rollsAround;
    }

    public static void main(String[] args) throws IOException {
        // Read input from file
        List<String> lines = Files.readAllLines(Paths.get("day4/input.txt"));

        // Create grid from input
        int rows = lines.size();
        int cols = lines.get(0).length();
        grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = lines.get(i).toCharArray();
        }

        System.out.println(findTotalNumberOfRolls());
    }
}
