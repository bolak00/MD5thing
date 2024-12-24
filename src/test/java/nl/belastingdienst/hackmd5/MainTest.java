package nl.belastingdienst.hackmd5;

import nl.belastingdienst.hackmd5.character.CharacterGenerator;
import nl.belastingdienst.hackmd5.md5.MD5;
import nl.belastingdienst.hackmd5.md5.MD5EncryptionStrategy;
import nl.belastingdienst.hackmd5.md5.MD5Repo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.persistence.EntityManager;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MainTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private MD5Repo md5Repo;

    @InjectMocks
    private MD5EncryptionStrategy encryptionStrategy;

    private CharacterGenerator characterGenerator;

    @BeforeEach
    void setUp() {
        characterGenerator = new CharacterGenerator();
    }

    @Test
    void testMD5EncryptionPerformance() {
        String testPhrase = "test123";

        Instant start = Instant.now();
        String hash = encryptionStrategy.encrypt(testPhrase);
        Instant end = Instant.now();

        long timeElapsed = Duration.between(start, end).toMillis();
        System.out.println("MD5 Encryption took: " + timeElapsed + " milliseconds");

        assertNotNull(hash);
        assertTrue(timeElapsed >= 0);
    }

    @Test
    void testMD5RepoBatchPerformance() {
        int batchSize = 1000;
        Instant start = Instant.now();

        for (int i = 0; i < batchSize; i++) {
            MD5 md5 = MD5.builder()
                    .hash("hash" + i)
                    .phrase("phrase" + i)
                    .build();
            md5Repo.save(md5);
        }

        Instant end = Instant.now();
        long timeElapsed = Duration.between(start, end).toMillis();
        System.out.println("Batch processing of " + batchSize + " records took: " + timeElapsed + " milliseconds");
        System.out.println("Average time per record: " + (double)timeElapsed/batchSize + " milliseconds");
    }

    @Test
    void testCharacterGeneratorPerformance() {
        int[] testLengths = {1, 2, 3, 4};

        for (int length : testLengths) {
            Instant start = Instant.now();
            List<String> result = characterGenerator.generate(length);
            Instant end = Instant.now();

            long timeElapsed = Duration.between(start, end).toMillis();
            System.out.println("Character generation for length " + length + " took: " + timeElapsed + " milliseconds");
        }
    }

    @Test
    void testEndToEndPerformance() {
        String testPhrase = "test123";

        Instant start = Instant.now();

        // Encryption
        String hash = encryptionStrategy.encrypt(testPhrase);

        // Repository lookup
        when(md5Repo.findHash(hash)).thenReturn(Optional.of(MD5.builder()
                .hash(hash)
                .phrase(testPhrase)
                .build()));
        Optional<MD5> result = md5Repo.findHash(hash);

        Instant end = Instant.now();

        long timeElapsed = Duration.between(start, end).toMillis();
        System.out.println("End-to-end operation took: " + timeElapsed + " milliseconds");

        assertTrue(result.isPresent());
        assertEquals(testPhrase, result.get().getPhrase());
    }

    @Test
    void testPerformanceUnderLoad() {
        int iterations = 1000;
        long totalTime = 0;
        long minTime = Long.MAX_VALUE;
        long maxTime = 0;

        for (int i = 0; i < iterations; i++) {
            String testPhrase = "test" + i;

            Instant start = Instant.now();
            encryptionStrategy.encrypt(testPhrase);
            Instant end = Instant.now();

            long timeElapsed = Duration.between(start, end).toMillis();
            totalTime += timeElapsed;
            minTime = Math.min(minTime, timeElapsed);
            maxTime = Math.max(maxTime, timeElapsed);
        }

        double avgTime = (double) totalTime / iterations;
        System.out.println("Performance under load (" + iterations + " iterations):");
        System.out.println("Average time: " + avgTime + " milliseconds");
        System.out.println("Min time: " + minTime + " milliseconds");
        System.out.println("Max time: " + maxTime + " milliseconds");
    }
}