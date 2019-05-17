import javax.annotation.Nullable;

/**
 * A {@link Container} that also supports the addition and removal of elements.
 *
 * @param <E> The type of elements contained
 */
public interface WritableContainer<E> extends Writeratable<E, WritableIterator<E>>, Container<E> {
    /**
     * Request to add an element to this container. When the call returns, the container holds the element.
     *
     * @param e The element to add
     * @return {@code true} if the element was added; {@code false} if the element was already contained and was not
     *         added
     */
    boolean add(@Nullable final E e);

    /**
     * Request to remove an element from the container. Containers that support multiple copies of elements will only
     * remove the first copy encountered.
     *
     * @param e The element to remove
     * @return {@code true} if the element was present (and thus removed); {@code false} otherwise
     */
    boolean remove(@Nullable final E e);

    /**
     * Removes all elements from the container.
     */
    void clear();
}
