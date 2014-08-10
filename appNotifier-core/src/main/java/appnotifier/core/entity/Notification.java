package appnotifier.core.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import appnotifier.core.enumeration.TypeNotification;
import appnotifier.core.view.GenericView;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification extends GenericEntity {

	public interface ListView extends GenericView {}

	@JsonView(ListView.class)
	private String message;

	@JsonView(ListView.class)
	private TypeNotification type;

	@JsonView(ListView.class)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date startDate;

	@JsonView(ListView.class)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date endDate;

	@JsonView(ListView.class)
	private boolean enabled;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "application_id", nullable = false)
	private Application application;


	public Notification() {
		super();
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


	public boolean hasGoodDates() {
		return startDate.before(endDate);
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	@JsonView(ListView.class)
	public boolean isShown() {
		Date now = new Date();
		return enabled && startDate.before(now) && endDate.after(now);
	}


	public void toggleActiveState() {
		enabled = !enabled;
	}
}
