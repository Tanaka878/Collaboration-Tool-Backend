package cicosy.templete.controller;

import cicosy.templete.domain.Project;
import cicosy.templete.dto.ProjectDTO;
import cicosy.templete.dto.ProjectsRequest;
import cicosy.templete.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin("*")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable String id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable String id, @RequestBody Project project) {
        Project updated = projectService.updateProject(id, project);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/getMyProjects")
    public ResponseEntity<List<ProjectDTO>> getMyProjects(@RequestBody ProjectsRequest  projectsRequest) {
       return projectService.getMyProjects(projectsRequest);

    }

}
