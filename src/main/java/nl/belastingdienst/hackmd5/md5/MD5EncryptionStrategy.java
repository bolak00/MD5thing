package nl.belastingdienst.hackmd5.md5;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@ApplicationScoped
public class MD5EncryptionStrategy {

    @Inject
    MD5Repo md5Repo;

    private static final String ALGORITHM = "MD5";

    public String encrypt(String phrase) {
        try {
            MessageDigest m = MessageDigest.getInstance(ALGORITHM);
            m.update(phrase.getBytes(), 0, phrase.length());
            String md5UserPass = new BigInteger(1, m.digest()).toString(16);
            md5Repo.save(MD5.builder().hash(md5UserPass).phrase(phrase).build());
            return md5UserPass;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
