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

import appnotifier.core.entity.Application;
import appnotifier.core.service.ApplicationService;

import com.fasterxml.jackson.annotation.JsonView;

@Controller
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/rs/applications")
public class ApplicationsWS {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationsWS.class);

    @Autowired
    private ApplicationService applicationService;

    @GET
    @Path("/")
    @JsonView(Application.ListView.class)
    public List<Application> getAllApplications() {
        LOGGER.info("recupération de toutes les applications");
        return applicationService.getAll();
    }

    @GET
    @Path("/{appUID}")
    @JsonView(Application.MinimalView.class)
    public Application getApplicationByUID(@PathParam("appUID") String appUID) {
        LOGGER.info("Récupération de l'application {}", appUID);
        return applicationService.getByAppUID(appUID);
    }

    @POST
    @Path("/")
    public void saveApplication(Application newApp) {
        LOGGER.info("Sauvegarde d'une nouvelle application");
        applicationService.save(newApp);
    }

    @POST
    @Path("/{id}")
    public void updateApplication(@PathParam("id") long id, Application updatedApp) {
        LOGGER.info("Mise à jour de l'application {}", id);
        applicationService.update(id, updatedApp);
    }

    @DELETE
    @Path("/{id}")
    public void deleteApplication(@PathParam("id") long id) {
        LOGGER.info("Suppression de l'application {}", id);
        applicationService.delete(id);
    }
}