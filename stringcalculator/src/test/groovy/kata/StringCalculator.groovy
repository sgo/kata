package kata

class StringCalculator {
    int run(String input) {
        return input.empty ? 0 : sum(input);
    }

    private int sum(String input) {
        new NumberParserFactory(input).inject(0) {sum, next -> sum + next}
    }
}
