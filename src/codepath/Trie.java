
package codepath;

import java.util.*;

public class Trie {
    private TrieNode root;

    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord = false;
    }

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toLowerCase().toCharArray()) {
            node = node.children.computeIfAbsent(c, k -> new TrieNode());
        }
        node.isEndOfWord = true;
    }

    public List<String> autocomplete(String prefix) {
        List<String> results = new ArrayList<>();
        TrieNode node = root;
        prefix = prefix.toLowerCase();
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) return results;
            node = node.children.get(c);
        }
        collectWords(node, prefix, results);
        return results;
    }

    private void collectWords(TrieNode node, String prefix, List<String> results) {
        if (node.isEndOfWord) results.add(prefix);
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            collectWords(entry.getValue(), prefix + entry.getKey(), results);
        }
    }
}