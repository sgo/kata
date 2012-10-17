package kata


class Game {
    Player player
    World world

    Game() {

    }

    void add(Player player) {
        this.player = player
    }

    void add(World world) {
        this.world = world
    }

    void start() {
        world.spawns(player)
    }
}
