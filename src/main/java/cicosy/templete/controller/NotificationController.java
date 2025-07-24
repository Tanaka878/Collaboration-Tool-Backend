package cicosy.templete.controller;

import cicosy.templete.domain.Notification;
import cicosy.templete.domain.Status;
import cicosy.templete.service.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationServiceImpl notificationService;

    @Autowired
    public NotificationController(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications(@RequestParam String email) {
        return notificationService.getUnreadNotifications(email);
    }

    @PutMapping("/{id}")
    public Notification updateNotificationStatus(
            @PathVariable String id,
            @RequestParam Status status) {
        return notificationService.updateNotificationStatus(id, status);
    }
}
