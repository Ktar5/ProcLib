package com.ktar5.proclib.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Direction {
    N(0, 1),
    NE(1, 1),
    E(1, 0),
    SE(1, -1),
    S(0, -1),
    SW(-1, -1),
    W(-1, 0),
    NW(-1, 1);

    public final int x, y;

    public static final Direction[]
            ALL = {N, NE, E, SE, S, SW, W, NW},
            CARDINAL = {N, E, S, W},
            DIAGNOL = {NE, SE, SW, NW};

    private static final Direction[][] rotations = {
            {NW, N, NE},
            {W, null, E},
            {SW, S, SE}
    };

    public Direction left45() {
        int ordinal = this.ordinal() - 1;
        if(ordinal == -1){
            ordinal = values().length - 1;
        }
        return values()[ordinal];
    }

    public Direction right45() {
        int ordinal = this.ordinal() + 1;
        if(ordinal == values().length - 1){
            ordinal = 0;
        }
        return values()[ordinal];

    }

    public Direction left90() {
        return left45().left45();
    }

    public Direction right90() {
        return right45().right45();
    }

}
