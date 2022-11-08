package basejava.webapp.model;

import basejava.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

import static basejava.webapp.util.DateUtil.NOW;
import static basejava.webapp.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Period implements Serializable {
    private final static long serialVersionUID = 1L;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate startTime;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate endTime;
    private String title;
    private String description;

    public Period() {
    }

    public Period(int startYear, Month startMonth, String title, String description) {
        this(of(startYear, startMonth), NOW, title, description);
    }

    public Period(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
        this(of(startYear, startMonth), of(endYear, endMonth), title, description);
    }

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

    public LocalDate getStartTime() {
        return startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Period{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
