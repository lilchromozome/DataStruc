# Discussion

The `Roster` class uses `IndexedList` to store a list of students. The
`Roster.find` implements the binary search algorithm. Which
implementation of the `IndexedList` should be used to implement the
`Roster` class? (It could be one or more of `ArrayIndexedList`,
`LinkedIndexList`, `SparseIndexedList`). And why?
   
--------------- Write your answers below this line ----------------

It should use ArrayIndexedList since arrays have random access which
is crucial for binary search since it can access any and more
importantly the middle element directly and in constant time. 
LinkedIndexList and SparseIndexedList both use Linked Lists, which 
doesn't have random access and would make the binary search in find
less efficient
