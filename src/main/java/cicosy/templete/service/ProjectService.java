package cicosy.templete.service;

import cicosy.templete.domain.Project;
import cicosy.templete.dto.ProjectDTO;
import cicosy.templete.dto.ProjectsRequest;
import cicosy.templete.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {


    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(String id) {
        return projectRepository.findById(id);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(String id, Project updatedProject) {
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

        List<ProjectDTO> projectDTOList = new ArrayList<>();

        byTeamMemberEmail.forEach(project -> {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setDescription(project.getDescription());
            projectDTO.setName(project.getProjectName());
            projectDTOList.add(projectDTO);


        });

        System.out.println(projectDTOList);
        return ResponseEntity.ok(projectDTOList);
    }

    public Project findByProjectName(String projectName) {
      return   projectRepository.findProjectByProjectName(projectName);
    }
}
