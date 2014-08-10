package appnotifier.core.service.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import appnotifier.core.dao.NotificationDAO;
import appnotifier.core.entity.Application;
import appnotifier.core.entity.Notification;
import appnotifier.core.service.ApplicationService;
import appnotifier.core.service.NotificationService;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private NotificationDAO notificationDAO;

	@Autowired
	private ApplicationService applicationService;


	@Override
	public List<Notification> getCurrentNotificationsByAppUid(String appUid) {
		LOGGER.debug("getting shown notifications for appUID {}", appUid);
		return notificationDAO.findCurrentNotificationsByAppUID(new Date(), appUid);
	}


	@Override
	public List<Notification> getAllNotificationsByAppUid(String appUid) {
		LOGGER.debug("getting all notifications for appUID {}", appUid);
		return notificationDAO.findAllNotificationsByAppUID(appUid);
	}


	@Override
	public Notification toggleEnabled(long notifId) {
		LOGGER.debug("updating active state for notification {}", notifId);
		Notification notif = notificationDAO.findOne(notifId);
		notif.toggleActiveState();
		return notificationDAO.save(notif);
	}


	@Override
	public void delete(long notifId) {
		LOGGER.debug("deleting notification {}", notifId);
		notificationDAO.delete(notifId);
	}


	@Override
	public Notification saveNotifWithAppUID(Notification newNotif, String appUID) {
		LOGGER.debug("saving new notification for application {}", appUID);
		Application application = applicationService.getByAppUID(appUID);
		newNotif.setApplication(application);
		return notificationDAO.save(newNotif);
	}


	@Override
	public Notification updateNotif(Notification updatedNotif, long notifId) {
		LOGGER.debug("updating notification {}", notifId);
		Notification storedNotification = notificationDAO.findOne(notifId);
		storedNotification.setEnabled(updatedNotif.isEnabled());
		storedNotification.setMessage(updatedNotif.getMessage());
		storedNotification.setType(updatedNotif.getType());
		storedNotification.setStartDate(updatedNotif.getStartDate());
		storedNotification.setEndDate(updatedNotif.getEndDate());
		return notificationDAO.save(storedNotification);
	}
}
