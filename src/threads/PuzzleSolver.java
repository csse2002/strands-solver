package threads;

import java.util.*;

public class PuzzleSolver {
    private final Puzzle puzzle;
    private final Trie trie;

    public PuzzleSolver(Puzzle puzzle, Trie trie) {
        this.puzzle = puzzle;
        this.trie = trie;
    }

    public List<String> wordsFrom(int row, int column) {
        List<String> words = new ArrayList<>();
        List<Path> paths = new ArrayList<>();
        char start = puzzle.at(row, column);
        TrieNode node = trie.find(("" + start).toLowerCase());
        wordsFrom(node, words, paths, new Path(), row, column);
        return words;
    }

    public List<Path> pathsFrom(int row, int column) {
        List<String> words = new ArrayList<>();
        List<Path> paths = new ArrayList<>();
        char start = puzzle.at(row, column);
        TrieNode node = trie.find(("" + start).toLowerCase());
        wordsFrom(node, words, paths, new Path(), row, column);
        return paths;
    }

    public void wordsFrom(TrieNode start, List<String> words, List<Path> paths, Path path, int row, int column) {
        path = path.add(row * Puzzle.COLUMNS + column);
        if (start.terminal) {
            if (!path.hasDuplicate()) {
                words.add(start.value);
                paths.add(path);
            }
        }
        for (int rowDelta = -1; rowDelta <= 1; rowDelta++) {
            for (int columnDelta = -1; columnDelta <= 1; columnDelta++) {
                if ((rowDelta == 0 && columnDelta == 0)) {
                    continue;
                }
                int newRow = row + rowDelta;
                int newColumn = column + columnDelta;
                if (!puzzle.inRange(newRow, newColumn)) {
                    continue;
                }

                char next = puzzle.at(newRow, newColumn);
                if (start.isValid(next)) {
                    wordsFrom(start.next(next), words, paths, path, newRow, newColumn);
                }
            }
        }
    }

    public List<Path> allPaths() {
        List<Path> paths = new ArrayList<>();
        for (int row = 0; row < Puzzle.ROWS; row++) {
            for (int column = 0; column < Puzzle.COLUMNS; column++) {
                paths.addAll(pathsFrom(row, column));
            }
        }
        return paths;
    }

    public List<String> allWords() {
        List<String> paths = new ArrayList<>();
        for (int row = 0; row < Puzzle.ROWS; row++) {
            for (int column = 0; column < Puzzle.COLUMNS; column++) {
                paths.addAll(wordsFrom(row, column));
            }
        }
        return paths;
    }

    public PuzzleSolution solve() {
        PuzzleSolution solution;
        Set<PuzzleSolution> seen = new HashSet<>();
        Stack<PuzzleSolution> toTry = new Stack<>();
        toTry.push(new PuzzleSolution());

        List<Path> paths = allPaths();

        while (!toTry.empty()) {
//            if (seen.size() > 10) {
//                break;
//            }
            solution = toTry.pop();
            if (seen.contains(solution)) {
                continue;
            }
            if (solution.isCovered()) {
                return solution;
            }

            seen.add(solution);
//            System.out.println(solution.paths);
//            System.out.println(solution);
//            System.out.println("----");

            for (Path path : paths) {
                PuzzleSolution may = solution.addPath(path);
                if (may == null) {
                    continue;
                }
                toTry.push(may);
            }
        }
        return null;
    }

}
