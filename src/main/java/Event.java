public class Event extends Task {
    protected String from;
    protected String to;

    Event(boolean marked, String name, String from, String to) {
        super(marked, name);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    @Override
    public String toString() {
        return String.format("[E]" + super.toString() + " (from: %s to: %s)", getFrom(), getTo());
    }
}
