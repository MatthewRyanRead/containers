- Update `IntegerContainerTestBase` to be generic (contained type)
- Update lists to cache and invalidate their hash codes
- MORE TESTS
    - Index tests for lists (especially `removeAt()`)
    - Other untested methods
    - etc.
- Update iterators to deal with concurrent modifications (?)
- Make concurrency-safe implementations
- Helper method for `WritableHashSet` to alter size and the cached hashcode at
  the same time