package threads;

// Implementation of dancing links algorithm derived from
// Warren Partridge: https://github.com/warren/algorithm-x

import java.util.*;
import java.util.function.Function;

public class DancingLinks {
    class DataObject {
        DataObject left, right, up, down;
        ColumnObject C;
        Object rowId;

        DataObject() {
            left = right = up = down = this;
        }

        DataObject(ColumnObject initC, Object rowId) {
            this();
            C = initC;
            this.rowId = rowId;
        }

        DataObject appendToRow(DataObject newDO) {
            this.right.left = newDO;
            newDO.left = this;
            newDO.right = this.right;
            this.right = newDO;

            return newDO;
        }

        DataObject appendToColumn(DataObject newDO) {
            this.down.up = newDO;
            newDO.up = this;
            newDO.down = this.down;
            this.down = newDO;

            return newDO;
        }

        void unlinkFromRow() {
            this.left.right = this.right;
            this.right.left = this.left;
        }

        void relinkToRow() {
            this.left.right = this.right.left = this;
        }

        void unlinkFromColumn() {
            this.up.down = this.down;
            this.down.up = this.up;
            this.C.size--;
        }

        void relinkToColumn() {
            this.up.down = this.down.up = this;
            this.C.size++;
        }

    }

    class ColumnObject extends DataObject {
        String name;
        int size; // Number of 1s in the column

        ColumnObject(String initName) {
            super();
            C = this;
            name = initName;
            size = 0;
        }

        void cover() {
            this.unlinkFromRow();

            for (DataObject i = this.down; i != this.C; i = i.down) {
                for (DataObject j = i.right; j != i; j = j.right) {
                    j.unlinkFromColumn();
                }
            }
        }

        void uncover() {
            for (DataObject i = this.up; i != this.C; i = i.up) {
                for (DataObject j = i.left; j != i; j = j.left) {
                    j.relinkToColumn();
                }
            }

            this.relinkToRow();
        }

    }


    private final ColumnObject root;
    private final List<DataObject> solutions = new ArrayList<>();

    public List<List<DataObject>> finalSolutions = new ArrayList<>();
    private int numSolutionsFound = 0;

    public void search(int K) {
        if (root.right == root) {
            numSolutionsFound++;

            finalSolutions.add(new ArrayList<>(solutions));
//            for (DataObject r : solutions) {
////                System.out.println(r.C.name);
//                System.out.println(r.rowId);
//            }
//            System.out.println();
//            System.out.println(solutions.subList(0, K));

            return;
        }

        ColumnObject c = getSmallestColumnObject();
        c.cover();

//        System.out.println(this);
//        System.out.println();
//        System.out.println("-----------------------------------------------");
//        System.out.println();

        for (DataObject r = c.down; r != c; r = r.down) {
            solutions.add(K, r);

            for (DataObject j = r.right; j != r; j = j.right) {
                j.C.cover();
            }

            search(K + 1);
            r = solutions.remove(K);
            c = r.C;

            for (DataObject j = r.left; j != r; j = j.left) {
                j.C.uncover();
            }
        }

        c.uncover();

        return;

        // Pseudocode from the paper:

//        If R[h] = h, print the current solution (see below) and return.
//        Otherwise choose a column object c (see below).
//        Cover column c (see below).
//        For each r ← D[c], D[D[c]], ... , while r != c,
//          set Ok ← r;
//          for each j ← R[r], R[R[r]], ... , while j != r,
//            cover column j (see below);
//          search(k + 1);
//          set r ← Ok and c ← C[r];
//          for each j ← L[r], L[L[r]], ... , while j != r,
//            uncover column j (see below).
//        Uncover column c (see below) and return.
    }






    private ColumnObject getSmallestColumnObject() {
        return (ColumnObject) root.right;
//        int min = Integer.MAX_VALUE;
//        ColumnObject smallestCO = null;
//
//        // Search for the min size CO by iterating through all COs by moving right until we end up back at the header
//        for (ColumnObject col = (ColumnObject) root.right; col != root; col = (ColumnObject) col.right) {
//            if (col.size < min) {
//                min = col.size;
//                smallestCO = col;
//            }
//        }
//
//        return smallestCO;
    }

    public DancingLinks(List<Path> paths) {
        root = new ColumnObject("ROOT");
        ColumnObject lastCol = root;
        DataObject[] columns = new DataObject[Puzzle.ROWS * Puzzle.COLUMNS];
        DataObject[] rows = new DataObject[paths.size()];

        for (int index = 0; index < Puzzle.ROWS * Puzzle.COLUMNS; index++) {

            ColumnObject column = new ColumnObject("" + index);

            lastCol.appendToRow(column);
            lastCol = column;
            columns[index] = column;

            int col = 0;
            for (Path path : paths) {
                if (contains(path.route, index)) {
                    DataObject object = new DataObject(column, path);
                    columns[index].appendToColumn(object);
                    if (rows[col] != null) {
                        rows[col].appendToRow(object);
                    }
                    rows[col] = object;
                }
                col++;
            }

//            col++;
        }
    }

//    public DancingLinks(List<Path> paths) {
//        root = new ColumnObject("ROOT");
//        ColumnObject lastCol = root;
//        DataObject[] columns = new DataObject[paths.size()];
//        DataObject[] rows = new DataObject[Puzzle.ROWS * Puzzle.COLUMNS];
//        int col = 0;
//        for (Path path : paths) {
//            ColumnObject column = new ColumnObject(path.toString());
//
//            lastCol.appendToRow(column);
//            lastCol = column;
//            columns[col] = column;
//
//            for (int index = 0; index < Puzzle.ROWS * Puzzle.COLUMNS; index++) {
//                if (contains(path.route, index)) {
//                    DataObject object = new DataObject(column, "" + index);
//                    columns[col].appendToColumn(object);
//                    if (rows[index] != null) {
//                        rows[index].appendToRow(object);
//                    }
//                    rows[index] = object;
//                }
//            }
//
//            col++;
//        }
//    }

    private boolean contains(int[] arr, int elem) {
        for (int e : arr) {
            if (e == elem) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (ColumnObject col = (ColumnObject) root.right; col != root; col = (ColumnObject) col.right) {
            builder.append(col.name);
            for (DataObject rowElem = col.down; rowElem != col; rowElem = rowElem.down) {
                builder.append(rowElem.rowId + " ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}