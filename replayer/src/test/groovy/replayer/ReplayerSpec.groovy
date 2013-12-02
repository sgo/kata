package replayer

import spock.lang.Specification

class ReplayerSpec extends Specification {
    def label
    def hitPower = 0
    def counter = 0
    def creeperHealth = 0
    def messages = []

    def handlers = [
            {if(it.action in ['plus', 'multiply']) counter = counter."$it.action"(it.term)},
            {if(it.action == 'set label') label = it.value},
            {if(it.action == 'reverse label') label = label.reverse()},
            {if(it.action == 'spawn creeper') creeperHealth = it.health},
            {if(it.action == 'hit creeper') creeperHealth -= hitPower},
            {if(it.action == 'set hit power') hitPower = it.power}
    ]

    def "counter starts at zero"() {
        expect:
        counter == 0
    }

    def "add message is added to message queue"() {
        given:
        def msg = 'payload'

        when:
        queue(msg)

        then:
        messages == [msg]
    }

    def queue(msg) {
        messages << msg
    }

    def "replayer executes messages from the queue"() {
        given:
        queue(action:'plus', term:term)

        when:
        replay(handlers)

        then:
        counter == term

        where:
        term << [1, 2, 3]
    }

    def "queue several addition messages"() {
        given:
        queue(action:'plus', term:1)
        queue(action:'plus', term:2)

        when:
        replay(handlers)

        then:
        counter == 3
    }

    def "combine addition and multiplication"() {
        given:
        queue(action:'plus', term:2)
        queue(action:'multiply', term:5)

        when:
        replay(handlers)

        then:
        counter == 10
    }

    def "set label"() {
        given:
        queue(action:'set label', value:'hello world!')

        when:
        replay(handlers)

        then:
        label == 'hello world!'
    }

    def "reverse label"() {
        given:
        queue(action:'set label', value:'hello world!')
        queue(action:'reverse label')

        when:
        replay(handlers)

        then:
        label == 'hello world!'.reverse()
    }

    def "change game rules"() {
        given:
        queue(action:'set hit power', power:10)
        queue(action:'spawn creeper', health:10)
        queue(action:'hit creeper')
        queue(action:'set hit power', power:8)
        queue(action:'spawn creeper', health:10)
        queue(action:'hit creeper')

        when:
        handlers[4] = {if(it.action == 'hit creeper') creeperHealth -= hitPower * 1.2}
        messages.each {if(it.action == 'spawn creeper') it.health = it.health * 1.2}
        replay(handlers)

        then:
        creeperHealth == 2 * 1.2
    }

    def "need 3 hits to kill creeper"() {
        given:
        queue(action:'set hit power', power:10)
        queue(action:'spawn creeper', health:30)
        queue(action:'hit creeper')
        queue(action:'hit creeper')

        when:
        handlers[4] = {if(it.action == 'hit creeper') creeperHealth -= hitPower * 1.2}
        messages.each {if(it.action == 'spawn creeper') it.health = it.health * 1.2}
        replay(handlers)

        then:
        creeperHealth == hitPower * 1.2
    }

    def replay(handlers) {
        messages.each {
            handlers*.call(it)
        }
    }
}
