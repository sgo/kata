package kata

import static kata.Direction.Right


class Puppet<C extends Coords> implements Player<C> {
    Velocity runningSpeed
    World<C> world
    C coords
    def brain = [] as Set<Command>

    @Override
    void position(C coords) {
        this.coords = coords
    }

    @Override
    void right() {
        def target = coords.update(Right, runningSpeed)
        world.validate(this, target)
    }

    @Override
    void joins(World<C> world) {
        this.world = world
    }

    @Override
    void falling() {

    }

    @Override
    boolean hasNext() {true}

    @Override
    Player next() {
        brain*.execute()
        return this
    }

    @Override
    void remove() {
        throw new UnsupportedOperationException()
    }
}
