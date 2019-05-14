import org.junit.Test;

import javax.annotation.Nullable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ReadableArrayListTest<T extends ReadableArrayList<Integer>> extends IntegerContainerTestBase<T> {
    @Override
    protected T makeContainer() {
        //noinspection unchecked
        return (T) new ReadableArrayList<Integer>();
    }

    @Override
    protected T makeContainer(@Nullable final Integer elem) {
        //noinspection unchecked
        return (T) new ReadableArrayList<>(elem);
    }

    @Override
    protected T makeContainer(final Integer... elems) {
        //noinspection unchecked
        return (T) new ReadableArrayList<>(elems);
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
