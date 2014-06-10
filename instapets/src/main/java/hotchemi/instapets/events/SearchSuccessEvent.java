package hotchemi.instapets.events;

public final class SearchSuccessEvent {

    private final String content;

    public SearchSuccessEvent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}