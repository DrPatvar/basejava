package basejava.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class ListSection extends Section {
    private final static long serialVersionUID = 1L;
    private List<String> strings;

    public ListSection() {
    }

    public ListSection(String... strings) {
        this.strings = Arrays.asList(strings);
    }

    public ListSection(List<String> strings) {
        Objects.requireNonNull(strings, "strings is not null");
        this.strings = strings;
    }

    public List<String> getStrings() {
        return strings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return strings.equals(that.strings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strings);
    }

    @Override
    public String toString() {
        return strings.toString();
    }
}
