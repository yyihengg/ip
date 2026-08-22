public class Task {
    protected boolean marked;
    protected final String name;

    Task(boolean marked, String name) {
        this.marked = marked;
        this.name = name;
    }
    public boolean isMarked() {
        return this.marked;
    }

    public String getName() {
        return this.name;
    }

    public void mark() {
        this.marked = true;
    }

    public void unmark() {
        this.marked = false;
    }

    @Override
    public String toString() {
        if (this.isMarked()) {
            return String.format("[X] %s", getName());
        }
        return String.format("[ ] %s", getName());
    }
}
