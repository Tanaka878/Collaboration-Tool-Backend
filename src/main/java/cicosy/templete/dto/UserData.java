package cicosy.templete.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserData {
    String username;
    List<TaskDTO> tasks;
    long activeProjects;
    long members;
    List<ProjectDTO> recentProjects;

}
