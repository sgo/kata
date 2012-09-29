package kata


class MoveRightCommand implements Command {
    Moveable moveable

    static MoveRightCommand moveRightWith(Moveable moveable) {
        return new MoveRightCommand(moveable:moveable);
    }

    @Override
    void execute() {
        moveable.right()
    }
}
