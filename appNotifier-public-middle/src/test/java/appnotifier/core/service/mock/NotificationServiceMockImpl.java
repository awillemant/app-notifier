package appnotifier.core.service.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import appnotifier.core.entity.Notification;
import appnotifier.core.service.NotificationService;

public class NotificationServiceMockImpl implements NotificationService {

	@Override
	public List<Notification> getCurrentNotificationsByAppUid(String appUid) {
		List<Notification> retour = new ArrayList<Notification>();
		Notification n1 = new Notification();
		n1.setMessage("Message1");
		n1.setEnabled(true);
		n1.setStartDate(new Date());
		n1.setEndDate(new Date());
		retour.add(n1);
		Notification n2 = new Notification();
		n2.setMessage("Message2");
		n2.setEnabled(true);
		n2.setStartDate(new Date());
		n2.setEndDate(new Date());
		retour.add(n2);
		return retour;
	}


	@Override
	public List<Notification> getAllNotificationsByAppUid(String appUid) {
		return null;
	}


	@Override
	public Notification toggleEnabled(long notifId) {
		return null;
	}


	@Override
	public void delete(long notifId) {
	}


	@Override
	public Notification saveNotifWithAppUID(Notification newNotif, String appUid) {
		return null;
	}


	@Override
	public Notification updateNotif(Notification updatedNotif, long notifId) {
		return null;
	}
}
