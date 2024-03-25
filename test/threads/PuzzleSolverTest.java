package threads;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PuzzleSolverTest {
    private PuzzleSolver solver;

    @Before
    public void setup() throws IOException {
        String[] words = Files.readAllLines(Path.of("week6/words.txt")).toArray(new String[]{});
        Trie trie = new Trie(words);
        String contents = Files.readString(Path.of("week6/boards/lock-steps.txt"));
        Puzzle puzzle = Puzzle.load(contents);
        solver = new PuzzleSolver(puzzle, trie);
    }

    @Test
    public void topLeft() {
        for (int row = 0; row < Puzzle.ROWS; row++) {
            for (int column = 0; column < Puzzle.COLUMNS; column++) {
                System.out.println(solver.pathsFrom(row, column));
                System.out.println(solver.wordsFrom(row, column));
            }
        }
        System.out.println(solver.allWords());
        System.out.println(solver.allPaths());
    }
}
