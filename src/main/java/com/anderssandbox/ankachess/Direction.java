package com.anderssandbox.ankachess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public enum Direction {
    UP,UP_RIGHT,RIGHT,DOWN_RIGHT,DOWN,DOWN_LEFT,LEFT,UP_LEFT;

    public static Stream<Direction> allDirections() {
        List<Direction> all = new ArrayList<>();
        for (Direction dir : values()) {
            all.add(dir);
        }
        return all.stream();
    }
}
