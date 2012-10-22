package kata


class NumberIterator implements Iterator<Integer> {
    int i

    NumberIterator(int i) {
        this.i = i
    }

    @Override
    boolean hasNext() {
        i > 0
    }

    @Override
    Integer next() {
        i--
    }

    @Override
    void remove() {

    }
}
