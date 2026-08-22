public class Deadline extends Task{
    protected String dueDate;
    Deadline(boolean marked, String name, String dueDate) {
        super(marked, name);
        this.dueDate = dueDate;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    @Override
    public String toString() {
        return String.format("[D]" + super.toString() + " (by: %s)", getDueDate());
    }
}
