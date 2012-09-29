package kata

import spock.lang.Specification

import static kata.Direction.Right

class XYCoordsSpec extends Specification {
    XYCoords coords = new XYCoords()
    def velocity = Mock(Velocity)
    def multiplier = 3

    XYCoords target

    def setup() {
        velocity.multiplier >> multiplier
    }

    def "moving right"() {
        when:
        target = coords.update(Right, velocity)

        then:
        target.x == coords.x + 1 * multiplier
    }
}
