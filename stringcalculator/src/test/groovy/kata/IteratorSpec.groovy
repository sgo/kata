package kata

import spock.lang.Specification


class IteratorSpec extends Specification {

    def "iterators"() {
        given:
        def it = new NumberIterator(5)

        expect:
        for(int i : it) {
            println i
        }
        for(int i : it) {
            println i
        }
    }

    def "iterables"() {
        given:
        def it = new Iterable<Integer>() {

            @Override
            Iterator<Integer> iterator() {
                new NumberIterator(5)
            }
        }

        expect:
        for(int i : it) {
            println i
        }
        for(int i : it) {
            println i
        }
    }
}
