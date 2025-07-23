package cicosy.templete.repository;

import cicosy.templete.domain.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, String> {

    @Query("{ 'teamMembers.email': ?0 }")
    List<Project> findByTeamMemberEmail(String email);

    @Override
    List<Project> findAll();
}
