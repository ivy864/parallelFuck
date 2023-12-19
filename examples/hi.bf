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

