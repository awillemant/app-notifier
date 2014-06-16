package appnotifier.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import appnotifier.core.entity.Notification;
import appnotifier.core.service.NotificationService;
import com.fasterxml.jackson.annotation.JsonView;

@Controller
@Produces(MediaType.APPLICATION_JSON)
@Path("/rs/public")
public class PublicWS {

    @Autowired
    private NotificationService notificationService;

    @Context
    private HttpServletRequest request;

    @GET
    @Path("/{appUid}")
    @JsonView(Notification.ListView.class)
    public List<Notification> getNotificationsByAppUid(@PathParam("appUid") String appUid) {
        List<Notification> notifications = notificationService.getCurrentNotificationsByAppUid(appUid);
        return notifications;
    }
}