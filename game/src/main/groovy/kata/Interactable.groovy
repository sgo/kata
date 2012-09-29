package kata


public interface Interactable<C extends Coords> extends Moveable {
    void position(C coords)
    void falling()
}