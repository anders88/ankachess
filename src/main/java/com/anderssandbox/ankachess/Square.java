package com.anderssandbox.ankachess;

import java.util.Optional;

public class Square {
    private String square;

    private Square(String square) {
        this.square = square;
    }

    public static Square fromString(String squareTxt) {
        if (illegal(squareTxt)) {
            throw new IllegalArgumentException("Illegal square " + squareTxt);
        }
        return new Square(squareTxt);
    }

    private static boolean illegal(String squareTxt) {
        return squareTxt == null || squareTxt.length() != 2 || squareTxt.charAt(0) < 'a' || squareTxt.charAt(0) > 'h' || squareTxt.charAt(1) < '1' || squareTxt.charAt(1) > '8';
    }

    public Optional<Square> negighbour(Direction direction) {
        switch (direction) {
            case UP:
                return op(x(),add(y(),1));
            case UP_RIGHT:
                return op(add(x(), 1),add(y(),1));
            case RIGHT:
                return op(add(x(),1),y());
            case DOWN_RIGHT:
                return op(add(x(),1),add(y(), -1));
            case DOWN:
                return op(x(),add(y(),-1));
            case DOWN_LEFT:
                return op(add(x(),-1),add(y(),-1));
            case LEFT:
                return op(add(x(),-1),y());
            case UP_LEFT:
                return op(add(x(),-1),add(y(),1));
        }
        throw new IllegalArgumentException("Wrong direction" + direction);
    }

    private static Optional<Square> op(char x, char y) {
        String sqTxt = "" + x + y;
        Optional<Square> res = !illegal(sqTxt) ?
                Optional.of(new Square(sqTxt)) :
                Optional.empty();
        return res;
    }

    private char x() {
        return square.charAt(0);
    }

    private char y() {
        return square.charAt(1);
    }

    private char add(char org,int diff) {
        return (char) (org + diff);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Square square1 = (Square) o;

        if (!square.equals(square1.square)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return square.hashCode();
    }

    @Override
    public String toString() {
        return "Square{" +
                "square='" + square + '\'' +
                '}';
    }
}
