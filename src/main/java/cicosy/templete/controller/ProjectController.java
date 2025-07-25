package cicosy.templete.controller;

import cicosy.templete.domain.Project;
import cicosy.templete.dto.DataRequest;
import cicosy.templete.dto.ProjectDTO;
import cicosy.templete.dto.ProjectsRequest;
import cicosy.templete.dto.UserData;
import cicosy.templete.service.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProject(@PathVariable String id, @RequestBody Project project) throws JsonProcessingException {
        Project updated = projectService.updateProject(id, project);
        System.out.println(ResponseEntity.ok("Serialized data : "+updated));
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/getMyProjects")
    public ResponseEntity<List<ProjectDTO>> getMyProjects(@RequestBody ProjectsRequest projectsRequest) {
        return projectService.getMyProjects(projectsRequest);
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<Project> getProjectByName(@PathVariable String id) {
        Optional<Project> project = projectService.getProjectById(id);
        if (project.isPresent()) {
            return ResponseEntity.ok(project.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*an enpoint that fetches the user stats
    * Task to be completed
    * Tasks completed this week
    * Username of the logged in user
    * */

    @PostMapping("/myData")
    public ResponseEntity<UserData> getMyData(@RequestBody DataRequest request){
        return projectService.getMyData(request);

    }






}
