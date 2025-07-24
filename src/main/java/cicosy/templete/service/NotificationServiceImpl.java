package cicosy.templete.service;

import cicosy.templete.domain.Notification;
import cicosy.templete.domain.Status;
import cicosy.templete.domain.Task;
import cicosy.templete.repository.NotificationRepository;
import cicosy.templete.util.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl {

    private final NotificationRepository notificationRepository;
    private final MailSenderService mailSenderService;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, MailSenderService mailSenderService) {
        this.notificationRepository = notificationRepository;
        this.mailSenderService = mailSenderService;
    }

    public void createNotification(String projectName, List<Task> tasks) {



        tasks.forEach(task -> {
            Notification notification = new Notification();
            notification.setProjectName(projectName);


            task.getTeamMembers().forEach(teamMember -> {
                notification.setEmail(teamMember.getEmail());
                notification.setTask(task.getName());
                notification.setStatus(Status.UNREAD);

                mailSenderService.sendSimpleMail(teamMember.getEmail(),task.getName(),projectName );

            });

            notificationRepository.save(notification);

        });


    }

    public List<Notification> getUnreadNotifications(String email) {
        return notificationRepository.findByEmailAndStatus(email, Status.UNREAD);
    }

    public Notification updateNotificationStatus(String id, Status status) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));

        notification.setStatus(status);
        return notificationRepository.save(notification);
    }

}
