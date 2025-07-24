package cicosy.templete.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDTO {

    private String id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String status;

}
