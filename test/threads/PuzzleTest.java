package threads;

import org.junit.Test;
import static org.junit.Assert.*;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PuzzleTest {
    @Test
    public void loadTest() throws IOException {
        String contents = Files.readString(Path.of("week6/boards/romeo.txt"));

        Puzzle puzzle = Puzzle.load(contents);
        System.out.println(puzzle);
        assertEquals('e', puzzle.at(0, 0));
        assertEquals('o', puzzle.at(0, 5));
        assertEquals('t', puzzle.at(1, 0));
        assertEquals('v', puzzle.at(2, 3));
        assertEquals('o', puzzle.at(7, 5));
    }
}
