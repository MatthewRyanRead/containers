# Containers

An exploration of an alternative inheritance setup for collections in Java.

---

Java's `Collection` interface is a mess of optional exceptions and operations.  It is an extremely poor software
contract; it is not specific or binding.  It is not reliable &mdash; you can't call `add()` and expect it to work unless
you know, _at compile time_, the exact _runtime_ type of the `Collection`.

One of the main issues stems from treating immutability as something that can be bolted on after the fact.  In truth,
write operations (mutations) should be built on top of read operations, not the other way around.

`Container` and its derivatives are an example of what `Collection` and its derivatives could have been with a focus on
consistent and dependable contracts.

Note that the concrete implementations are for example only, and are not necessarily performant.
