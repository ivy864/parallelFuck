# ParallelFuck

A programming language derivative of [BrainFuck](https://en.wikipedia.org/wiki/Brainfuck) with the addition of shared state and parallelization

My interpreter _should_ be fully functional with the exception of input while in a thread. idk whats up with that but I'm gonna blame all my problems on java.

## New Operators

ParallelFuck adds 7 new operators to brainfuck

|Operator|Description|
|:-|:-|
|(|start new thread|
|)|close thread|
|_|join on last thread created|
|&|send value at pointer to transfer cell| 
|^|put value in transfer cell at position n of universal tape|
|%|put value at position n of the universal tape into transfer cell|
|*|replace value at pointer with value in transfer cell|

## Shared Memory 

Memory in ParallelFuck is shared through a universal tape. The universal tape 
cannot be accessed directly. Values are sent to and retrieved from the 
universal tape through the 'transfer cell'. Values can be placed in the transfer 
cell, and then sent to a cell in the universal tape. To access values in the 
universal tape, the value must be first staged in the transfer cell, and then
placed into the current active cell of the local tape.

When accessing the universal tape, the value of the active cell (the cell in the 
local tape which the pointer is pointing to) will be used as the index.

### An example program using the universal tape

```
++
&       put '2' into transfer cell. the value of the active cell is now 0
^       put '2' at position 0 of universal tape. the value in the transfer cell is now null 
+++ 

&       put '3' into transfer cell. the value of the active cell is now 0

+^      set value of active cell to 1. put '3' at position 1 of universal tape
-       set value at pointer to 0
%       put value at position 0 of universal tape (2) into transfer cell
*       replace value of active cell with value of transfer cell. transfer cell is now null
.       outputs '2'
```

## Parallelization

Code placed inside of parentheses will run inside a new thread. Each thread has 
its own local tape and transfer cell. The '_' operator waits until the last thread 
created has terminated (much like java's join method (I'm writing this shit in java, 
so its literally gonna be java's join method))

### Thread scope

When a new thread is spawned, the thread is scoped to the thread which spawned it.

In the following example, one thread is spawned from the main thread, and a second is 
spawned from the new thread. The main thread then tries to join twice. This would result
in an error. 

```
(
  (
    ...
  )
  ...
)
_
_       throws error, join on non-existant thread
```

One can spawn and join on two threads like so:

```
(
...
)
(
...
)
_
_
```

Another method:

```
(
  (
    ...
  )
  _
)
_
```

### An example of parallelization in ParallelFuck

The following program spawns 2 new threads to concurrently get the values for all 3 
characters of output. The additional threads transfer these values back to main, where 
they are then outputted


```
(                   start new thread
  +++++++++
  [                 set value to 72
    >++++++++
    <-
  ]
  >&
  ^                 put value in position 0 of universal tape
)
(                   start second new thread
  ++++++++++
  [
    >++++++++++
    <-
  ]
  >+++++            set value to 105
  &
  +^                put 105 in position 1 of universal tape
)

+++++
[
  >++++++
  <-
]
>+++                set value to 33

_                   wait for second thread to finish
_                   wait for first thread to finish

>%*.                get value at position 0 of universal tape and print it
>+
%*.                 get value at position 1 of universal tape and print it
<<.                 go back 2 cells and print
```

## Compatibility with BrainFuck

All BrainFuck programs should be fully compatible with ParallelFuck, although comments
which use include characters used for the new operators will cause problems.
