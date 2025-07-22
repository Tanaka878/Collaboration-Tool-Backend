package cicosy.templete.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "tasks") // This maps the class to a MongoDB collection named "tasks"
public class Task {

    @Id
    private String id; // MongoDB IDs are typically stored as String (ObjectId)

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String status;
}
