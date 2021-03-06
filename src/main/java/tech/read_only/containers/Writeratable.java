package tech.read_only.containers;

/**
 * Interface denoting an {@link Iteratable} class that may also have data removed by the iterator.
 *
 * @param <T> The type of {@link WritableIterator<E>}
 */
public interface Writeratable<E, T extends WritableIterator<E>> {
    /** @return A {@link WritableIterator} over this {@code Writeratable} */
    @SuppressWarnings("unused") // hidden in implementations by {@link Iteratable::iterator()}
    T iterator();
}
