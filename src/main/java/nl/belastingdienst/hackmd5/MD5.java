package nl.belastingdienst.hackmd5;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MD5 {
    @Id
    private String hash;
    private String phrase;
}