package tech.read_only.containers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

class WritableHashSetTest<T extends WritableHashSet<Integer>> extends ReadableHashSetTest<T>
        implements WritableContainerTest {
    @Override
    protected T makeContainer() {
        //noinspection unchecked
        return (T) new WritableHashSet<Integer>();
    }

    @Override
    protected T makeContainer(@Nullable final Integer elem) {
        //noinspection unchecked
        return (T) new WritableHashSet<>(elem);
    }

    @Override
    protected T makeContainer(final Object... elems) {
        //noinspection unchecked
        return (T) (WritableHashSet<?>) new WritableHashSet<>(elems);
    }

    @Override
    protected T makeContainer(final Container<Integer> other) {
        //noinspection unchecked
        return (T) new WritableHashSet<>(other);
    }

    @Test
    public void testAdd() {
        final T set1 = this.makeContainer();

        assertTrue(set1.add(1));
        assertFalse(set1.isEmpty());
        assertEquals(1, set1.size());
        assertTrue(set1.contains(1));

        assertFalse(set1.add(1));
        assertEquals(1, set1.size());

        final T set2 = this.makeContainer(1);

        assertEquals(set1, set2);
        assertEquals(set1.hashCode(), set2.hashCode());

        assertTrue(set1.add(null));
        assertTrue(set1.contains(null));
        assertEquals(2, set1.size());
        assertFalse(set1.add(null));
    }

    @Test
    public void testResize() {
        final T set1 = this.makeContainer(1);
        final int initialHashCode = set1.hashCode();

        set1.add(2);

        assertEquals(2, set1.size());
        assertTrue(set1.contains(1));
        assertTrue(set1.contains(2));
        assertNotEquals(initialHashCode, set1.hashCode());

        final T set2 = this.makeContainer(1, 2);

        assertEquals(set1, set2);
        assertEquals(set1.hashCode(), set2.hashCode());

        final T set3 = this.makeContainer();

        set3.add(1);

        assertEquals(1, set3.size());
        assertTrue(set1.contains(1));
    }

    @Test
    public void testIteratorRemove() {
        final T set1 = this.makeContainer(1, 2, 3);
        final WritableIterator<Integer> iter = set1.iterator();

        try {
            iter.remove();
            fail("Expected IllegalStateException");
            return;
        } catch (final IllegalStateException e) {
            iter.next();
        }

        iter.next();
        iter.remove();

        assertTrue(iter.hasNext());
        assertEquals(Integer.valueOf(3), iter.next());
        assertFalse(iter.hasNext());

        final T set2 = this.makeContainer(1, 3);

        assertEquals(set2, set1);
        assertEquals(set1.hashCode(), set2.hashCode());
    }

    @Test
    @Override
    public void testClear() {
        final T set1 = this.makeContainer(1, 2, 3);

        set1.clear();

        assertTrue(set1.isEmpty());
        assertFalse(set1.contains(1));
        assertFalse(set1.contains(2));
        assertFalse(set1.contains(3));

        final T set2 = this.makeContainer();

        assertEquals(set2, set1);
        assertEquals(set2.hashCode(), set1.hashCode());
    }

    @Test
    @Override
    public void testRemove() {
        final T set1 = this.makeContainer(1, 2, 3);

        assertTrue(set1.remove(2));

        assertEquals(2, set1.size());
        assertFalse(set1.contains(2));
        assertFalse(set1.remove(2));
        assertFalse(set1.remove(null));

        final T set2 = this.makeContainer(1, 3);

        assertEquals(set2, set1);
        assertEquals(set2.hashCode(), set1.hashCode());

        final T set3 = this.makeContainer((Integer) null);
        assertTrue(set3.remove(null));
        assertTrue(set3.isEmpty());

        final T set4 = this.makeContainer();
        set4.add(1);
        assertFalse(set4.remove(3));
        assertTrue(set4.remove(1));
        assertTrue(set4.isEmpty());
    }
}
