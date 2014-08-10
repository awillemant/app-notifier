package appnotifier.core.dao;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import appnotifier.core.entity.Notification;

public interface NotificationDAO extends JpaRepository<Notification, Long> {

	@Query(value = "FROM Notification n WHERE :now > n.startDate AND :now < n.endDate AND n.enabled=true AND n.application.uid=:appuid")
	List<Notification> findCurrentNotificationsByAppUID(@Param("now") Date now, @Param("appuid") String appUID);


	@Query(value = "FROM Notification n WHERE n.application.uid=:appuid")
	List<Notification> findAllNotificationsByAppUID(@Param("appuid") String appUid);
}
