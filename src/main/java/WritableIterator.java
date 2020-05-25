/**
 * Interface denoting a {@link ReadableIterator} class that can also remove elements from the
 * underlying source.
 */
public interface WritableIterator<E> extends ReadableIterator<E> {
    /**
     * Remove the last element returned by {@link #next()} from the underlying source.
     *
     * @throws IllegalStateException if {@link #next()} has not previously been called successfully.
     */
    void remove() throws IllegalStateException;
}
