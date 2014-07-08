package appnotifier.core.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import appnotifier.core.dao.ApplicationDAO;
import appnotifier.core.entity.Application;
import appnotifier.core.service.ApplicationService;

@Transactional
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private static Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    @Autowired
    private ApplicationDAO applicationDAO;

    @PostConstruct
    public void initService() {
        logger.debug("initialisation du service Spring 'ApplicationService'");
    }

    @Override
    public List<Application> getAll() {
        logger.debug("Récupération de toutes les applications");
        List<Application> all = applicationDAO.findAll();
        Collections.sort(all, new Comparator<Application>() {

            @Override
            public int compare(Application o1, Application o2) {
                return o1.getNbOfShownNotifications().compareTo(o2.getNbOfShownNotifications());
            }
        });
        if (all.size() == 1) {
            all.get(0).getNbOfShownNotifications();
        }
        return all;
    }

    @Override
    public void save(Application newApp) {
        logger.debug("Sauvegarde d'une nouvelle application");
        newApp.setUid(UUID.randomUUID().toString());
        applicationDAO.save(newApp);
    }

    @Override
    public void update(long id, Application updatedApp) {
        logger.debug("Edition de l'application {}", id);
        Application storedApplication = applicationDAO.findOne(id);
        storedApplication.setNom(updatedApp.getNom());
        storedApplication.setUrl(updatedApp.getUrl());
        applicationDAO.save(storedApplication);
    }

    @Override
    public void delete(long id) {
        logger.debug("Suppression de l'application {}", id);
        applicationDAO.delete(id);
    }

    @Override
    public Application getByAppUID(String appUID) {
        return applicationDAO.findOneByUid(appUID);
    }
}
