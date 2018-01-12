package com.ktar5.proclib.util.functional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Tuple<L, R> {
    L left;
    R right;
}
