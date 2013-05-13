package pony.independent

import spock.lang.Specification

class PonyIndepentSpec extends Specification {
    def "test"() {
        expect:
        canInvite(input) == expected

        where:
        input           | expected
        "0 0"           | 0
        "1 0"           | 1
        "2 1\n0 1"      | 1
        "3 1\n0 1"      | 2
        "3 1\n1 2"      | 2
//        "3 2\n0 1\n0 2" | 2
        "4 1\n0 1" | 3
    }

    /**
     * + 0 1 2  + 0 1 2
     * 0   x x  0 0 1 1 2
     * 1 x      1 1 0 0 1
     * 2 x      2 1 0 0 1
     *                + -
     *                  2
     *
     * [[0, 0],
     *
     *  [0, 0]]
     */
    // every pony w/o a neighbour gets invited
    //
    // a pony with just one neighbour can be invitied, if you dismiss his neighbour
    def canInvite(String input) {
        def lines = input.readLines().collect { it.split(' ')*.toInteger() }
        def numberOfPonies = lines[0][0]
        def haters = lines[0][1]
        if(numberOfPonies == 0)
            return 0
        if(numberOfPonies == 1)
            return 1
        def checkboard = (1..numberOfPonies).collect {
            (1..numberOfPonies).collect {0}
        }
        lines.subList(1, lines.size()).each {
            checkboard[it[0]][it[1]] = 1
            checkboard[it[1]][it[0]] = 1
        }
        println checkboard
        def sums = checkboard.collect { numberOfPonies - it.sum() }
        int highestIndex = sums.findIndexOf {it == sums.max()}
        checkboard.remove(highestIndex)
        println checkboard

        // Verterx(x, y)   -> container.remove(1)
        // Container (like matrix, but no constraints)  wand we put an Ojbect of Type Vertex inside.
        // Vertex has x and y, x is rownumber, y is column number.
        // container of size 2 has 4 vertizes.
        // if we remove in a container of 2 the second row/column, we remove each vertex with row/col 1

        def container = new Container(numberOfPonies)
        lines.subList(1, lines.size()).each {
            container.add(new Vertex(row: it[0], col: it[1]))
        }
        container.remove(1)
        println container

        numberOfPonies - haters
    }

    /**
     *
       0 1 2 3 4 5 6 7 8 9
     0 0 0 0 0 0 0 1 1 0 0
     1 0 0 0 0 0 1 0 0 1 0
     2 0 0 0 0 0 1 0 0 0 0
     3 0 0 0 0 1 0 0 0 0 1
     4 0 0 0 1 0 0 0 0 0 0
     5 0 1 1 0 0 0 0 0 0 0
     6 1 0 0 0 0 0 0 0 0 0
     7 1 0 0 0 0 0 0 0 0 1
     8 0 1 0 0 0 0 0 0 0 1
     9 0 0 0 1 0 0 0 1 1 0
     */

    class Container {

        List<Vertex> vertizes

        private int size

        Container(int size) {
            this.size = size ** 2
            vertizes = new ArrayList(this.size)
        }

        void add(Vertex v) {
            vertizes.add(v)
            vertizes.add(new Vertex(row: v.col, col:v.row))
        }

        void remove(int rowCol) {
            vertizes.removeAll { it.col == rowCol || it.row == rowCol }
            size--
        }


        String toString() {
            println "["
            for(int i = 0; i < size; i ++) {
                for (int j = 0; j < size; j ++) {
                    print vertizes.find { it.row == i && it.col== j} ? " x " : " _ "
                }
                print "\n"
            }
            println "]"
        }
    }


    class Vertex {
        int row
        int col

        String toString() {
            "[$row, $col]"
        }
    }

}
