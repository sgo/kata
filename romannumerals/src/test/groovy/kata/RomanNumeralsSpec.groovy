package kata

import spock.lang.Specification

class RomanNumeralsSpec extends Specification {
    def "to roman numerals"() {
        given:
        def converter = new Converter()
        converter.inputZero = 0
        converter.outputZero = ''
        converter.predicateFactory = { n -> return { n >= it.key } }
        converter.map = [
                1000: 'M',
                900: 'CM',
                500: 'D',
                400: 'CD',
                100: 'C',
                90: 'XC',
                50: 'L',
                40: 'XL',
                10: 'X',
                9: 'IX',
                5: 'V',
                4: 'IV',
                1: 'I'
        ]

        expect:
        converter.convert(input) == output

        where:
        input | output
        0     | ''
        1     | 'I'
        2     | 'II'
        3     | 'III'
        4     | 'IV'
        5     | 'V'
        6     | 'VI'
        7     | 'VII'
        8     | 'VIII'
        9     | 'IX'
        10    | 'X'
        11    | 'XI'
        40    | 'XL'
        50    | 'L'
        90    | 'XC'
        100   | 'C'
        400   | 'CD'
        500   | 'D'
        900   | 'CM'
        1000  | 'M'
        1903  | 'MCMIII'
        1954  | 'MCMLIV'
        1990  | 'MCMXC'
    }

    def "from roman numerals"() {
        given:
        def converter = new Converter()
        converter.inputZero = ''
        converter.outputZero = 0
        converter.predicateFactory = { n -> return { n.startsWith(it.key) } }
        converter.map = [
                'C': 100,
                'XC': 90,
                'L': 50,
                'XL': 40,
                'X': 10,
                'IX': 9,
                'V': 5,
                'IV': 4,
                'I': 1
        ]

        expect:
        converter.convert(input) == output

        where:
        input  | output
        ''     | 0
        'I'    | 1
        'II'   | 2
        'III'  | 3
        'IV'   | 4
        'V'    | 5
        'VI'   | 6
        'VII'  | 7
        'VIII' | 8
        'IX'   | 9
        'X'    | 10
        'XL'   | 40
        'L'    | 50
        'XC'   | 90
        'C'    | 100
    }

    def "atm"() {
        given:
        def converter = new Converter()
        converter.inputZero = 0
        converter.outputZero = []
        converter.predicateFactory = { n -> return { n >= it.key } }
        converter.map = [
                10: [10],
                5: [5]
        ]

        expect:
        converter.convert(input) == output

        where:
        input | output
        5     | [5]
        10    | [10]
        15    | [10, 5]
    }

    class Converter {
        def map
        def inputZero
        def outputZero
        def predicateFactory

        def convert(n) { n == inputZero ? outputZero : reduce(n) }

        private def reduce(n) {
            def r = map.entrySet().find(predicateFactory(n))
            r.value + convert(n - r.key)
        }
    }
}
