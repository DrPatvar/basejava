package basejava.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class ListSection extends Section {
    private final static long serialVersionUID = 1L;
    private List<String> stringList;

    public ListSection() {
    }

    public ListSection(String... stringList) {
        this.stringList = Arrays.asList(stringList);
    }

    public ListSection(List<String> stringList) {
        Objects.requireNonNull(stringList, "strings is not null");
        this.stringList = stringList;
    }

    public List<String> getStringList() {
        return stringList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return stringList.equals(that.stringList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringList);
    }

    @Override
    public String toString() {
        return stringList.toString();
    }
}
