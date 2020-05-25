package tech.read_only.containers;

import javax.annotation.Nullable;

/**
 * Interface denoting a class that traverses elements from some underlying source in some fixed
 * encounter order.
 *
 * @param <E> The type of elements being traversed
 */
public interface ReadableIterator<E> {
    /**
     * @return {@code true} if this iterator has elements left to traverse; {@code false} otherwise
     */
    boolean hasNext();

    /**
     * @return The next element in this iterator's traversal
     * @throws IllegalStateException if this iterator has no elements left to traverse
     */
    @Nullable
    E next() throws IllegalStateException;
}
