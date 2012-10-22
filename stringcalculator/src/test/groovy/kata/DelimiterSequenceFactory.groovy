package kata


class DelimiterSequenceFactory implements Iterable<String> {
    private String delimiterLine

    DelimiterSequenceFactory(String delimiterLine) {
        this.delimiterLine = delimiterLine
    }

    @Override
    Iterator<String> iterator() {
        new DelimiterSequence(delimiterLine)
    }
}
