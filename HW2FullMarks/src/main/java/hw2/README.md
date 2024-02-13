# Discussion

The `Roster` class uses `IndexedList` to store a list of students. The
`Roster.find` implements the binary search algorithm. Which
implementation of the `IndexedList` should be used to implement the
`Roster` class? (It could be one or more of `ArrayIndexedList`,
`LinkedIndexList`, `SparseIndexedList`). And why?
   
--------------- Write your answers below this line ----------------

I would use ArrayIndexedList because binary search needs to divide the
data structure in half, and linked lists don't allow us to easily 
access any elements that are not at the beginning (or at the end,
depending on its implementation), while it is a constant operation
to access an element by index in an array.