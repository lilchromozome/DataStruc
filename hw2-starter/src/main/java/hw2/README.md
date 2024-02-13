# Discussion

The `Roster` class uses `IndexedList` to store a list of students. The
`Roster.find` implements the binary search algorithm. Which
implementation of the `IndexedList` should be used to implement the
`Roster` class? (It could be one or more of `ArrayIndexedList`,
`LinkedIndexList`, `SparseIndexedList`). And why?
   
--------------- Write your answers below this line ----------------
As a binary search algorithm isn't implementing any insertion/deletion operations,
the searching efficiency of each data structure more important.
Because of this, we know that an array is more efficient to search through than a linked list
(either version of a linkedIndexList). As a result, I would choose to use an ArrayIndexedList
to implemnent the Roster class.