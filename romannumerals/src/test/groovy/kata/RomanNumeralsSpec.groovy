package kata

import spock.lang.Ignore
import spock.lang.Specification

/**
 * I => 1
 * II => 2
 * III => 3
 * IV => 4
 * V => 5
 * VI => 6
 * VII -> 7
 * VIII => 8
 * IX => 9
 * X => 10
 * XL => 40
 * L => 50
 * XC => 90
 * C => 100
 * CD => 400
 * D => 500
 * CM => 900
 * M => 1000
 * MCMX => 1910
 */
class RomanNumeralsSpec extends Specification {
    def "to roman numerals"() {
        given:
        def converter = new Converter()
        converter.zero = ''
        converter.isZero = { it == 0 }
        converter.isLargerThan = { n, key -> n >= key }
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
        1910  | 'MCMX'
    }

    class Converter {
        def map
        def zero
        def isZero
        def isLargerThan

        def convert(n) {
            if (isZero(n)) return zero
            def kv = map.find{ key, value -> isLargerThan(n, key) }
            return kv.value + convert(n - kv.key)
        }
    }
}
