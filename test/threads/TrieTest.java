package threads;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TrieTest {
    private Trie trie;

    @Before
    public void setup() throws IOException {
        String[] words = Files.readAllLines(Path.of("week6/words.txt")).toArray(new String[]{});
        trie = new Trie(words);
    }

    @Test
    public void asdf() {
        assertNull(trie.find("asdf"));
    }

    @Test
    public void cat() {
        assertNotNull(trie.find("cat"));
    }
}
