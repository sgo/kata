package kata


class SimpleVelocity implements Velocity {
    private int multiplier

    @Override
    int getMultiplier() {
        return multiplier
    }

    static SimpleVelocity multiplier(int multiplier) {
        new SimpleVelocity(multiplier:multiplier)
    }
}
