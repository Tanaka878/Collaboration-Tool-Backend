package cicosy.templete.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "teams")
public class TeamMember{
    @Id
    @GeneratedValue
    private Long id;

    private String email;
}
