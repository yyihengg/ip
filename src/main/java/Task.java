public class Task {
    private boolean marked;
    private final String name;

    Task(boolean marked, String name) {
        this.marked = marked;
        this.name = name;
    }
    public boolean getMarked() {
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
        if (this.marked) {
            return String.format("[X] %s", getName());
        }
        return String.format("[ ] %s", getName());
    }
}
