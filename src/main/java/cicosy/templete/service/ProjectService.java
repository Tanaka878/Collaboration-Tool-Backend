package cicosy.templete.service;

import cicosy.templete.domain.Project;
import cicosy.templete.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
