package threads;

public class TrieNode {
    String value;
    TrieNode[] children = new TrieNode[26];
    boolean terminal;

    public boolean isValid(char next) {
        int index = next - 'a';
        return children[index] != null;
    }

    public TrieNode next(char nextChar) {
        int index = nextChar - 'a';
        return children[index];
    }
}
