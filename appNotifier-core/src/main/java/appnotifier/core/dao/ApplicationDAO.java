package appnotifier.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import appnotifier.core.entity.Application;

public interface ApplicationDAO extends JpaRepository<Application, Long> {

    Application findOneByUid(String appUID);
}
