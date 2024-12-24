package nl.belastingdienst.hackmd5;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class MD5Repo {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public MD5 save(MD5 md5) {
        if (findHash(md5.getHash()).isPresent()) {
            return md5;
        }
        entityManager.persist(md5);
        return md5;
    }

    public Optional<MD5> findHash(String hash) {
        return Optional.ofNullable(entityManager.find(MD5.class, hash));
    }
}