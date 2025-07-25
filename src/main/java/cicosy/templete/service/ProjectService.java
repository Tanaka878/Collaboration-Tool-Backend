package cicosy.templete.service;

import cicosy.templete.domain.Project;
import cicosy.templete.domain.Task;
import cicosy.templete.domain.User;
import cicosy.templete.dto.*;
import cicosy.templete.repository.ProjectRepository;
import cicosy.templete.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {


    private final ProjectRepository projectRepository;
    private final NotificationServiceImpl notificationServiceImpl;
    private final UserRepository userRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, NotificationServiceImpl notificationServiceImpl, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.notificationServiceImpl = notificationServiceImpl;
        this.userRepository = userRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(String id) {
        return projectRepository.findById(id);
    }

    public Project createProject(Project project) {
        notificationServiceImpl.createNotification(project.getProjectName(), project.getTasks());


        return projectRepository.save(project);
    }

    public Project updateProject(String id, Project updatedProject) throws JsonProcessingException {

        System.out.println("Updated project: " + new ObjectMapper().writeValueAsString(updatedProject));

        return projectRepository.findById(id).map(project -> {
            project.setProjectName(updatedProject.getProjectName());
            project.setDescription(updatedProject.getDescription());
            project.setStartDate(updatedProject.getStartDate());
            project.setFinishDate(updatedProject.getFinishDate());
            project.setStatus(updatedProject.getStatus());
            project.setTasks(updatedProject.getTasks());
            project.setTeamMembers(updatedProject.getTeamMembers());
            return projectRepository.save(project);
        }).orElse(null);
    }

    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }

    public ResponseEntity<List<ProjectDTO>> getMyProjects(ProjectsRequest projectsRequest) {

        List<Project> byTeamMemberEmail = projectRepository.findAll();
        List<Task> taskList = new ArrayList<>();


        List<ProjectDTO> projectDTOList = new ArrayList<>();

        byTeamMemberEmail.forEach(project -> {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setDescription(project.getDescription());
            projectDTO.setName(project.getProjectName());
            projectDTO.setStartDate(project.getStartDate());
            projectDTO.setFinishDate(project.getFinishDate());
            projectDTO.setStatus(project.getStatus());
            projectDTO.setId(project.getId());
            taskList.addAll(project.getTasks());



            projectDTOList.add(projectDTO);
            project.setTasks(taskList);


        });

        System.out.println(projectDTOList);
        return ResponseEntity.ok(projectDTOList);
    }

    public Project findByProjectName(String projectName) {
      return   projectRepository.findProjectByProjectName(projectName);
    }

    public ResponseEntity<UserData> getMyData(DataRequest request) {

        Optional<User> byEmail = userRepository.findByEmail(request.getEmail());
        long members = userRepository.findAll().stream().count();

        UserData userData = new UserData();


        if (byEmail.isPresent()) {
            userData.setUsername(byEmail.get().getUsername());

        }
        userData.setUsername(request.getEmail());

        List<Project> projects = projectRepository.findProjectsByTaskTeamMemberEmail(request.getEmail());
        List<TaskDTO> matchingTasks = new ArrayList<>();

        List<ProjectDTO> recentProjects = new ArrayList<>();

        for (Project project : projects) {
            if (project.getTasks() != null) {
                for (Task task : project.getTasks()) {
                    if (task.getTeamMembers() != null &&
                            task.getTeamMembers().stream().anyMatch(member -> request.getEmail().equalsIgnoreCase(member.getEmail()))) {
                        //matchingTasks.add(task);
                        TaskDTO taskDTO = new TaskDTO();
                        taskDTO.setDescription(task.getDescription());
                        taskDTO.setName(task.getName());
                        taskDTO.setStartDate(task.getStartDate());
                        taskDTO.setEndDate(task.getFinishDate());
                        taskDTO.setStatus(task.getStatus());
                        matchingTasks.add(taskDTO);
                    }
                }
            }
        }

        projects.parallelStream().limit(20).forEach(project -> {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setDescription(project.getDescription());
            projectDTO.setName(project.getProjectName());
            projectDTO.setStartDate(project.getStartDate());
            projectDTO.setFinishDate(project.getFinishDate());
            projectDTO.setStatus(project.getStatus());
            projectDTO.setId(project.getId());
            recentProjects.add(projectDTO);

        });

        long activeProjects = projects.parallelStream().filter(project -> project.getStatus().equalsIgnoreCase("In Progress")).count();
        userData.setActiveProjects(activeProjects);
        userData.setTasks(matchingTasks);
        userData.setMembers(members);
        userData.setRecentProjects(recentProjects);





        return ResponseEntity.ok(userData);
    }
}
