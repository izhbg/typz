package com.izhbg.typz.base.util;

public class RandomCode {
    public static int generate() {
        return (int) (((Math.random() * 9) + 1) * 100000);
    }
}
