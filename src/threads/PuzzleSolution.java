package threads;

import java.util.*;

public class PuzzleSolution {
    public List<Path> paths = new ArrayList<>();
    private Set<Integer> covered = new HashSet<>();

    public boolean isCovered() {
        return covered.size() == Puzzle.ROWS * Puzzle.COLUMNS;
    }

    public int next() {
        Random random = new Random();
        int next;
        do {
            next = random.nextInt(Puzzle.ROWS * Puzzle.COLUMNS);
        } while (covered.contains(next));
        return next;
//        for (int i = 0; i < Puzzle.ROWS * Puzzle.COLUMNS; i++) {
//            if (!covered.contains(i)) {
//                return i;
//            }
//        }
//        throw new IndexOutOfBoundsException("Puzzle covered");
    }

    public PuzzleSolution addPath(Path path) {
        PuzzleSolution newSolution = new PuzzleSolution();
        newSolution.paths = new ArrayList<>(paths);
        newSolution.covered = new HashSet<>(covered);
        for (int index : path.route) {
            if (newSolution.covered.contains(index)) {
                return null;
            }
            newSolution.covered.add(index);
        }
        newSolution.paths.add(path);
        return newSolution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuzzleSolution solution = (PuzzleSolution) o;
        return Objects.equals(paths, solution.paths);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paths);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int[] indexes = new int[Puzzle.ROWS * Puzzle.COLUMNS];
        for (int i = 0; i < Puzzle.ROWS * Puzzle.COLUMNS; i++) {
            indexes[i] = -1;
        }
        for (Path path : paths) {
            for (int i = 0; i < path.route.length; i++) {
                indexes[path.route[i]] = i;
            }
        }
        builder.append(covered + "\n");
        builder.append(paths + "\n");
        for (int row = 0; row < Puzzle.ROWS; row++) {
            builder.append("|");
            for (int column = 0; column < Puzzle.COLUMNS; column++) {
                if (covered.contains(row * Puzzle.COLUMNS + column)) {
                    builder.append("*");
                } else {
                    builder.append(" ");
                }
            }
            builder.append("|");
            for (int column = 0; column < Puzzle.COLUMNS; column++) {
                if (indexes[row * Puzzle.COLUMNS + column] == -1) {
                    builder.append(" ");
                } else {
                    builder.append(indexes[row * Puzzle.COLUMNS + column]);
                }
            }
            builder.append("|\n");
        }
        return builder.toString();
    }
}
