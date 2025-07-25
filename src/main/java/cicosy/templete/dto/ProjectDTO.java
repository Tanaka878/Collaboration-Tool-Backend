package cicosy.templete.dto;

import cicosy.templete.domain.Task;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectDTO {

    private String id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String status;
    List<Task> tasks;

}
