package basejava.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private LocalDate startTime;
    private LocalDate endTime;
    private String title;
    private String description;

    public Period(LocalDate startTime, LocalDate endTime, String title, String description) {
        Objects.requireNonNull(startTime, "startTime is not null");
        Objects.requireNonNull(endTime, "endTime is not null");
        Objects.requireNonNull(title, "title is not null");
        Objects.requireNonNull(description, "description is not null");
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return Objects.equals(startTime, period.startTime) && Objects.equals(endTime, period.endTime) && Objects.equals(title, period.title) && Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, title, description);
    }

}
