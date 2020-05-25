package tech.read_only.containers;

import javax.annotation.Nullable;

/** A strictly ordered {@link Container}. Each element has a corresponding index for ordering. */
public interface ReadableList<E> extends Container<E> {
    /**
     * Retrieves the element at the given index.
     *
     * @param index The index for lookup
     * @return The element at {@code index}
     * @throws IllegalArgumentException if the index is out of bounds (less than 0 or greater than
     *     or equal to {@link #size})
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

    /**
     * Consistent with {@link Container#equals}, but also requires that both lists have the same
     * order of elements. Subtypes may set further conditions.
     *
     * @param other The other container to compare
     * @return {@code true} if this list equals {@code other}; {@code false} otherwise
     */
    @Override
    boolean equals(@Nullable final Object other);

    /**
     * Like {@link #equals}, but does not compare the class. Subtypes may <b>not</b> set further
     * conditions. This allows very consistent comparison of lists on the basis of "a container with
     * a fixed order".
     *
     * @param other The list to compare
     * @return {@code true} if the two lists contain equal elements in the same order; {@code false}
     *     otherwise
     */
    default boolean listEquals(@Nullable final ReadableList<?> other) {
        if (other == null) return false;
        if (this.size() != other.size()) return false;

        final ReadableIterator<E> thisIter = this.iterator();
        final ReadableIterator<?> otherIter = other.iterator();

        while (thisIter.hasNext() && otherIter.hasNext()) {
            final E thisElem = thisIter.next();
            final Object otherElem = otherIter.next();

            if (thisElem == null) {
                if (otherElem != null) return false;
            } else {
                if (!thisElem.equals(otherElem)) return false;
            }
        }

        return true;
    }
}
