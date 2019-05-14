import org.junit.Test;

import javax.annotation.Nullable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReadableHashSetTest<T extends ReadableHashSet<Integer>> extends IntegerContainerTestBase<T> {
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
    protected T makeContainer(final Integer... elems) {
        //noinspection unchecked
        return (T) new ReadableHashSet<>(elems);
    }

    @Test
    public void testCompare() {
        final T set1 = this.makeContainer(1, 2, 3);
        final T set2 = this.makeContainer(3, 2, 3, 1);

        assertEquals(set1.size(), set2.size());

        assertTrue(set1.containsAll(set2));
        assertTrue(set2.containsAll(set1));

        assertEquals(set1, set2);
        assertEquals(set1.hashCode(), set2.hashCode());
    }
}
