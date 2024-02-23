# Discussion

## PART I: MEASURED IndexedList

**Discuss from a design perspective whether iterating over a `MeasuredIndexedList`should 
affect the accesses and mutation counts. Note that for the purposes of this assignment we are NOT 
asking you to rewrite the `ArrayIndexedListIterator` to do so. However, if you wanted to include 
the `next()` and/or `hasNext()` methods in the statistics measured, can you inherit 
`ArrayIndexedListIterator` from `ArrayIndexedList` and override the relevant methods, or not? 
Explain.**

From a design standpoint, iterating over a list leads to accessing of each element, which 
makes sense that invoking the next() method of the iterator retrieving an element counts
as an access operation, incrementing numAccesses. However, iterating does not always modify the list's 
elements, so it would not make sense to treat iteration as a mutation, so mutations would stay same. 
However, mutations would be increased if there is an implementation that modifies the list during traversal.

Inheritance typically uses an IS-A relationship, so making `ArrayIndexedListIterator` inherit from `ArrayIndexedList`
would not make much sense. While this relationship could technically work, from a design point, this is confusing 
and less intuitive as the iterator is not a type of list, but rather a tool to traverse through the list. In addition,
in the context of next() and/or hasNext(), these methods would not be able to be overridden as the `ArrayIndexedListIterator`
is a private member of `ArrayIndexedList`. If the inner class was changed to protected or public, these methods could be overridden,
but the design would be more complicated. `MeasuredIndexedList` can still extend `ArrayIndexedList` and utilize the iterator
without these complications.


## PART II: EXPERIMENTS

**Explain the mistake in the setup/implementation of the experiment which resulted in a discrepancy 
between the results and what is expected from each sorting algorithm.**

A discrepancy was found reading through each data file, specifically descending.data. Ascending.data 
shows an increasing data set from [0, 9999] and random.data contains random numbers [0, 9999], 
but descending.data is not in proper descending order. The file has an incorrectly 
ordered value every ten data values. This may cause unexpected behavior in the sorting algorithm outputs. 

In `SortingAlgorithmDriver.java`, an important issue was found in the readData method.
This method reads the lines in each file as strings rather than integers, visible in `List<String> data = new ArrayList<>();`. 
This shows the algorithms are not sorting the .data files numerically, but through string comparison of ASCII values, causing code to compare
the first ASCII value of each number. For example, '15' would be less than '2' because the '1' in 15 has a lower ASCII value than '2'. This behavior 
was verified when driver was tested with small SIZE of 5. The results showed that the ascending order was "sorted" by null sort,
although null sort does not sort, confirming the sorting was based on string comparison, as numbers in this size have same string lengths, 
making ASCII order correct.

Overall, the null sort performed as anticipated and consistently showed zero mutations and accesses, which makes sense as it is O(1).
With the other sorts, an anomaly was seen when looking at ascending.data. Under normal circumstances, when the sorting algorithms 
sort through already sorted data, it would be expected to perform accesses but not mutations. However, the logs show numerous mutations
for each sort type (other than null) for SIZE 4000, which was not expected. While Bubble Sort and Insertion Sort can potentially operate 
in O(n) time on already sorted data, both Selection and Gnome Sort should generally show O(n^2) behavior. In worst case scenario, all 
of these should exhibit O(n^2), and this was maintained in the experimental results. Though, the number of accesses and mutations for 
descending data seemed low relative to what they should be. Due to the discrepancies of ASCII use 
and incorrect descending order, the experiment fails to reflect the genuine worst case scenario (sorting descending numerically), and 
performs slightly better than one would expect for descending. Random.data should approach average case performance, lying in between
that of ascending and descending (still O(n^2)), which was still exhibited in this experiment.

## PART III: ANALYSIS OF SELECTION SORT

1   public static void selectionSort(int[] a) {
2       int max, temp;
3       for (int i = 0; i < a.length - 1; i++) {
4          max = i;
5          for (int j = i + 1; j < a.length; j++) {
6            if (a[j] > a[max]) {
7              max = j;
8            }
9          }
10          temp = a[i];
11          a[i] = a[max];
12          a[max] = temp;
13       }
14   }

**Determine exactly how many comparisons C(n) and assignments A(n) are performed by this 
implementation of selection sort in the worst case. Both of those should be polynomials of degree 2 
since you know that the asymptotic complexity of selection sort is O(n^2).**

Let n represent an integer that represents a.length.

Comparisons C(n) (WORST CASE):
No comparisons are in lines 1-2, line 1 contains a parameter declaration line 2 has two variable declarations.
Line 3 has a comparison in `i < a.length - 1;`, which is checked n - 1 times, but it runs 1 extra time when loop exits, so total is n comparisons.
Line 5 has a nested for loop, which `j < a.length;` decreases iterations with each outer loop cycle. For the first iteration, it will run n times,
for the second n - 1 times, then n - 2.... until it reaches 1. The total number of iterations for this loop can be rewritten as (n(n+1))/2 mathematically.
Line 6 has a comparison `a[j] > a[max]` which can be evaluated as many times as the inner loop cause it is nested. However, this line is evaluated
once less for each outer loop iteration, which results in ((n(n+1))/2) - n = ((n(n-1))/2) comparisons.
Lines 4, 7, 10, 11, and 12 only have assignments and there are no comparisons.

Therefore, the number of comparisons as a function of n C(n) can be summed:
C(n) = n + ((n(n+1)/2) - 1) + (n(n-1)/2)
C(n) = n + ((n^2+n)/2) - 1) + ((n^2-n)/2)
C(n) = n + ((n^2+n)/2) - 1 + ((n^2-n)/2)
C(n) = n + n^2+n - 1 
C(n) = n^2 + 2n - 1

Assignments A(n) (WORST CASE):

Lines 1-2 have no assignments. 
Line 3's for loop has an assignment `int i = 0;`, which is 1 assignment, and `i++`, which will 
assign i as i + 1 for each loop iteration, contributing n - 1 times (as the loop runs n - 1 times). So
the total number of assignments on the line 3 for loop is (n - 1) + 1 = n.
On line 4, `max = i;` is called within the outer for loop, meaning it also has n - 1 assignments. 
Line 5 contains a nested for loop, and it initializes `int j = i + 1;` for each iteration of the outer loop, 
so it contributes n - 1 assignments.
On line 5, the nested for loop's number assignment would be the same as comparisons, being n, n - 1, n-2, etc. , 
until the final iteration. This can once again be shown as (n(n+1))/2. However, this once again must be subtracted by 
n because the loop does not run the full n iterations for each value, so ((n(n+1))/2) - n = (n(n-1))/2 once again.
On line 7, `max = j;` is an assignment that happens when line 6 is true. For worst case, it can be assumed
this assignment occurs every time the loop runs, resulting in the same as line 5 with (n(n-1))/2.
On lines 10, 11, 12, all of these are only in the outer for loop, therefore each of them run n - 1 times, 
for a total of 3(n - 1) assignments. 

Therefore, the number of assignments as a function of n A(n) can be summed:
A(n) = 1 + (n - 1) + (n - 1) + (n - 1) + ((n(n+1))/2) - 1) + ((n(n-1))/2) + (3n - 3)
A(n) = 1 + 3(n - 1) + ((n(n-1))/2) + ((n(n-1))/2) + 3(n - 1)
A(n) = 1 + 6(n - 1) + (n(n-1))
A(n) = 1 + 6n - 6 + n^2 - n
A(n) = n^2 + 5n - 5

