package basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private final static long serialVersionUID = 1L;
    private String content;
    private List<Period> periods = new ArrayList<>();
    private String webSite;

    public Organization() {
    }

    public Organization(String content, String webSite, Period... periods) {
        this.content = content;
        this.webSite = webSite;
        this.periods = Arrays.asList(periods);
    }

    public Organization(String content, List<Period> periods, String webSite) {
        Objects.requireNonNull(content, "content is not null");
        Objects.requireNonNull(periods, "periods is not null");
        Objects.requireNonNull(webSite, "website is not null");
        this.content = content;
        this.periods = periods;
        this.webSite = webSite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(content, that.content) && Objects.equals(periods, that.periods) && Objects.equals(webSite, that.webSite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, periods, webSite);
    }

    public String getContent() {
        return content;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public String getWebSite() {
        return webSite;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "content='" + content + '\'' +
                ", webSite='" + webSite + '\'' +
                '}';
    }
}
