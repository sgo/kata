package kata

import spock.lang.Specification

class RomanNumeralsSpec extends Specification {
    def "to roman numerals"() {
        given:
        def converter = new Converter()

        expect:
        converter.convert(input) == output

        where:
        input | output
        0     | ''
    }

    class Converter {
        def convert(n) {}
    }
}
