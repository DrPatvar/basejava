package basejava.webapp.model;

import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;
    private  String fullName;

    public Resume(String fullName) {
        this.uuid = (UUID.randomUUID().toString());
        this.fullName = fullName;

    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Resume resume = (Resume) obj;
        return fullName.equals(resume.fullName);
    }

    @Override
    public String toString() {
        return uuid + " " + fullName;
    }
}
