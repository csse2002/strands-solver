package threads;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class AlgorithmXTest {
    private PuzzleSolver solver;

    @Before
    public void setup() throws IOException {
        String[] words = Files.readAllLines(java.nio.file.Path.of("words.txt")).toArray(new String[]{});
        Trie trie = new Trie(words);
        String contents = Files.readString(Path.of("boards/lock-step.txt"));
        Puzzle puzzle = Puzzle.load(contents);
        solver = new PuzzleSolver(puzzle, trie);
    }

    @Test
    public void testAlgoX() {
//        AlgorithmX algorithmX = new AlgorithmX(solver.allPaths());
//        System.out.println(algorithmX);
//        System.out.println(algorithmX.solve());
    }

    @Test
    public void testDancing() throws IOException {
//        for (int row = 0; row < Puzzle.ROWS; row++) {
//            for (int column = 0; column < Puzzle.COLUMNS; column++) {
//                System.out.println(solver.pathsFrom(row, column));
//                System.out.println(solver.wordsFrom(row, column));
//            }
//        }
        List<threads.Path> allPaths = solver.allPaths();
        List<String> allWords = solver.allWords();
        Map<threads.Path, String> map = new HashMap<>();
        for (int i = 0; i < allWords.size(); i++) {
            map.put(allPaths.get(i), allWords.get(i));
        }
        System.out.println(allWords);
//        Files.writeString(Path.of("week6/out.txt"), allWords.toString());
//        for (String word : allWords) {
//            System.out.println(word);
//        }
        DancingLinks links = new DancingLinks(solver.allPaths());
//        System.out.println(links);
        links.search(0);

        Set<Set<String>> solutions = new HashSet<>();
        for (List<DancingLinks.DataObject> objects : links.finalSolutions) {
            Set<String> solution = new HashSet<>();
            for (DancingLinks.DataObject object : objects) {
                String word = map.get(object.rowId);
                solution.add(word);
//                solutions.append(word + "\n");
//                System.out.println(word);
            }
            solutions.add(solution);
//            solutions.append("\n");
//            System.out.println();
        }

        Set<String> mustContain = new HashSet<>();
        mustContain.add("virgin");
        mustContain.add("astrology");
        mustContain.add("bull");
        mustContain.add("archer");
        mustContain.add("twins");
        mustContain.add("goat");
//        mustContain.add("lather");

        StringBuilder solutionOut = new StringBuilder();
        for (Set<String> sol : solutions) {
            boolean possible = true;
            for (String must : mustContain) {
                if (!sol.contains(must)) {
                    possible = false;
                    break;
                }
            }
            if (!possible) {
                continue;
            }
            for (String word : sol) {
                solutionOut.append(word + "\n");
            }
            solutionOut.append("\n");
        }
        Files.writeString(Path.of("solutions.txt"), solutionOut.toString());
//        System.out.println(links);
    }

    public PuzzleSolver load(String source) throws IOException {
        String[] words = Files.readAllLines(java.nio.file.Path.of("words.txt")).toArray(new String[]{});
        Trie trie = new Trie(words);
        String contents = Files.readString(Path.of(source));
        Puzzle puzzle = Puzzle.load(contents);
        return new PuzzleSolver(puzzle, trie);
    }

    @Test
    public void testLockSteps() throws IOException {
        solver = load("boards/lock-steps.txt");

        List<threads.Path> allPaths = solver.allPaths();
        List<String> allWords = solver.allWords();
        Map<threads.Path, String> map = new HashMap<>();
        for (int i = 0; i < allWords.size(); i++) {
            map.put(allPaths.get(i), allWords.get(i));
        }

        DancingLinks links = new DancingLinks(solver.allPaths());
        links.search(0);

        Set<Set<String>> solutions = new HashSet<>();
        for (List<DancingLinks.DataObject> objects : links.finalSolutions) {
            Set<String> solution = new HashSet<>();
            for (DancingLinks.DataObject object : objects) {
                String word = map.get(object.rowId);
                solution.add(word);
            }
            solutions.add(solution);
        }

        assertTrue(solutions.contains(Set.of(
                "condition",
                "hair",
                "towel",
                "apply",
                "rinse",
                "moisturize",
                "lather",
                "care"
        )));
    }

    @Test
    public void testRomeoAndJuliet() throws IOException {
        solver = load("boards/romeo.txt");

        List<threads.Path> allPaths = solver.allPaths();
        List<String> allWords = solver.allWords();
        Map<threads.Path, String> map = new HashMap<>();
        for (int i = 0; i < allWords.size(); i++) {
            map.put(allPaths.get(i), allWords.get(i));
        }

        DancingLinks links = new DancingLinks(solver.allPaths());
        links.search(0);

        Set<Set<String>> solutions = new HashSet<>();
        for (List<DancingLinks.DataObject> objects : links.finalSolutions) {
            Set<String> solution = new HashSet<>();
            for (DancingLinks.DataObject object : objects) {
                String word = map.get(object.rowId);
                solution.add(word);
            }
            solutions.add(solution);
        }

        assertTrue(solutions.contains(Set.of(
                "feud",
                "tragedy",
                "tryst",
                "love",
                "poison",
                "kiss",
                "balcony",
                "star",
                "crossed"
        )));
    }

    @Test
    public void testFruits() throws IOException {
        Puzzle.ROWS = 5;
        Puzzle.COLUMNS = 4;
        solver = load("boards/fruits.txt");


        List<threads.Path> allPaths = solver.allPaths();
        List<String> allWords = solver.allWords();
        Map<threads.Path, String> map = new HashMap<>();
        for (int i = 0; i < allWords.size(); i++) {
            map.put(allPaths.get(i), allWords.get(i));
        }

        DancingLinks links = new DancingLinks(solver.allPaths());
        links.search(0);

        Set<Set<String>> solutions = new HashSet<>();
        for (List<DancingLinks.DataObject> objects : links.finalSolutions) {
            Set<String> solution = new HashSet<>();
            for (DancingLinks.DataObject object : objects) {
                String word = map.get(object.rowId);
                solution.add(word);
            }
            solutions.add(solution);
        }

        assertTrue(solutions.contains(Set.of(
                "banana",
                "apple",
                "lime",
                "fruit"
        )));
    }

    @Test
    public void testSmall() throws IOException {
        if (true) {
            return;
        }
        solver = load("board/small.txt");

        List<threads.Path> allPaths = solver.allPaths();
        List<String> allWords = solver.allWords();
        Map<threads.Path, String> map = new HashMap<>();
        for (int i = 0; i < allWords.size(); i++) {
            map.put(allPaths.get(i), allWords.get(i));
        }

        DancingLinks links = new DancingLinks(solver.allPaths());
        links.search(0);

        Set<Set<String>> solutions = new HashSet<>();
        for (List<DancingLinks.DataObject> objects : links.finalSolutions) {
            Set<String> solution = new HashSet<>();
            for (DancingLinks.DataObject object : objects) {
                String word = map.get(object.rowId);
                solution.add(word);
            }
            solutions.add(solution);
        }

        assertTrue(solutions.contains(Set.of(
                "lick",
                "sips",
                "suck"
        )));
    }
}
