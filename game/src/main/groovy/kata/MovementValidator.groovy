package kata


interface MovementValidator<C extends Coords> {
    void validate(Interactable<C> interactable, C coords)
}