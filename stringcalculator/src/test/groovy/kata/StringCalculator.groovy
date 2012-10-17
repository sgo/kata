package kata

import static java.util.regex.Pattern.quote

class StringCalculator {
    private String splitterRegex = /,|\n/
    private String userDefinedDelimiterMark = '//'

    private int upperBound = 1000

    int run(String input) {
        return input.empty ? 0 : sum(input);
    }

    private int sum(String input) {
        parseNumbers(input).sum()
    }

    private List<Integer> parseNumbers(String input) {
        if (userDefinedDelimitersOn(input)) {
            registerUserDefinedDelimiters(extractUserDefinedDelimiters(input))
            input = stripUserDefinedDelimiters(input)
        }
        toNumbers(input)
    }

    private def toNumbers(String input) {
        List<Integer> numbers = input.trim().split(splitterRegex)*.toInteger()
        numbers = numbers.findAll(withinBounds)
        containsNegative(numbers) ? raiseOutOfBound() : numbers
    }

    private def withinBounds = {it <= upperBound}

    private boolean containsNegative(ArrayList<Integer> numbers) {
        numbers.any { number -> number < 0 }
    }

    def raiseOutOfBound() {
        throw new IllegalArgumentException()
    }

    private String registerUserDefinedDelimiters(List<String> delimiter) {
        delimiter.each {splitterRegex += "|${quote(it)}"}
    }

    private String stripUserDefinedDelimiters(String input) {
        return input.substring(input.indexOf('\n'))
    }

    private List<String> extractUserDefinedDelimiters(String input) {
        def delimiter = input.substring(2, input.indexOf('\n'))
        isMultiChar(delimiter) ? extractMultiChar(delimiter) : [delimiter]
    }

    private boolean isMultiChar(String delimiter) {
        delimiter.startsWith('[')
    }

    private List<String> extractMultiChar(String delimiter) {
        new DelimiterExtractor(delimiterLine: delimiter).extract()
    }

    private boolean userDefinedDelimitersOn(String input) {
        input.startsWith(userDefinedDelimiterMark)
    }

    private static class DelimiterExtractor {
        String delimiterLine
        int currentChar
        List<String> bufferedDelimiter = []
        List<String> delimiters = []

        public List extract() {
            // TODO google: uncle bob extract method object
            while (anyCharactersLeft()) {
                delimiters << extractDelimiter()
                resetBuffer()
            }
            return delimiters
        }

        private String extractDelimiter() {
            multiCharDelimiter ? extractMultiCharDelimiter() : extractOneCharDelimiter()
            bufferedDelimiter.join('')
        }

        private void resetBuffer() {
            bufferedDelimiter = []
            currentChar++
        }

        private boolean isMultiCharDelimiter() {
            delimiterLine[currentChar].equals('[')
        }

        private void extractMultiCharDelimiter() {
            while (isNotClosingChar(nextChar) || isMultipleClosingChars()) {
                currentChar++
                readChar()
            }
            // [###][%%%]    [[]][[[]]]
        }

        private boolean isMultipleClosingChars() {
            isNotCharNearEnd() && isClosingChar(nextChar+1)
        }

        private boolean isNotCharNearEnd() {
            currentChar < delimiterLine.length() - 2
        }

        private void readChar() {
            if (anyCharactersLeft()) {
                bufferedDelimiter << delimiterLine[currentChar]
            }
        }

        private void extractOneCharDelimiter() {
            bufferedDelimiter << delimiterLine[currentChar]
        }



        private boolean isNotClosingChar(int index) {
            !isClosingChar(index)
        }

        private boolean isClosingChar(int index) {
            delimiterLine[index].equals(']')
        }

        private int getNextChar() {
            currentChar + 1
        }

        private boolean anyCharactersLeft() {
            currentChar < delimiterLine.length()
        }
    }
}
