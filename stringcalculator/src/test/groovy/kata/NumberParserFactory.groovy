package kata

import static java.util.regex.Pattern.quote

class NumberParserFactory implements Iterable<Integer> {
    private String splitterRegex = /,|\n/
    private String userDefinedDelimiterMark = '//'
    private int upperBound = 1000

    private String input

    NumberParserFactory(String input) {
        this.input = input
        if (userDefinedDelimitersFound) {
            registerUserDefinedDelimiters()
            stripUserDefinedDelimiters()
        }
    }

    private boolean isUserDefinedDelimitersFound() {
        input.startsWith(userDefinedDelimiterMark)
    }

    private String registerUserDefinedDelimiters() {
        userDefinedDelimiters.each {splitterRegex += "|${quote(it)}"}
    }

    private Iterable<String> getUserDefinedDelimiters() {
        def delimiter = input.substring(2, input.indexOf('\n'))
        isMultiChar(delimiter) ? extractMultiCharDelimiters(delimiter) : [delimiter]
    }

    private boolean isMultiChar(String delimiter) {
        delimiter.startsWith('[')
    }

    private Iterable<String> extractMultiCharDelimiters(String delimiter) {
        new DelimiterSequenceFactory(delimiter)
    }

    private void stripUserDefinedDelimiters() {
        input = input.substring(input.indexOf('\n'))
    }

    @Override
    Iterator<Integer> iterator() {
        List<Integer> numbers = input.trim().split(splitterRegex)*.toInteger()
        numbers = numbers.findAll(withinBounds)
        if(containsNegative(numbers)) raiseOutOfBound()
        else numbers.iterator()
    }

    private def withinBounds = {it <= upperBound}

    private boolean containsNegative(ArrayList<Integer> numbers) {
        numbers.any { number -> number < 0 }
    }

    def raiseOutOfBound() {
        throw new IllegalArgumentException()
    }
}
