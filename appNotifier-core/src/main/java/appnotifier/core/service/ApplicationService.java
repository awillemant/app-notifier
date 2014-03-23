package appnotifier.core.service;

import java.util.List;
import appnotifier.core.entity.Application;

public interface ApplicationService {

	List<Application> getAll();


	void save(Application newApp);


	void update(long id, Application updatedApp);


	void delete(long id);


	Application getByAppUID(String appUID);
}
