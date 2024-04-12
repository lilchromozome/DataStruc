# Discussion
Hashmap Approaches Discussion:
`ChainingHashMap` uses separate chaining with linked lists to handle collisions. LinkedLists are effective in handling collisions and 
allow dynamic resizing without resizing the entire hash table, giving advantage over using arrays. Each bucket is a chain of nodes, 
handling collisions by storing elements at same hash index. To reduce collisions, an array of prime numbers is used for dynamic resizing. 
This leverages properties of prime numbers for more uniform key distribution, reducing clustering. Capacity is doubled when the array end 
is reached. Initially, this was set to trigger an error, but this was adjusted to avoid early program termination.

`ChainingHashMap`'s original rehash() reset size and depended on insert() to increment size for newly added elements. This approach was not
optimal since insert() performs additional checks for key existence and LF, processing every cell, even empty ones, leading to more inefficient
processing. Rehashing only occurs when a resize is necessary, so these checks were redundant. In new design, each node directly assigns to its new
bucket. The count of elements remains constant as they are reallocated among the new buckets. `OpenAddressingHashMap` had the same rehashing issue 
but differed as the rehashed index of an entry in the new array can differ from its original index. This means incrementing the size of the hash table 
during rehashing is necessary to correctly count elements. Size was directly modified in this rehash() to avoid unnecessary operations. 
`OpenAddressingHashMap` uses linear probing as the collision strategy due to simplicity. Linear probing sequentially searches for the next available slot
when a collision occurs. The same primes array technique was used. Tombstones were decided not to be carried over to the new array in rehashing, effectively 
cleaning up 'dead' entries, optimizing the overall performance/memory usage of the hash table. Other probing methods such as quadratic probing or cuckoo were not explored
due to their increased complexity and less intuitive nature to implement.

Benchmark Data Discussion and Final Map Decision (LF = Load Factor), (T = Time Efficiency (ms/op)), (S = Space Efficiency (bytes)):
ChainingHashMap Performance Comparison 
Dataset       | File Size |T, LF = 0.33|S, LF = 0.33 |T, LF = 0.50|S, LF = 0.50 |T, LF = 0.75|S, LF = 0.75  |T, LF = 0.90| S, LF = 0.90 
apache.txt    | large-ish |  483.862   |143217428.000|605.926     |143678504.000|410.737     |112162960.000 |532.796     |171757720.000
jhu.txt       | small     |   0.774    |15748572.000 |0.537       |15791608.000 |0.473       |15850460.000  |0.497       |15932148.000
joanne.txt    | small     |   0.255    |16130572.000 |0.303       |14932244.000 |0.184       |15879728.000  |0.178       |16498000.000 
newegg.txt    | large-ish |  255.267   |90856860.000 |270.871     |146636840.000|191.424     |169157592.000 |262.041     |77780080.000
random164.txt | big!      |  2622.968  |921601716.000|2607.706    |933284580.000|1716.760    |1092161448.000|1578.770    |1035474112.000
urls.txt      | tiny      |   0.092    |14695140.000 |0.056       |14885704.000 |0.050       |15024512.000  |0.057       |15030068.000
Average       |   x       |   560.536  |200375048    |580.900     |211534913.333|386.605     |236706116.667 |395.723     |222078688.000
T/S Avg Ratio |   x       |         2.797E-6         |         2.746E-6         |         1.633E-6          |         1.782E-6

OpenAddressingHashMap Performance Comparison
Dataset       | File Size |T, LF = 0.33|S, LF = 0.33 |T, LF = 0.50|S, LF = 0.50  |T, LF = 0.75|S, LF = 0.75  |T, LF = 0.90| S, LF = 0.90
apache.txt    | large-ish |  517.701   |142313368.000|666.759     |177589156.000 |443.499     |143150844.000 |459.100     |142811380.000
jhu.txt       | small     |   0.469    |16430104.000 |0.664       |15263908.000  |0.511       |15901384.000  |0.502       |16064452.000
joanne.txt    | small     |   0.416    |15472024.000 |0.278       |15590104.000  |0.186       |16423088.000  |0.201       |15692180.000
newegg.txt    | large-ish |  381.494   |72098976.000 |202.957     |105603792.000 |221.104     |169537732.000 |224.073     |79084460.000
random164.txt | big!      |  2293.458  |941486968.000|2215.204    |1008891632.000|1652.564    |1047522584.000|1750.740    |1013524008.000
urls.txt      | tiny      |   0.048    |15000272.000 |0.067       |14707264.000  |0.051       |14930220.000  |0.049       |15005160.000
Average       |   x       |   532.264  |200466952    |514.322     |222940976.000 |386.319     |234577642.000 |405.778     |213696940.000
T/S Avg Ratio |   x       |         2.655E-6         |         2.307E-6          |         1.647E-6          |         1.899E-6

The benchmarking data showed how `ChainingHashMap` and `OpenAddressingHashMap` perform under different LF and file sizes. `ChainingHashMap` 
performed exceptionally well with smaller to large-ish file sizes, mainly at LF 0.75. As LF increased from 0.33 to 0.75, avg time 
performance improved from 560 to 386 ms/op. Increasing to LF = 0.90 showed diminishing returns with a time efficiency drop to 395 ms/op, 
likely due to the increased collision rate leading to longer chains. `OpenAddressingHashMap` showed consistent performance, maintaining 
a somewhat steady time efficiency from LF = 0.33 -> 0.50, and improved at LF = 0.75 and diminishing returns at LF = 0.90. LF = 0.75 had best 
performance in both hash maps and lowest T/S avg ratios. At LF 0.75, each file had better time performance from `ChainingHashMap` except 
`random164.txt`. This file is interestingly `big!`, indicating `OpenAddressingHashMap` handles larger files better, including space efficiency,
specifically seen with `random164.txt` at 1047522584.000 vs 1092161448.000 bytes, a 44638864 byte improvement. This was another surprising
finding as chaining was thought as more memory efficient due to linked list usage. For space efficiency at lower LF, `ChainingHashMap` has 
an edge and lower space usage, showing it is better for smaller datasets. `OpenAddressingHashMap` has the advantage at higher LF and is better 
for larger datasets.

The final decision on which map implementation to use for Jhugle should be determined by the expected dataset size and the prioritization of time 
versus space efficiency. If the engine is expected to handle smaller to large-ish files most frequently, then `ChainingHashMap` at LF 0.75 may 
offer the best balance between time and space efficiency. However, for scenarios with large or extremely large files or when high load factors 
can't be avoided, `OpenAddressingHashMap` at LF 0.75 may be the preferable choice due to its surprisingly great performance for densely populated 
hash tables as shown in the data.