package tech.read_only.containers;

import java.util.stream.IntStream;

import javax.annotation.Nullable;

/**
 * Basic implementations of some {@link ReadableList} functionality that cannot be contained in an
 * interface.
 */
public abstract class AbstractReadableList<E> implements ReadableList<E> {
    /** A basic implementation of {@link ReadableList#equals}. */
    @Override
    public boolean equals(@Nullable final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        return this.listEquals((ReadableList<?>) o);
    }

    @Override
    public int hashCode() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .map(e -> e == null ? 0 : e.hashCode())
                .reduce(1, (hc1, hc2) -> 31 * hc1 + hc2);
    }

    @Override
    public ReadableIterator<E> iterator() {
        return new ReadableListIterator();
    }

    /** A basic {@link ReadableIterator} implementation. */
    protected class ReadableListIterator implements ReadableIterator<E> {
        protected int currIndex = 0;

        @Override
        public boolean hasNext() {
            return this.currIndex < AbstractReadableList.this.size();
        }

        @Nullable
        @Override
        public E next() throws IllegalStateException {
            if (!this.hasNext()) throw new IllegalStateException("No elements remaining");

            return AbstractReadableList.this.get(this.currIndex++);
        }
    }
}
