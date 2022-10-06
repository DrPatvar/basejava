package basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private String content;
    private List<Period> periods;
    private String webSite;


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
}
