# Discussion

The `Roster` class uses `IndexedList` to store a list of students. The
`Roster.find` implements the binary search algorithm. Which
implementation of the `IndexedList` should be used to implement the
`Roster` class? (It could be one or more of `ArrayIndexedList`,
`LinkedIndexList`, `SparseIndexedList`). And why?
   
--------------- Write your answers below this line ----------------
ArrayIndexedList should be used to implement the Roster class. Unlike 
LinkedIndexList and SparseIndexList, which do not allow for efficient 
index-based access to their elements, ArrayIndexedList allows the array
to access to its elements much faster at constant time (O(1)).