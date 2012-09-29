package kata

import spock.lang.Ignore
import spock.lang.Specification

import static com.google.common.collect.Iterators.limit
import static kata.MoveRightCommand.moveRightWith
import static kata.SimpleVelocity.multiplier
import static kata.XYCoords.coords

class AcceptanceScenarioSpec extends Specification {
    def ticks = 5
    def puppet = new Puppet<XYCoords>()
    def world = new SinglePlayerWorld<XYCoords>()

    def setup() {
        puppet.runningSpeed = multiplier(1)
        world.spawnPoint = coords(0, 0)
        world.spawns(puppet)
    }

    def "a puppet which moves right for 5 ticks of world time"() {
        given:
        puppet.brain << moveRightWith(puppet)

        when:
        limit(world, ticks).each {}

        then:
        puppet.coords.x == 1 * puppet.runningSpeed.multiplier * ticks
    }

    @Ignore
    def "a puppet which tries to move right with a block barring his way"() {
        expect:
        false
    }
}
