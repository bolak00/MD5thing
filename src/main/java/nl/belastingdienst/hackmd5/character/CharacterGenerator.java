package nl.belastingdienst.hackmd5.character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CharacterGenerator {

    private List<String> alphabet;

    public CharacterGenerator() {
        List<String> chars = new ArrayList<>();

        for (char c = 'a'; c <= 'z'; c++) {
            chars.add(String.valueOf(c));
        }

        for (char c = '0'; c <= '9'; c++) {
            chars.add(String.valueOf(c));
        }

        this.alphabet = Collections.unmodifiableList(chars);
    }

    public List<String> generate(Integer length) {
        if (length <= 0) {
            return Collections.emptyList();
        }

        List<String> combinations = new ArrayList<>();
        generateCombinations(new StringBuilder(), length, combinations);
        return combinations;
    }

    private void generateCombinations(StringBuilder current, int length, List<String> result) {
        if (current.length() == length) {
            result.add(current.toString());
            return;
        }

        for (String character : alphabet) {
            current.append(character);
            generateCombinations(current, length, result);
            current.setLength(current.length() - 1);
        }
    }

    public List<String> generateAllUpToLength(int maxLength) {
        List<String> allCombinations = new ArrayList<>();
        for (int i = 1; i <= maxLength; i++) {
            allCombinations.addAll(generate(i));
        }
        return allCombinations;
    }
}