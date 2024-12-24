package nl.belastingdienst.hackmd5;

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
}