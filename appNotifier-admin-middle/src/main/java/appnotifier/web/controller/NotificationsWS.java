package appnotifier.web.controller;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import appnotifier.core.entity.Notification;
import appnotifier.core.service.NotificationService;
import com.fasterxml.jackson.annotation.JsonView;

@Controller
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/rs/notifications")
public class NotificationsWS {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationsWS.class);

	@Autowired
	private NotificationService notificationService;


	@GET
	@Path("/app/{appUID}")
	@JsonView(Notification.ListView.class)
	public List<Notification> getNotificationsByAppId(@PathParam("appUID") String appUid) {
		LOGGER.info("getting all notifications for application {}", appUid);
		return notificationService.getAllNotificationsByAppUid(appUid);
	}


	@POST
	@Path("/enabled/{notifId}")
	@JsonView(Notification.ListView.class)
	public Notification toggleActiveState(@PathParam("notifId") long notifId) {
		LOGGER.info("updating active state for notification {}", notifId);
		return notificationService.toggleEnabled(notifId);
	}


	@DELETE
	@Path("/{notifId}")
	public void deleteNotification(@PathParam("notifId") long notifId) {
		LOGGER.info("deleting notification {}", notifId);
		notificationService.delete(notifId);
	}


	@POST
	@Path("/app/{appUID}")
	@JsonView(Notification.ListView.class)
	public Notification saveNotifWithAppUID(@PathParam("appUID") String appUid, Notification newNotif) {
		LOGGER.info("saving a new notification for application {}", appUid);
		return notificationService.saveNotifWithAppUID(newNotif, appUid);
	}


	@POST
	@Path("/{notifId}")
	@JsonView(Notification.ListView.class)
	public Notification updateNotif(@PathParam("notifId") long notifId, Notification updatedNotif) {
		LOGGER.info("updating notification {}", notifId);
		return notificationService.updateNotif(updatedNotif, notifId);
	}
}