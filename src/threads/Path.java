package threads;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Path {
    int[] route;

    public Path() {
        this(new int[]{});
    }

    public Path(int[] route) {
        this.route = route;
    }

    public boolean hasDuplicate() {
        Set<Integer> seen = new HashSet<>();
        for (int number : route) {
            if (seen.contains(number)) {
                return true;
            }
            seen.add(number);
        }
        return false;
    }

    public Path add(int number) {
        int[] newRoute = Arrays.copyOf(route, route.length + 1);
        newRoute[route.length] = number;
        return new Path(newRoute);
    }

    @Override
    public String toString() {
        return "Path{" +
                "route=" + Arrays.toString(route) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path = (Path) o;
        return Arrays.equals(route, path.route);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(route);
    }
}
