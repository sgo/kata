package kata

class PrimeFactors {
    static def of(def n) {
        new PrimeFactorsCalculator(number: n).factors
    }

    private static class PrimeFactorsCalculator {
        def factors = []
        def factor = 1
        double number

        def getFactors() {
            while (notAllFactorsFound()) {
                nextGuess()
                while (divisible) {
                    addFactor()
                    divideByFactor()
                }
                if (onlySelfLeft) nextFactorIsSelf()
            }
            return factors
        }

        private boolean notAllFactorsFound() {
            number > 1
        }

        private void nextGuess() {
            factor++
        }

        private boolean isDivisible() {
            number % factor == 0
        }

        private void addFactor() {
            factors << factor
        }

        private void divideByFactor() {
            number /= factor
        }

        private boolean isOnlySelfLeft() {
            factor > Math.sqrt(number)
        }

        private void nextFactorIsSelf() {
            factor = number - 1
        }
    }
}
