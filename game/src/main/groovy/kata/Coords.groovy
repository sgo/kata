package kata


public interface Coords<T extends Coords> {
    T update(Direction direction, Velocity velocity)
}