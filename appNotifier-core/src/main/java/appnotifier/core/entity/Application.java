package appnotifier.core.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import appnotifier.core.view.GenericView;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Application extends GenericEntity {

	public interface MinimalView extends GenericView {}

	public interface ListView extends MinimalView {}

	@JsonView(MinimalView.class)
	private String nom;

	@JsonView(MinimalView.class)
	private String url;

	@JsonView(MinimalView.class)
	private String uid;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "application")
	private List<Notification> notifications;


	public Application() {
		super();
		notifications = new ArrayList<Notification>();
	}


	public Long getId() {
		return id;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public List<Notification> getNotifications() {
		if (notifications == null) {
			notifications = new ArrayList<Notification>();
		}
		return notifications;
	}


	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	@JsonView(ListView.class)
	public Integer getNbOfShownNotifications() {
		if (notifications == null) {
			return 0;
		}
		int count = 0;
		for (Notification notif : getNotifications()) {
			if (notif.isShown()) {
				count++;
			}
		}
		return count;
	}
}
