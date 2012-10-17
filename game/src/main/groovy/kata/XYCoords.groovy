package kata


class XYCoords implements Coords<XYCoords> {
    int x

    static XYCoords coords(int x, int y) {
        new XYCoords(x:x)
    }

    @Override
    XYCoords update(Direction direction, Velocity velocity) {
        new XYCoords(x:x + 1 * velocity.multiplier)
    }
}
