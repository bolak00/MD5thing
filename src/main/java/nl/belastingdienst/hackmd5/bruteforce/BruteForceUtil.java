package nl.belastingdienst.hackmd5.bruteforce;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nl.belastingdienst.hackmd5.md5.MD5;
import nl.belastingdienst.hackmd5.md5.MD5EncryptionStrategy;
import nl.belastingdienst.hackmd5.md5.MD5Repo;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BruteForceUtil {

    @Inject
    private CharacterGenerator characterGenerator;

    @Inject
    private MD5EncryptionStrategy md5EncryptionStrategy;

    @Inject
    private MD5Repo md5Repo;

    public Optional<String> findPhraseForHash(String hash, int maxLength) {
        Optional<MD5> existingHash = md5Repo.findHash(hash);
        if (existingHash.isPresent()) {
            return Optional.of(existingHash.get().getPhrase());
        }

        List<String> combinations = characterGenerator.generateAllUpToLength(maxLength);

        for (String phrase : combinations) {
            String generatedHash = md5EncryptionStrategy.encrypt(phrase);
            if (hash.equals(generatedHash)) {
                return Optional.of(phrase);
            }
        }

        return Optional.empty();
    }
}