package kata

import spock.lang.Specification

class RomanNumeralsSpec extends Specification {
    def "to roman numerals"() {
        given:
        def converter = new Converter()
        converter.zero = ''
        converter.isZero = { it > 0 }
        converter.isLargest = { x, y -> x >= y }
        converter.sorter = { x, y -> (x.key <=> y.key) * -1 }
        converter.map = [
                500: 'D',
                100: 'C',
                90: 'XC',
                10: 'X',
                50: 'L',
                900: 'CM',
                1: 'I',
                40: 'XL',
                4: 'IV',
                400: 'CD',
                9: 'IX',
                1000: 'M',
                5: 'V'
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
        40    | 'XL'
        50    | 'L'
        90    | 'XC'
        100   | 'C'
        400   | 'CD'
        500   | 'D'
        900   | 'CM'
        1000  | 'M'
        1910  | 'MCMX'
    }

    def "from roman numeral"() {
        given:
        def converter = new Converter()
        converter.zero = 0
        converter.isZero = { !it.isEmpty() }
        converter.isLargest = { x, y -> x.startsWith(y) }
        converter.sorter = { x, y -> (x.value <=> y.value) * -1 }
        converter.map = [
                'I': 1,
                'IX': 9,
                'IV': 4,
                'V': 5
        ]

        expect:
        converter.convert(input) == output

        where:
        input  | output
        ''     | 0
        'I'    | 1
        'IV'   | 4
        'V'    | 5
        'VIII' | 8
        'IX'   | 9
    }

    def "automated teller machine"() {
        given:
        Converter converter = createATMConverter()

        expect:
        converter.convert(input) == output

        where:
        input | output
        0     | []
        5     | [5]
        10    | [10]
        15    | [10, 5]
    }

    private Converter createATMConverter() {
        def converter = new Converter()
        converter.zero = []
        converter.isZero = { it > 0 }
        converter.isLargest = { x, y -> x >= y }
        converter.sorter = { x, y -> (x.key <=> y.key) * -1 }
        converter.map = [
                10: [10],
                5: [5]
        ]
        converter
    }

    class Converter {
        def map
        def zero
        def isZero
        def isLargest
        def sorter

        def convert(input) {
            if (isZero(input)) {
                def largest = map.find { isLargest(input, it.key) }
                return largest.value + convert(input - largest.key)
            } else return zero
        }

        void setMap(map) {
            this.map = (map.entrySet() as List).sort(sorter)
        }
    }
}
