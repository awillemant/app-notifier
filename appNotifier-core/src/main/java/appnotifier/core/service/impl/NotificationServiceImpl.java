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

	private static Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private NotificationDAO notificationDAO;

	@Autowired
	private ApplicationService applicationService;


	@Override
	public List<Notification> getCurrentNotificationsByAppUid(String appUid) {
		logger.debug("Récupération des notifications affichées pour l'appUID {}", appUid);
		return notificationDAO.findCurrentNotificationsByAppUID(new Date(), appUid);
	}


	@Override
	public List<Notification> getAllNotificationsByAppUid(String appUid) {
		logger.debug("Récupération de toutes les notifications pour l'appUID {}", appUid);
		return notificationDAO.findAllNotificationsByAppUID(appUid);
	}


	@Override
	public Notification toggleActif(long notifId) {
		logger.debug("Changement de l'état de la notification {}", notifId);
		Notification notif = notificationDAO.findOne(notifId);
		notif.toggleActif();
		return notificationDAO.save(notif);
	}


	@Override
	public void delete(long notifId) {
		logger.debug("Suppression de la notification {}", notifId);
		notificationDAO.delete(notifId);
	}


	@Override
	public Notification saveNotifWithAppUID(Notification newNotif, String appUID) {
		logger.debug("Création d'une nouvelle notification pour l'application {}", appUID);
		Application application = applicationService.getByAppUID(appUID);
		newNotif.setApplication(application);
		return notificationDAO.save(newNotif);
	}


	@Override
	public Notification updateNotif(Notification updatedNotif, long notifId) {
		logger.debug("Update de la notif {}", notifId);
		Notification storedNotification = notificationDAO.findOne(notifId);
		storedNotification.setActif(updatedNotif.isActif());
		storedNotification.setMessage(updatedNotif.getMessage());
		storedNotification.setType(updatedNotif.getType());
		storedNotification.setDateDebut(updatedNotif.getDateDebut());
		storedNotification.setDateFin(updatedNotif.getDateFin());
		return notificationDAO.save(storedNotification);
	}
}
