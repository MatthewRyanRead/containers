package tech.read_only.containers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

class ReadableHashSetTest<T extends ReadableHashSet<Integer>>
        extends ContainerTestBase<Integer, T> {
    private int currElem = 0;

    @Override
    protected T makeContainer() {
        //noinspection unchecked
        return (T) new ReadableHashSet<Integer>();
    }

    @Override
    protected T makeContainer(@Nullable final Integer elem) {
        //noinspection unchecked
        return (T) new ReadableHashSet<>(elem);
    }

    @Override
    protected T makeContainer(final Object... elems) {
        //noinspection unchecked
        return (T) (ReadableHashSet<?>) new ReadableHashSet<>(elems);
    }

    @Override
    protected T makeContainer(final Container<Integer> other) {
        //noinspection unchecked
        return (T) new ReadableHashSet<>(other);
    }

    @Override
    protected Integer generateElement() {
        return ++currElem;
    }

    @Test
    void testCompare() {
        final T set1 = this.makeContainer(1, 2, 3);
        final T set2 = this.makeContainer(3, 2, 3, 1);

        assertEquals(set1.size(), set2.size());

        assertTrue(set1.containsAll(set2));
        assertTrue(set2.containsAll(set1));

        assertEquals(set1, set2);
        assertEquals(set1.hashCode(), set2.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("null, 1, 2", this.makeContainer(1, null, 2).toString());
        assertEquals("null", this.makeContainer((Integer) null).toString());
        assertEquals("1", this.makeContainer(1).toString());
    }
}
