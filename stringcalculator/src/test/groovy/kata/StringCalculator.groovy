package kata

import static java.util.regex.Pattern.quote

class StringCalculator {
    private String splitterRegex = /,|\n/
    private String userDefinedDelimiterMark = '//'

    int run(String input) {
        return input.empty ? 0 : sum(input);
    }

    private int sum(String input) {
        parseNumbers(input).sum()
    }

    private List<Integer> parseNumbers(String input) {
        if(userDefinedDelimitersOn(input)) {
            registerUserDefinedDelimiters(extractUserDefinedDelimiters(input))
            input = stripUserDefinedDelimiters(input, extractUserDefinedDelimiters(input))
        }
        toNumbers(input)
    }

    private def toNumbers(String input) {
        List<Integer> numbers = input.trim().split(splitterRegex)*.toInteger()
        containsNegative(numbers) ? raiseOutOfBound() : numbers
    }

    private boolean containsNegative(ArrayList<Integer> numbers) {
        numbers.any { number -> number < 0 }
    }

    def raiseOutOfBound() {
        throw new IllegalArgumentException()
    }

    private String registerUserDefinedDelimiters(String delimiter) {
        splitterRegex += "|${quote(delimiter)}"
    }

    private String stripUserDefinedDelimiters(String input, String delimiter) {
        input -= "//$delimiter"
        return input
    }

    private String extractUserDefinedDelimiters(String input) {
        input.substring(2, input.indexOf('\n'))
    }

    private boolean userDefinedDelimitersOn(String input) {
        input.startsWith(userDefinedDelimiterMark)
    }
}
