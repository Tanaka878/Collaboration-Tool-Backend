package cicosy.templete.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "projects")
public class Project {

    private String projectName;
    private String description;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String status;

    private List<Task> tasks;             // Embedded or referenced documents
    private List<TeamMember> teamMembers; // Same as above
}
