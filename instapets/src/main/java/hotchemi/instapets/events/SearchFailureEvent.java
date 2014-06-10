package hotchemi.instapets.events;

public final class SearchFailureEvent {

    private final int statusCode;

    private final String content;

    public SearchFailureEvent(int statusCode, String content) {
        this.statusCode = statusCode;
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getContent() {
        return content;
    }

}