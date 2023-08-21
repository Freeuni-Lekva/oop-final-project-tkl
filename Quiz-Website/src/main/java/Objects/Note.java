package Objects;

public class Note {
    private final long author_id;

    private final String text;

    public Note(long author_id, String text) {
        this.author_id = author_id;
        this.text = text;
    }

    public long getAuthor_id() {
        return author_id;
    }

    public String getText() {
        return text;
    }
}
