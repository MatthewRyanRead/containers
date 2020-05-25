package tech.read_only.containers;

import org.junit.Test;

interface WritableContainerTest {
    @Test
    void testAdd();

    @Test
    void testResize();

    @Test
    void testIteratorRemove();

    @Test
    void testClear();

    @Test
    void testRemove();
}
