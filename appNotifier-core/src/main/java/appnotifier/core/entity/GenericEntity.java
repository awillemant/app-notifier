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

    protected Date dateCreation;

    protected Date dateModification;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public boolean equals(GenericEntity entity) {
        return this.id.equals(entity.getId());
    }

    @PreUpdate
    public void preUpdate() {
        dateModification = new Date();
    }

    @PrePersist
    public void prePersist() {
        Date now = new Date();
        dateCreation = now;
        dateModification = now;
    }
}
