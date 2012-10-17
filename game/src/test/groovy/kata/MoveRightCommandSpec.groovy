package kata

import spock.lang.Specification

import static kata.MoveRightCommand.moveRightWith


class MoveRightCommandSpec extends Specification {

    def moveable = Mock(Moveable)
    def command = moveRightWith(moveable)

    def "test"() {
        when:
        command.execute()

        then:
        1 * moveable.right()
    }
}
