import javax.annotation.Nullable;

/**
 * Basic implementations of some {@link ReadableList} functionality that cannot be contained in an
 * interface.
 */
public abstract class AbstractReadableList<E> implements ReadableList<E> {
    /** A basic implementation of {@link ReadableList#equals}. */
    @Override
    public boolean equals(@Nullable final Object other) {
        if (other == null) return false;
        if (this.getClass() != other.getClass()) return false;

        return this.listEquals((ReadableList<?>) other);
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
            if (!this.hasNext()) {
                throw new IllegalStateException("No elements remaining");
            }

            return AbstractReadableList.this.get(this.currIndex++);
        }
    }
}
