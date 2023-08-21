package Objects;

public class Note {

    private final long id;
    private final long authorId;
    private final String text;

    public Note(long id, long authorId, String text) {
        this.id = id;
        this.authorId = authorId;
        this.text = text;
    }

    public long getNoteId() { return id; }
    public long getAuthorId() {
        return authorId;
    }
    public String getText() {
        return text;
    }
}
