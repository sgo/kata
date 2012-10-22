package kata

import spock.lang.Specification

/**
 * Prime Factors Kata
 * 1 => []
 * 2 => [2]
 * 3 => [3]
 * 4 => [2,2]
 * 5 => [5]
 * 6 => [2,3]
 * 7 => [7]
 * 8 => [2,2,2]
 * 9 => [3,3]
 * 2*3*5*7*11*13 => [2,3,5,7,11,13]
 * 8191*131071 => [8191,131071]
 * 2**100 => [2]*100
 * 2**19-1 => [2**19-1]
 * 2**31-1 => [2**31-1]
 */
class PrimeFactorsSpec extends Specification {

    private double number

    private int guess = 2

    def "test"() {
        given:
        number = input
        println number

        expect:
        execute() == result

        where:
        input                   | result
        1                       | []
        2                       | [2]
        3                       | [3]
        4                       | [2, 2]
        5                       | [5]
        6                       | [2, 3]
        7                       | [7]
        8                       | [2, 2, 2]
        9                       | [3, 3]
        2 * 3 * 5 * 7 * 11 * 13 | [2, 3, 5, 7, 11, 13]
        8191 * 131071           | [8191, 131071]
        2 ** 100                | [2] * 100
        2 ** 19 - 1             | [2 ** 19 - 1]
        2 ** 31 - 1             | [2 ** 31 - 1]
    }

    def execute() {
        return hasFactors() ? calc() : empty();
    }

    private boolean hasFactors() {
        number > 1
    }

    private def calc() {
        while (guess < number) {
            if (number % guess == 0) {
                number /= guess
                return [guess] + execute()
            }
            // square root
            guess = (guess > Math.sqrt(number) ? number : guess + 1)
        }

        return [number]

    }

    private def empty() {
        []
    }
}
