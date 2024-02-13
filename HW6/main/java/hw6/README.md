# Discussion

## Unit testing TreapMap
There are three main difficulties I had when testing TreapMap. Due to the random nature of the random number generator, I had difficulty in determining 
the priority numbers, but I solved this using a non-default constructor for treap and setting my own seed
value. All unit tests involve this. However, using these randomly generated priorities to then generate pre-determined structures to observe 
specific rotations was quite difficult as I couldn't easily insert nodes into the tree in the order that I wanted to.
Below are my unit tests and attempts for certain difficult unit tests:

1. One example of a test that I had difficulty implementing was for a single right rotation:

This is implemented by my insertRightRotation() test in TreapMapTest which is as follows:

map.insert("7", "a");
map.insert("5", "b");
map.insert("3", "c");

Because I used a fixed seed values, the sequence of priorities is always the same (i.e. time invariant).
7's priority was -1157793070
5's priority was 1913984760
3's priority was 1107254586

ASCII tree: 
    7
     \
      5
     /
    3
After the right rotation, the structure becomes as follows:
    7
   /
  3
   \
    5

To generate this test, I first inserted 7, then 5, and then 3. However, due to my specific sequence of priorities, it has to be in this order
for a right rotation with three to occur (due to a lower priority value than 5).

2. Another example I had difficulty generating was inserting and having only multiple right rotations

I examined this problem in my insertMultipleRightRotation() unit test which is as follows:

map.insert("5", "e");
map.insert("4", "d");
map.insert("3", "c");
map.insert("2", "b");
map.insert("1", "a");

My priorities are as follows: 
5's priority: -1157793070
4's priority: 1913984760
3's priority: 1107254586
2's priority: 1773446580
1's priority: 254270492

The progression of rotations is as follows:

        5
       /   
      4 
     /       
    3     
After the insertion of 3, a right rotation occurs leading to 

         5
        /  
       3    
        \       
         4   
Then insertion continues:
        5
       /
      3    
     /  \
    2    4 
   /
  1
The insertion of 1 causes another right rotation:

        5
       /  
      3
     /  \
    1    4
     \
      2
We're still not done as 1's priority is still lower than 3, so we do another right rotation:

        5
       /
      1
       \
        3
       / \
      2    4 

In order for us to observe only multiple right rotations, we have to insert in this exact pattern. If we inserted in  ascending order for example,
then the root node would be 1 and would stay one and we would only observe left rotations. If we had varied this order and scrambled the numbers, we could not control 
for the type of rotations as we cannot directly control the priorities. I had to carefully execute this order in order to observe my intended effect.

3. Lastly, I tested the remove functions which were difficult to conceptualize.

You can either remove a leaf, a parent with one child, or a parent with two children. From these removals, you can have 
either only left rotations, only right rotations, or a mix. For my unit tests, I decided to isolate one of these cases which was diffcuilt.

I tested the case of removing one parent with two children and observing a single right rotation
This is covered in my removeSingleLeftThenRightRotationTwoChildren() unit test which is as follows: 

map.insert("6", "b");
map.insert("3", "c");
map.insert("8", "a");
map.remove("6");

Priorities: 
6: -1157793070
8: 1107254586
3: 1913984760

After all the insertions, we arrive at the following ASCII tree:

            6
          /  \
         3    8
            
When we remove 6, we replace its value with the child with the lowest possible priority and rotate until 
the node to remove becomes a leaf. During the removal, we first do a left rotation since 8 has a smaller priority than 3.


ASCII tree:

        8
       /  
      6    
     /      
    3        
      
Afterwards, the to be removed node (6) still has a child, so we perform another rotation. In this case, we do a right rotation.

ASCII tree:

    8
   /
  3

This was difficult as I had to make sure 6 had a higher priority (i.e lower value) than 3 and 8 and at the same time make sure that
8's priority was higher than 7's priority so a left rotation would take place then the right rotation. For example, I couldn't add the elements
in a different order as I need to the priority ordering to remain the same so that a left and then right rotation will occur . 

Treap's use of random priority generation makes it very difficult to create focused, unit tests. However, these three unit tests are
examples of how I was able to think through and overcome them, making sure to take into account the placement of each node after each insert according to its priority 
and how that would affect the removal behavior if a removal operation was performed.
## Benchmarking
For the hotel_california.txt 
Benchmark                  Mode  Cnt  Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2  0.274          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2  0.200          ms/op
JmhRuntimeTest.bstMap      avgt    2  0.208          ms/op
JmhRuntimeTest.treapMap    avgt    2  0.259          ms/op

For moby_dick.txt
Benchmark                  Mode  Cnt     Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2  4038.343          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2   162.251          ms/op
JmhRuntimeTest.bstMap      avgt    2   169.078          ms/op
JmhRuntimeTest.treapMap    avgt    2   188.686          ms/op

For federalist01.txt
Benchmark                  Mode  Cnt  Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2  2.848          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2  1.212          ms/op
JmhRuntimeTest.bstMap      avgt    2  1.138          ms/op
JmhRuntimeTest.treapMap    avgt    2  1.510          ms/op

For pride_and_prejudice.txt
Benchmark                  Mode  Cnt    Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2  915.594          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2  104.362          ms/op
JmhRuntimeTest.bstMap      avgt    2  105.763          ms/op
JmhRuntimeTest.treapMap    avgt    2  118.732          ms/op

Amongst all the tests, arrayMap performed the worst out of all four Map implementations.
This is because the has, put, and insert methods are all O(n) operations. In the other implementations,
the average cases are as follows: bstMap (has, put, and insert are close to O(lgn) as stated on the 
data structure coursepage for BSTs), treapMap (O(lgn) for has, put, and insert), and finally avlTreeMap is also O(lgn) for
has, put, and insert. This result, makes sense then as all of them implement find which is O(lgn) for a balanced tree. Examining the worst case of each implementation 
(when find has to linearly search through 
each node subsequently linked to one another (e.g. a degenerate bst or treap can be a linkedlist)), we can see that all of them
but AVLtree have a O(n) worst case runtime for all methods (put, insert, and has). 
Using this logic, we observe that AVLtree is indeed faster in pride_and_prejudice, moby_dick, and hotel_california than any other implementation.
AVLtree is marginally slower than treapmap in federalist01.txt, but this might be due to the short text length. For shorter texts, BSTs may be more likely 
to be balanced or close to it. As a result, the rebalancing operations don't help to increase efficiency for short text lengths in AVLtreemap and treapmap.
Lastly, AVLtreeMap outperforms treapMap as the worst case for treapMap is O(n) but is O(lgn) in avlTreeMap. This is because treapMap only obeys the order property
of BSTs and the min-heap property (for the randomly generated priorities). As a result, it may not be balanced like avlTreeMap, which will decrease the efficiency of find
especially with larger text sizes. 
