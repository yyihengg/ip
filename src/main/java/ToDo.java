public class ToDo extends Task{
    ToDo(boolean marked, String name) {
        super(marked, name);
    }

    @Override
    public String toString() {
        return String.format("[T]" + super.toString());
    }
}

