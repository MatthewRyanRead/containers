import javax.annotation.Nullable;

/**
 * A strictly ordered {@link Container}. Each element has a corresponding index for ordering.
 *
 * @param <E> The type of elements contained
 */
public interface ReadableList<E> extends Container<E> {
    /**
     * Retrieves the element at the given index.
     *
     * @param index The index for lookup
     * @return The element at {@code index}
     * @throws IllegalArgumentException if the index is out of bounds (less than 0 or greater than or equal to
     *         {@link #size()})
     */
    @Nullable
    E get(final int index);

    /**
     * Finds the first index of the specified element, if any.
     *
     * @param e The element to locate
     * @return -1 if the element was not found; the index of the element otherwise
     */
    int indexOf(@Nullable final Object e);
}
