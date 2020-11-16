package tech.read_only.containers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

@SuppressWarnings("unchecked")
class WritableArrayListTest<T extends WritableArrayList<Integer>> extends ReadableArrayListTest<T>
        implements WritableContainerTest {
    @Override
    protected T makeContainer() {
        return (T) new WritableArrayList<Integer>();
    }

    @Override
    protected T makeContainer(@Nullable final Integer elem) {
        return (T) new WritableArrayList<>(elem);
    }

    @Override
    protected T makeContainer(final Object... elems) {
        return (T) (WritableArrayList<?>) new WritableArrayList<>(elems);
    }

    @Override
    protected T makeContainer(final Container<Integer> other) {
        return (T) new WritableArrayList<>(other);
    }

    @Test
    @Override
    public void testAdd() {
        final T list1 = this.makeContainer();
        list1.add(1);

        assertFalse(list1.isEmpty());
        assertEquals(1, list1.size());
        assertTrue(list1.contains(1));

        list1.add(1);

        assertEquals(2, list1.size());

        final T list2 = this.makeContainer(1, 1);

        assertEquals(list2, list1);
        assertEquals(list2.hashCode(), list1.hashCode());
    }

    @Test
    @Override
    public void testResize() {
        final T list1 = this.makeContainer(2);
        final int initialHashCode = list1.hashCode();

        list1.add(1);

        assertEquals(2, list1.size());
        assertTrue(list1.contains(2));
        assertTrue(list1.contains(1));
        assertNotEquals(initialHashCode, list1.hashCode());

        final T list2 = this.makeContainer(2, 1);

        assertEquals(list2, list1);
        assertEquals(list2.hashCode(), list1.hashCode());
    }

    @Test
    @Override
    public void testIteratorRemove() {
        final T list1 = this.makeContainer(1, 2, 3);
        final WritableIterator<Integer> iter = list1.iterator();

        iter.next();
        iter.next();
        iter.remove();

        assertTrue(iter.hasNext());
        assertEquals(Integer.valueOf(3), iter.next());
        assertFalse(iter.hasNext());

        final T list2 = this.makeContainer(1, 3);

        assertEquals(list2, list1);
        assertEquals(list2.hashCode(), list1.hashCode());
    }

    @Test
    @Override
    public void testClear() {
        final T list1 = this.makeContainer(1, 2, 3);

        list1.clear();

        assertTrue(list1.isEmpty());
        assertFalse(list1.contains(1));
        assertFalse(list1.contains(2));
        assertFalse(list1.contains(3));

        final T list2 = this.makeContainer();

        assertEquals(list2, list1);
        assertEquals(list2.hashCode(), list1.hashCode());
    }

    @Test
    void testSet() {
        final T list1 = this.makeContainer(1, 2, 3);

        list1.set(0, 4);
        list1.set(1, 5);
        list1.set(2, 6);

        final T list2 = this.makeContainer(4, 5, 6);

        assertEquals(list2, list1);
        assertEquals(list2.hashCode(), list1.hashCode());
    }

    @Test
    void testRemoveAt() {
        final T list1 = this.makeContainer(1, 2, 3);

        final Integer removed = list1.removeAt(1);

        assertEquals(Integer.valueOf(2), removed);
        assertEquals(2, list1.size());
        assertFalse(list1.contains(2));

        final T list2 = this.makeContainer(1, 3);

        assertEquals(list2, list1);
        assertEquals(list2.hashCode(), list1.hashCode());
    }

    @Test
    @Override
    public void testRemove() {
        final T list1 = this.makeContainer(1, 2, 3);

        assertTrue(list1.remove(2));

        assertEquals(2, list1.size());
        assertFalse(list1.contains(2));
        assertFalse(list1.remove(2));

        final T list2 = this.makeContainer(1, 3);

        assertEquals(list2, list1);
        assertEquals(list2.hashCode(), list1.hashCode());
    }
}
