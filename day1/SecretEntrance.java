package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SecretEntrance {

    private static List<String[]> moves;

    private static void processInput(List<String> lines) {
        moves = new ArrayList<>();
        for (String line : lines) {
            String direction = line.substring(0, 1);
            String numberOfTimes = line.substring(1, line.length());
            moves.add(new String[] { direction, numberOfTimes });
        }
    }

    private static int findPassword() {
        int password = 0;
        long current = 50;
        for (String[] move : moves) {
            long numberOfTimes = Long.parseLong(move[1]);
            numberOfTimes %= 100;
            String direction = move[0];
            if (direction.equalsIgnoreCase("L")) {
                current -= numberOfTimes;
                if (current < 0)
                    current = 100 + current;
            } else {
                current += numberOfTimes;
                current %= 100;
            }
            if (current == 0)
                password++;
        }
        return password;
    }

    public static void main(String[] args) throws IOException {
        // read input
        List<String> lines = Files.readAllLines(Paths.get("day1/input.txt"));
        processInput(lines);
        System.out.println(findPassword());

    }
}
