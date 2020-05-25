package tech.read_only.containers;

import javax.annotation.Nullable;

/** A {@link ReadableSet} that also supports the addition and removal of elements. */
public interface WritableSet<E> extends WritableContainer<E>, ReadableSet<E> {
    /**
     * Consistent with {@link WritableContainer#add}), but also returns the opposite of an
     * immediately preceding call to {@link #contains} with the same element. In other words, it
     * will be {@code true} if the set does not contain the element, and {@link false} if it does.
     */
    @Override
    boolean add(@Nullable final E elem);
}
