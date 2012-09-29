package kata


class SinglePlayerWorld<C extends Coords> implements World<C> {
    C spawnPoint
    Player player
    def blocks = [] as LinkedList<Block>

    @Override
    void spawns(Player<C> player) {
        this.player = player
        player.position(spawnPoint)
        player.joins(this)
    }

    @Override
    boolean hasNext() {
        true
    }

    @Override
    World next() {
        player.falling()
        player.next()
        return this
    }

    @Override
    void remove() {
        throw new UnsupportedOperationException()
    }

    void add(Block block) {
        blocks << block
    }

    @Override
    void validate(Interactable<C> interactable, C coords) {
        interactable.position(coords)
        blocks*.validate(interactable, coords)
    }
}
