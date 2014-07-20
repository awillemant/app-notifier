package appnotifier.core.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import appnotifier.core.view.GenericView;
import com.fasterxml.jackson.annotation.JsonView;

@MappedSuperclass
public abstract class GenericEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(GenericView.class)
	protected Long id;

	protected Date creationDate;

	protected Date updateDate;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getCreationDate() {
		return creationDate;
	}


	public Date getUpdateDate() {
		return updateDate;
	}


	@PreUpdate
	public void preUpdate() {
		updateDate = new Date();
	}


	@PrePersist
	public void prePersist() {
		Date now = new Date();
		creationDate = now;
		updateDate = now;
	}
}
