package kata

import spock.lang.Specification

import static kata.Direction.Right


class PuppetSpec extends Specification {
    Puppet puppet = new Puppet()
    def world = Mock(World)
    def runningSpeed = Mock(Velocity)
    def coords = Mock(Coords)
    def targetCoords = Mock(Coords)
    def command1 = Mock(Command)
    def command2 = Mock(Command)

    def setup() {
        puppet.brain << command1
        puppet.brain << command2
        puppet.runningSpeed = runningSpeed
        puppet.position(coords)
        puppet.joins(world)
    }

    def "moving right calculates new coords and validates them with the world"() {
        when:
        puppet.right()

        then:
        1 * coords.update(Right, runningSpeed) >> targetCoords
        1 * world.validate(puppet, targetCoords)
    }

    def "hasNext is needed for iterating so always true"() {
        expect:
        puppet.hasNext()
    }

    def "remove is forced upon us by iterable interface but not supported"() {
        when:
        puppet.remove()

        then:
        thrown UnsupportedOperationException
    }

    def "progressing time executes each command in the puppet's brains"() {
        when:
        puppet.next()

        then:
        1 * command1.execute()
        1 * command2.execute()
    }

    def "next returns itself"() {
        expect:
        puppet.next() == puppet
    }
}
