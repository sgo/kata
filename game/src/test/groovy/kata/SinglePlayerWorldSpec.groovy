package kata

import spock.lang.Specification

class SinglePlayerWorldSpec extends Specification {
    def world = new SinglePlayerWorld()
    def player = Mock(Player)
    def spawnPoint = Mock(Coords)
    def coords = Mock(Coords)
    def block1 = Mock(Block)
    def block2 = Mock(Block)

    def setup() {
        world.spawnPoint = spawnPoint
    }

    def "spawning a player sets his position as he joins the world"() {
        when:
        world.spawns(player)

        then:
        1 * player.position(spawnPoint)
        1 * player.joins(world)
    }

    def "a world can usually progress"() {
        expect:
        world.hasNext()
    }

    def "remove is forced upon us by the iterator interface and is not supported"() {
        when:
        world.remove()

        then:
        thrown UnsupportedOperationException
    }

    def "next progresses time and returns the updated world"() {
        given:
        world.spawns(player)

        expect:
        world.next() == world
    }

    def "as the player is floating in the void he starts falling as soon as time progresses"() {
        given:
        world.spawns(player)

        when:
        world.next()

        then:
        1 * player.falling()
    }

    def "as time progresses for the world so does time progress for the player"() {
        given:
        world.spawns(player)

        when:
        world.next()

        then:
        1 * player.next()
    }

    def "validating coordinates with the world validates them with every block in the world"() {
        given:
        world.add(block1)
        world.add(block2)

        when:
        world.validate(player, coords)

        then:
        1 * player.position(coords)

        then:
        1 * block1.validate(player, coords)
        1 * block2.validate(player, coords)
    }
}
