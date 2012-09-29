package kata

import spock.lang.Specification

/**
 * 1. empty string => 0
 * 2. "1" => 1
 * 3. "1,2" => 3
 * 4. "2\n3" => 5
 * 5. "1,2\n3" => 6
 * 6. "//#\n1#2" => 3
 * 7. "//?\n1?2" => 3
 * 8. "-1" => IllegalArgumentException
 * 9. "1,1001,2" => 3
 * 10. "//[###]\n1###2" => 3
 * 11. "//[***]\n1***2" => 3
 * 12. "//[#][%]\n1#2%3" => 6
 * 13. "//[###][%%%]\n1###2%%%3" => 6
 */
class StringCalculatorSpec extends Specification {

    private StringCalculator calculator = new StringCalculator()

    def "calculations"() {
        expect:
        calculator.run(input) == result

        where:
        input      | result
        ""         | 0
        "1"        | 1
        "1,2"      | 3
        "2\n3"     | 5
        "1,2\n3"   | 6
        "//#\n1#2" | 3
        "//?\n1?2" | 3
//        "1,1001,2" | 3
    }


    def "testing Exceptions" () {
        given:
        def input = "-1"

        when:
        calculator.run(input)

        then:
        thrown IllegalArgumentException
    }


}
