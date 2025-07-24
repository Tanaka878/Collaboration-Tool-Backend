package cicosy.templete.domain;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "teams")
public class TeamMember{
    @Id
    private String id;
    private String email;
}
