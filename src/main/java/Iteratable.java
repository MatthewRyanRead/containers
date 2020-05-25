/**
 * Interface denoting a class with data that can be traversed with an iterator.
 *
 * @param <T> The type of {@link ReadableIterator<E>}
 */
public interface Iteratable<E, T extends ReadableIterator<E>> {
    /** @return A {@link ReadableIterator} over this {@code Iteratable} */
    T iterator();
}
