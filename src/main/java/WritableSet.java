import javax.annotation.Nullable;

/**
 * A {@link ReadableSet} that also supports the addition and removal of elements.
 *
 * @param <E>
 */
public interface WritableSet<E> extends WritableContainer<E>, ReadableSet<E> {
    /**
     * Consistent with {@link WritableContainer#add(Object)}), but also returns the opposite of an
     * immediately preceding call to {@link #contains(Object)} with the same element.
     */
    @Override
    boolean add(@Nullable final E elem);
}
