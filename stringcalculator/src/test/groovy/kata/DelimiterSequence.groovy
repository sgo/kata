package kata

class DelimiterSequence implements Iterator<String> {
    private String delimiterLine
    private int currentChar
    private def bufferedDelimiter = new StringBuffer()

    DelimiterSequence(String delimiterLine) {
        this.delimiterLine = delimiterLine
    }

    @Override
    boolean hasNext() {
        anyCharactersLeft()
    }

    private boolean anyCharactersLeft() {
        currentChar < delimiterLine.length()
    }

    @Override
    String next() {
        return extractDelimiter()
    }

    private String extractDelimiter() {
        multiCharDelimiter ? extractMultiCharDelimiter() : extractOneCharDelimiter()
        def delimiter = bufferedDelimiter.toString()
        resetBuffer()
        return delimiter
    }

    private boolean isMultiCharDelimiter() {
        delimiterLine[currentChar].equals('[')
    }

    private void extractMultiCharDelimiter() {
        while (isNotClosingChar(nextChar) || isMultipleClosingChars()) {
            currentChar++
            readChar()
        }
    }

    private void extractOneCharDelimiter() {
        bufferedDelimiter << delimiterLine[currentChar]
    }

    private int getNextChar() {
        currentChar + 1
    }

    private boolean isNotClosingChar(int index) {
        !isClosingChar(index)
    }

    private boolean isClosingChar(int index) {
        delimiterLine[index].equals(']')
    }

    private boolean isMultipleClosingChars() {
        isNotCharNearEnd() && isClosingChar(nextChar + 1)
    }

    private boolean isNotCharNearEnd() {
        currentChar < delimiterLine.length() - 2
    }

    private void readChar() {
        if (anyCharactersLeft()) {
            bufferedDelimiter << delimiterLine[currentChar]
        }
    }

    private void resetBuffer() {
        bufferedDelimiter = new StringBuffer()
        currentChar++
    }

    @Override
    void remove() {
        throw new UnsupportedOperationException()
    }
}
