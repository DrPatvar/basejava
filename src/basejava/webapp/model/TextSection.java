package basejava.webapp.model;

import java.util.Objects;

public class TextSection  extends  AbstractSection{
    private String content;

    public TextSection(String content) {
        Objects.requireNonNull(content, "content is not null");
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
