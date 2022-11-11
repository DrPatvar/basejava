package basejava.webapp.model;

import java.util.Objects;


public class TextSection extends Section {
    private final static long serialVersionUID = 1L;
    private String content;


    public TextSection() {
    }

    public TextSection(String content) {
        Objects.requireNonNull(content, "content is not null");
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void addContent(String content){
         this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return content;
    }
}
