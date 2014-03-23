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
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import appnotifier.core.entity.Notification;
import appnotifier.core.service.ApplicationService;
import appnotifier.core.service.NotificationService;
import com.fasterxml.jackson.annotation.JsonView;

@Controller
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/rs/notifications")
public class NotificationsWS {

	private static Logger logger = LoggerFactory.getLogger(NotificationsWS.class);

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private NotificationService notificationService;


	@Secured("ROLE_BASIC")
	@GET
	@Path("/app/{appUID}")
	@JsonView(Notification.ListView.class)
	public List<Notification> getNotificationsByAppId(@PathParam("appUID") String appUid) {
		logger.info("Récupération des notifications pour l'application {}", appUid);
		return notificationService.getAllNotificationsByAppUid(appUid);
	}


	@Secured("ROLE_BASIC")
	@POST
	@Path("/actif/{notifId}")
	@JsonView(Notification.ListView.class)
	public Notification toggleActif(@PathParam("notifId") long notifId) {
		logger.info("On modifie l'état actif pour la notification {}", notifId);
		return notificationService.toggleActif(notifId);
	}


	@Secured("ROLE_BASIC")
	@DELETE
	@Path("/{notifId}")
	public void deleteNotification(@PathParam("notifId") long notifId) {
		logger.info("On supprime la notification {}", notifId);
		notificationService.delete(notifId);
	}


	@Secured("ROLE_BASIC")
	@POST
	@Path("/app/{appUID}")
	@JsonView(Notification.ListView.class)
	public Notification saveNotifWithAppUID(@PathParam("appUID") String appUid, Notification newNotif) {
		logger.info("Sauvegarde d'une nouvelle notif l'application {}", appUid);
		return notificationService.saveNotifWithAppUID(newNotif, appUid);
	}


	@Secured("ROLE_BASIC")
	@POST
	@Path("/{notifId}")
	@JsonView(Notification.ListView.class)
	public Notification updateNotif(@PathParam("notifId") long notifId, Notification updatedNotif) {
		logger.info("Edition de la notification {}", notifId);
		return notificationService.updateNotif(updatedNotif, notifId);
	}
	/*
	 * @GET
	 * 
	 * @Path("/{notifId}") public Notification
	 * getNotificationById(@PathParam("notifId") long notifId) {
	 * logger.debug("Récupération de la notification {}", notifId); return
	 * notificationService.getById(notifId); }
	 * 
	 * 
	 * @DELETE
	 * 
	 * @Path("/{notifId}") public void
	 * deleteNotificationById(@PathParam("notifId") long notifId) {
	 * logger.debug("Suppression de la notification {}", notifId);
	 * notificationService.delete(notifId); }
	 * 
	 * 
	 * @POST
	 * 
	 * @Path("/{notifId}") public Notification
	 * updateNotification(@PathParam("notifId") long id, Notification
	 * notifToUpdate) { logger.debug("Mise à jour de la notification {}", id);
	 * return notificationService.update(id, notifToUpdate); }
	 * 
	 * 
	 * @POST
	 * 
	 * @Path("/") public Notification saveNotification(Notification newNotif) {
	 * logger.debug("création de notification"); return
	 * notificationService.save(newNotif); }
	 */
}