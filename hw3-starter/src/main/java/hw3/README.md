# Discussion

## PART I: MEASURED IndexedList

**Discuss from a design perspective whether iterating over a `MeasuredIndexedList`should 
affect the accesses and mutation counts. Note that for the purposes of this assignment we are NOT 
asking you to rewrite the `ArrayIndexedListIterator` to do so. However, if you wanted to include 
the `next()` and/or `hasNext()` methods in the statistics measured, can you inherit 
`ArrayIndexedListIterator` from `ArrayIndexedList` and override the relevant methods, or not? 
Explain.**

As a user is iterating over a 'MeasuredIndexedList' object, I believe that it should affect the access count of the list as they are actively engaging with it even if not directly using the get functions.
Likewise, the same logic should apply for any mutations made while using the iterator. If a user wants to change an element of 'MeasuredIndexedList' while using the iterator to iterate over the array
then the mutation counter should be updated (i.e. if the user wants to modify the list in any way through the iterator, then the mutation counter should change). However, the mutation counter shouldn't
increase just from the action of accessing each element via the iterator. 

Hypothetically, if one wanted to you, one could inherit
'ArrayIndexedListIterator' from the "ArrayIndexedList" class and override the relevant methods to add statistics tracking. This would be only be possible if the visibility of the inner class (arrayindexediterator) was changed 
from private to something like protected. With private visibility, it wouldn't be possible. However, this is also invokes a question of if the 
designer of the program wants to keep track of put/get usages or keep track of any access/mutation method on MeasuredIndexedList. It might not be a strong design decision to do this though.




## PART II: EXPERIMENTS

**Explain the mistake in the setup/implementation of the experiment which resulted in a discrepancy 
between the results and what is expected from each sorting algorithm.**

Examining the data and the code there are mistakes which result in deviations from the expected results. For example, 
one mistake is that the descending data is not really in descending order (i.e. there are elements which aren't in descending order). Around every 10 values, there is a value
that isn't in order. This is compounded by the fact that the data stored in each file given (ascending.data, descending.data, and random.data) 
are read and sorted as strings instead of integers. 
This helps to explain some of the confounding results. For example "10" is less than "2" using a string comparison with .compareTo but 10 is not less than 2 numerically.
For example, in the ascending data (for high numbers), there should be 0 mutations in any of the sorts as the data is already sorted. However, this is not the case for large sample sizes of the data
as the strings are compared lexicographically instead of integers being compared based on their values. This bug appears for the rest of the sorting algorithms in the ascending case 
as they all have a nonzero amount of mutations which shouldn't be the case. There should only be a nonzero amount of accesses recorded for each algorithm.
Additionally, in the ascending case (which is already sorted), null sort appears not to be sorted at large numbers of data (i tried greater than 500). This isn't right.
Furthermore, the descending case is behaving better than expected as it should be the worst case scenario for all sorting algorithms. This discrepancy was found by examining the contents of all the data files and through the tests below.


I also tested each sorting algorithm across a range of values (starting from 0, stopping at 9999, stepping by 2500). By debugging each experiment, I realized that there were several things going wrong. The data was a string of the format "X" where X is any positive integer < 10000.
This was in contrast to the expected input of an integer. This might have resulted in the other discrepancies, runtime improvement in the worst-case scenario (descending data) and improper mutation numbers across the algorithms.
For example, selection sort should have n/2 mutations for descending data but it did not. I tried to verify this by creating my own truly descending data file, but that didn't resolve the issue. As a result, by working through the debugger, I understood that these deviations are a result of the 
integers being treated as strings in combination with an improper descending dataset. Together, these made the descending dataset not behave as if it truly was the worst-case scenario (a truly descending set of objects). Random data, however, did fall within the bounds of ascending data and descending data for the most par (for most n tested).
From the code, I observed that each algorithm is O(n^2) in the worst case scenario and i generally observed a quick increase in time taken for each algorithm as the data size increased.


## PART III: ANALYSIS OF SELECTION SORT

**Determine exactly how many comparisons C(n) and assignments A(n) are performed by this 
implementation of selection sort in the worst case. Both of those should be polynomials of degree 2 
since you know that the asymptotic complexity of selection sort is O(n^2).**

The worst case scenario is a pure descending list of objects. Let's define there to be n elements in the array. 
For the number of comparisons in this implementation, line 3 makes n comparisons as the loop compares i and a.length - 1 n times.
So, C(n) = n for now. The next comparison happens in line 5 in the nested for loop. When i = 0, there are n comparisons of the inner loop. 
Then for i = 1, there are n - 1 comparisons. Taking this behavior to i = n - 2, we see that there are 0 iterations of the inner loop. Summing together 
all of these comparison (n + n-1 + n-2 + n-3 + .... + 2), we get the total number of total comparisons of the inner loop. We can approximate that number
of comparisons as (n(n+1))/2 - 1. So, C(n) = -1 + n + (n(n+1))/2. Observing line 6, the last place any comparisons happen, we can also observe additional comparisons. If i = 0, the comparison
in the if statement is evaluated n-1 times. Subsequently, if i = 1, the statement has n - 2 checks and for i = n - 2, there is 1 check. We can approximate this number (n-2 + n-3  + .... 1)
as ((n(n+1))/2) - n, which through algebraic manipulation, evaluates to (n(n-1))/2. Finally, we have the total number of comparisons as C(n) = -1 + n + (n(n+1))/2 + (n(n-1))/2. Through further 
manipulation this evaluates to C(n) = -1 + n + 0.5n^2 + 0.5n + 0.5n^2 - 0.5n which yields C(n) = n^2 + n - 1. 

Moving on to the number of assignments, we see observe that the worst case is also a purely descending list of objects. Let's again define there to be n elements in the array.  
Line 3 has 1 assignment from the i = 0 and n - 1 assignments from i++. There are n - 1 assignments as the loop only increments i n - 1 times given the condition of a.length - 1.
As a result, we can see that A(n) = n - 1 + 1 = n after line 3. The next assignment occurs in line 4 where max = i. This occurs n - 1 times. A(n) = n + n - 1 = 2n - 1. The next 
assignment occurs at line 5. If i = 0, then there is 1 assignment from the j = i + 1 and n - 1 assignments from the j++. If i = 0, then there is 1 assignment from j = i + 1
and n - 2 assignments from j++. This goes on until i = a.length - 2 where there is 1 assignment from the j = i + 1 and 1 assignment from j++.
Adding all of these assignments together, we get 1 + n - 1 + 1 + n - 2 +  ..... 1 + 1. Approximating this, we get (n - 1) + -n + (n(n + 1))/2 . A(n) = 2n - 1 + (n - 1) - n + (n(n+1))/2 = 2n - 2 + 0.5n^2 + 0.5n. Line 7, also has an assignment
operation. At i = 0, there are n - 1 assignment operations. When i = a.length - 2, there is 2 assignment operation. Summing this, there are n -1 + n-2 + ..... + 1 assignments from line 7. Approximating that, there are
(n(n + 1))/2 - n assignments from line 7. A(n) = 2n - 2 + 0.5^2 + 0.5n + 0.5n^2 + 0.5n - n = 2n - 2 + n^2. Finally in lines 10, 11, and 12, there are three assignment operations each occuring n - 1 times because of the outer for loop.
As a result, there are 3(n-1) assignments from these lines together. A(n) = 2n - 2 + n^2 + 3n - 3 = n^2 + 5n - 5.
