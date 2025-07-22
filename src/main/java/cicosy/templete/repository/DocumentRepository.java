package cicosy.templete.repository;

import cicosy.templete.domain.DocumentData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends MongoRepository<DocumentData, String> {
}
