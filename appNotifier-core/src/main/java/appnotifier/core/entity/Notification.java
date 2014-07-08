package appnotifier.core.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import appnotifier.core.enumeration.TypeNotification;
import appnotifier.core.view.GenericView;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification extends GenericEntity {

    public interface ListView extends GenericView {
    }

    @Transient
    private SimpleDateFormat sdf;

    @JsonView(ListView.class)
    private String message;

    @JsonView(ListView.class)
    private TypeNotification type;

    @JsonView(ListView.class)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateDebut;

    @JsonView(ListView.class)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateFin;

    @JsonView(ListView.class)
    private boolean actif;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    public Notification() {
        super();
        sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TypeNotification getType() {
        return type;
    }

    public void setType(TypeNotification type) {
        this.type = type;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public boolean hasGoodDates() {
        return dateDebut.before(dateFin);
    }

    @Transient
    public String getDateDebutString() {
        return sdf.format(dateDebut);
    }

    @Transient
    public String getDateFinString() {
        return sdf.format(dateFin);
    }

    @JsonView(ListView.class)
    public boolean isShown() {
        Date now = new Date();
        return actif && dateDebut.before(now) && dateFin.after(now);
    }

    public void toggleActif() {
        actif = (actif == false);
    }
}
