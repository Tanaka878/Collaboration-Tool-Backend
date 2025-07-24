package cicosy.templete.repository;

import cicosy.templete.domain.Notification;
import cicosy.templete.domain.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification,String> {

    List<Notification> findByEmailAndStatus(String email, Status status);



}
