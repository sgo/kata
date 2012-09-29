package kata

import spock.lang.Specification


class GameSpec extends Specification {
    private Game game = new Game()
    def player = Mock(Player)
    def world = Mock(World)

    def "add player to game"() {
        expect:
        game.add(player)
    }

    def "add world to game"() {
        expect:
        game.add(world)
    }

    def "at game start player joins world"() {
        given:
        game.add(player)
        game.add(world)

        when:
        game.start()

        then:
        1 * world.spawns(player)
    }
}
