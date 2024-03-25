package threads;

public class Trie {
    private TrieNode root;

    public Trie(String[] words) {
        root = new TrieNode();
        for (String word : words) {
            if (!validWord(word)) {
                continue;
            }
            insert(word.toLowerCase(), word.toLowerCase());
        }
    }

    public boolean validWord(String word) {
        if (word.length() <= 3) {
            return false;
        }
        for (char letter : word.toCharArray()) {
            if (!Character.isAlphabetic(letter)) {
                return false;
            }
        }
        return true;
    }

    public TrieNode find(String key) {
        TrieNode node = root;
        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 'a';
            if (node.children[index] == null) {
                return null;
            }
            node = node.children[index];
        }
        return node;
    }

    public void insert(String key, String value) {
        TrieNode node = root;
        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.value = value;
        node.terminal = true;
    }
}
