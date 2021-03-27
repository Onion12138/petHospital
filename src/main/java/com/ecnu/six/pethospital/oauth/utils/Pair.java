package com.ecnu.six.pethospital.oauth.utils;

import java.util.Objects;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc pair工具包
 * @since 1.0
 */
public class Pair<A, B> {
    private final A left;

    private final B right;

    private Pair(A left, B right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Pair<?,?> &&
                        Objects.equals(left, ((Pair<?,?>)other).getLeft()) &&
                        Objects.equals(right, ((Pair<?,?>)other).getRight());
    }

    @Override
    public String toString() {
        return "Pair{" +
                left + "," + right +
                '}';
    }

    public A getLeft() {
        return left;
    }

    public B getRight() {
        return right;
    }

    public static <A,B> Pair<A,B> of(A a, B b) {
        return new Pair<>(a,b);
    }
}
