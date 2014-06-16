package appnotifier.core.service;

import java.util.List;
import appnotifier.core.entity.Notification;

public interface NotificationService {

    List<Notification> getCurrentNotificationsByAppUid(String appUid);

    List<Notification> getAllNotificationsByAppUid(String appUid);

    Notification toggleActif(long notifId);

    void delete(long notifId);

    Notification saveNotifWithAppUID(Notification newNotif, String appUid);

    Notification updateNotif(Notification updatedNotif, long notifId);
}
