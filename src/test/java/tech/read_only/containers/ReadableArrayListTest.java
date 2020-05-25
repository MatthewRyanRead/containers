package tech.read_only.containers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import javax.annotation.Nullable;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class ReadableArrayListTest<T extends ReadableArrayList<Integer>>
        extends ContainerTestBase<Integer, T> {
    private int currElement = 0;

    @Override
    protected T makeContainer() {
        return (T) new ReadableArrayList<Integer>();
    }

    @Override
    protected T makeContainer(@Nullable final Integer elem) {
        return (T) new ReadableArrayList<>(elem);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected T makeContainer(final Object... elems) {
        return (T) (ReadableArrayList<?>) new ReadableArrayList<>(elems);
    }

    @Override
    protected T makeContainer(final Container<Integer> other) {
        return (T) new ReadableArrayList<>(other);
    }

    @Override
    protected Integer generateElement() {
        return ++currElement;
    }

    @Test
    public void testCompare() {
        final T list1 = this.makeContainer(1, 2, 3);
        final T list2 = this.makeContainer(3, 2, 3, 1);

        assertNotEquals(list1.size(), list2.size());

        assertTrue(list1.containsAll(list2));
        assertTrue(list2.containsAll(list1));

        assertNotEquals(list1, list2);
        assertNotEquals(list1.hashCode(), list2.hashCode());

        final T list3 = this.makeContainer(4, 3, 2, 1);

        assertFalse(list1.containsAll(list3));
        assertFalse(list2.containsAll(list3));

        assertTrue(list3.containsAll(list1));
        assertTrue(list3.containsAll(list2));
    }

    @Test
    public void testOrder() {
        final T list1 = this.makeContainer(1, 2, 3);
        final T list2 = this.makeContainer(3, 2, 1);

        assertNotEquals(list1, list2);
        // not strictly guaranteed, but should be the case here
        assertNotEquals(list1.hashCode(), list2.hashCode());
    }
}
