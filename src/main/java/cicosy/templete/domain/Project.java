package cicosy.templete.domain;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "projects")
public class Project {

    @Id
    private String id;  // MongoDB document ID

    private String projectName;
    private String description;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String status;

    private List<Task> tasks;
    private List<TeamMember> teamMembers;
}
