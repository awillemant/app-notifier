package appnotifier.core.enumeration;

public enum TypeNotification {
    INFO("Info"), WARNING("Warning"), ERROR("Error");

    private String displayName;

    private TypeNotification(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
