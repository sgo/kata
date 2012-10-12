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
        // [#][%]   [[]     []]    [[]]    [[]]]
//        [delimiter.substring(1, delimiter.length() - 1)]
//        def chars = delimiter.chars as List
//        chars.inject("") {delim, c->
//            delim + (c == "[" ? "" : c)
//        }
        List<String> list = []
        def oneDelimiter = [] as List<String>
        for (int i = 0; i < delimiter.length(); i++) {
            if (delimiter[i].equals('[')) {
                oneDelimiter << extractFirstChar(delimiter, ++i)

                i = readCharsUpToFirstClosingBracket(oneDelimiter, delimiter, i)

                oneDelimiter << continueUpToLastClosingBracket(delimiter, i).join('')
                i += oneDelimiter.last().length()+1
            } else {
                oneDelimiter << delimiter[i]
            }
            list.add(oneDelimiter.join(''))
            oneDelimiter = []
        }
        return list
    }

    private int readCharsUpToFirstClosingBracket(List<String> oneDelimiter, String delimiter, int charsRead) {
        while (!delimiter[++charsRead].equals(']')) oneDelimiter << delimiter[charsRead]
        return charsRead
    }

    private List continueUpToLastClosingBracket(String delimiter, int i) {
        def s3 = []
        if (delimiter[i].equals(']')) {
            while (i < delimiter.length() - 1 && delimiter[++i].equals(']')) {
                if (i < delimiter.length()) {   // "123" 012 3
                    s3 << delimiter[i]
                }
            }
        }
        return s3
    }


    private String extractFirstChar(String delimiter, int i) {
        delimiter[i]
    }

    private boolean userDefinedDelimitersOn(String input) {
        input.startsWith(userDefinedDelimiterMark)
    }
}
