import javax.annotation.Nullable;

/**
 * A container for elements, supporting iteration. Implementing classes need not necessarily be
 * immutable; this interface only guarantees containment.
 *
 * @param <E> The type of elements contained
 */
public interface Container<E> extends Iteratable<E, ReadableIterator<E>> {
    /**
     * Whether the element is contained.
     *
     * @param e The element to check
     * @return {@code true} if this container holds {@code e}; {@code false} otherwise
     */
    boolean contains(@Nullable final Object e);

    /**
     * Whether all elements of the other container are contained by this container. Does not check
     * the converse.
     *
     * @param other The other container
     * @return {@code true} if all of {@code other}'s elements are contained; {@code false}
     *     otherwise
     */
    default boolean containsAll(final Container<?> other) {
        for (final ReadableIterator<?> iter = other.iterator(); iter.hasNext(); ) {
            if (!this.contains(iter.next())) {
                return false;
            }
        }

        return true;
    }

    /**
     * The size of this container, in number of elements.
     *
     * @return The number of contained elements
     */
    int size();

    /**
     * Whether this container is empty.
     *
     * @return {@code true} when {@link #size()} is 0; {@code false} otherwise
     */
    default boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * @return An array containing the same elements as this container, in the same encounter order
     *     as {@link #iterator()}.
     */
    default E[] toArray() {
        final Object[] array = new Object[this.size()];

        int i = 0;
        for (final ReadableIterator<?> iter = this.iterator(); iter.hasNext(); ) {
            array[i++] = iter.next();
        }

        //noinspection unchecked
        return (E[]) array;
    }

    /**
     * Whether this container is equal to another. Must be false if {@link #containsAll} is false,
     * if {@link #size()} is not equal, or if the containers are not of the same class. Subtypes may
     * set further conditions.
     *
     * <p>Note that implementations do not need to explicitly check {@link #containsAll}; they must
     * simply be consistent.
     *
     * @param other The other container to compare
     * @return {@code true} if this container equals {@code other}; {@code false} otherwise
     */
    @Override
    boolean equals(@Nullable final Object other);
}
