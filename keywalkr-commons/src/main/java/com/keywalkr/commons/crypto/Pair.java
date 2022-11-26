package com.keywalkr.commons.crypto;

import java.util.Objects;

public class Pair<A, B> {
    private final A left;
    private final B right;

    public Pair(final A left, final B right) {
        this.left = left;
        this.right = right;
    }

    public Pair<A, B> withLeft(final A left) {
        return this.left == left ? this : new Pair<>(left, this.right);
    }

    public Pair<A, B> withRight(final B right) {
        return this.right == right ? this : new Pair<>(this.left, right);
    }

    public A getLeft() {
        return this.left;
    }

    public B getRight() {
        return this.right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return left.equals(pair.left) && right.equals(pair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Pair;
    }
}
