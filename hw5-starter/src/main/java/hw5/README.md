# Discussion
In the README.md file, please give a list of the flaws you found in our 
implementation and list the unit tests (using the names you gave them) that 
uncovered each flaw. For each flaw you identify, please share an 
"educated guess" as to the mistake that led to the flaw.

## Flawed Deque

1. +  2. One flaw I found was that FlawedDeque doesn't throw an EmptyException error when 
trying to remove the front or back element of an empty Deque. This was proven by my removeFrontEmptyDeque() and removeBackEmptyDeque() 
unit tests. An educated guess for how this might have happened is that the developer of
FlawedDeque forgot to include an if statement checking to see if the Deque is empty before
implementing these operations. The developer never handled EmptyExceptions for these operations.

3. Another flaw: insertBack doesn't function properly in FlawedDeque.

When insertBack attempts to increase the capacity of FlawedDeque, 
the insertBack function call doesn't work as expected. Instead of increasing the capacity and then adding onto the rear of the 
data structure, the insertBack call only increases the capacity without inserting the element. This means that when 
insertBack needs to add additional capacity, it does so successfully but fails to insert the new element into the rear of the newly extended data structure.
I discovered this through all unit tests that incorporated insertBack (except for insertBack one time on an empty array). These unit tests were
removeBackMultipleElementsDeque, insertBackMultipleElementsDeque, removeFrontBackMultipleElementsDeque, insertFrontBackMultipleElementsDeque.

For example, in removeBackMultipleElementsDeque.

I try:
String a = "a";
String b = "b";
String c = "c";
deque.insertBack(a);
deque.insertFront(b);
deque.insertBack(c);

This however leads to an array of ["a", "b", null, null] as indicated in the Java Visualizer.
"b" and "a" are still the back and front respectively, but "c" doesn't get added and the length is subsequently off.
As a result, my tests checking whether c gets inserted as the new back fail and I fail to obtain the proper length.

A similar story is repeated in the other unit tests where the capacity is doubled, but the element never gets inserted.
This throws off the true back element and the length, causing those respective tests to fail.

This error might be caused by the developer neglecting to input the new back value after a capacity doubling operation.

4. The last error I found was that the back method fails to throw an EmptyException when 
called on an empty FlawedDeque. I found this through my testBackEmpty() unit test after it failed to throw an EmptyException and instead
threw a LengthException when I called back() on an empty data structure. As specified by the Deque interface, back() is supposed to throw an
EmptyException not a LengthException. The developer probably implemented LengthException handling instead of EmptyException
to see if the data structure was empty or not before retrieving the back value. They should have implemented EmptyException error handling instead.

## Hacking Linear Search
In TransposeArraySet.java, the behavior of remove doesn't work as expected (moveToFront is okay). Instead of the element simply
being removed from the array and all subsequent elements shifting left by one, the heuristics caused some unusual behavior.
For example, in my main method, I had inserted ["a", "b", "c", "d", "e"] in my array already.
When I tried to remove the "b" element, the operations resulted in ["e", "a", "c", "d"] 
instead of the expected ["a", "c", "d", "e"]. This is because the find method is overridden in TransposeArraySet.java.
Instead of the normal find, we have a new implementation of find that implements the Transpose Array Heuristic which swaps
the found element with its predecessor. As a result, this behavior is now explained. In the original array when using find("b"), 
the array mutates to ["b", "a", "c", "d", "e"]. Afterwards, the rest of the remove() implementation acts.

Looking through that implementation, we see the following: 

protected void remove(int index) {
    data[index] = data[numElements - 1];
    data[numElements - 1] = null;
    numElements--;
    }
when b is swapped with a, TransposeArraySet.java returns the new index of the swapped position (i.e. 0 instead of 1 when searching for "b").
Inputting 0 as the index in this method, we can see that the head of the array is changed to the last element of the array,
which in this case is "e". The last index is subsequently set as a null value and then the total number of elements decrements. 
From this, we can understand how the result came to be for the unexpected result of remove. For the MoveToFront heuristic, we observe that no detectable difference
in end behavior is reported. Rather, by looking at how moveToFront implements its remove operation, we can see that extra operations are taken by moving the selected node 
to the front of the set. Both of these behaviors (from TransposeArraySet and moveToFront) are explained by their respective implementations of the find method. 


## Profiling

In this experiment, my hypothesis is that my heuristics will outperform the other data structures 
when a small subset is more frequently searched than the rest of the elements, with increasing differences at larger dataset
sizes and number of searches. To test this, I evaluate a large dataset (10,000 elements) and do 10,000 searches on the last element (9,999).
I create a set with 10,000 elements in increasing order (ascending order) and repeatedly search for the last element (9,999).

1. 10,000 element dataset w/ 10,000 searches on the value 9,999

Benchmark                         Mode  Cnt    Score   Error  Units
JmhRuntimeTest.arraySet           avgt    2  119.224          ms/op
JmhRuntimeTest.linkedSet          avgt    2  437.210          ms/op
JmhRuntimeTest.moveToFront        avgt    2  148.549          ms/op
JmhRuntimeTest.transposeSequence  avgt    2   79.997          ms/op

As we see through this experiment, the moveToFront and transposeSequence heuristics improve the performance of this searching operation. This is to be expected 
by the assumptions of my original hypothesis. As arraySet is a 50% increase in time from tranposeSequence and linkedSet is almost 200 % increase in time than moveToFront for this experiment,
I can reasonably conclude that my hypothesis (if there is a sufficiently large dataset with a small subset of elements searched often then the heuristics will outperform the other implementations)
is true. From here, we have demonstrated a scenario where the heuristics improved the performance of these data structures. 

