package kata


interface Player<C extends Coords> extends Iterator<Player>, Interactable<C> {
    void joins(World<C> world)
}
