# Discussion
Below are the results for time and space of each map implementation along with a comparison with 
the JDKHashMap for each of the files. I profiled both the chainedHashMap and openAddresingHashMap implementations
under two different load factors as well to observe performance differences. For concision, I present averages
of each implementation against all files for both memory and speed. I attached the full data as well so that exact data can be viewed.


JDK average time:

170.00 ms/operation with an average memory consumption of 202.1 Mb

ChainingHashMap with load factor of 0.75:

228.06 ms/operation with an average memory consumption of 241.68 Mb

OpenAddressingHashMap with load factor of 0.75:

179.9 ms/operation with an average memory consumption of 199.78 Mb

ChainingHashMap with load factor of 0.5:

242.2 ms/operation with an average memory consumption of 278.8 Mb

OpenAddressingHashMap with load factor of 0.5:

180.32 ms/operation with average memory consumption of 238.55 Mb.

From these averages alone, we can see that OpenAddressing has clear performance increases in both 
memory consumption as well as speed against ChainingHashMap. Diving further into the data, we can also see that openAddressingHashMap
even outperforms JDKHashMap (and ChainingHashMap) for speed and memory for both load factors in the apache.txt file. Both other map implementations beat JDKHashmap generally when it comes to 
memory consumption, though. For almost every file, for both speed and memory efficiency, OpenAddressingHashMap is
more performant than ChainingHashMap. I found it surprising that OpenAddressingHashMap had minimal differences in efficiency  at a lower load factor (0.5)
as I would have expected to performance to decrease with a lower and lower load factors (as the number of rehashes will quickly grow). However, I only observed an increase
in memory consumed for a lower load factor compared to the load factor of 0.75. 

I tried testing multiple different probing strategies for OpenAddressingHashMap (linear and quadratic). However, while testing 
quadratic probing, I found it inefficient as attempting to insert into an empty spot as quickly as possible. This is because quadratic search doesn't methodically check 
for the next available open spot. In a full array, this makes it difficult to insert into the array for a new node. Additionally, I wasn't able to pass insert case 4 for OpenAddressingHashMap
using my implementation of quadratic probing. Linear probing was the simplest while likely being the more performant probing strategy as it is most able to quickly find the next 
available open spot in the array. For ChainingHashMap, I tried two difference approaches: 1) coding my own linkedlist from scratch using a private node class and 2) using the 
inbuilt linkedlist API. I found it more streamlined to use Java's API, so I decided to go ahead and use it rather than to make my own linkedlist and have to deal with a variety of edge 
cases and performance issues. For both OpenAddressingHashMap and ChainingHashMap, I decided to try different load factors as well and observe differences in performance. I know that choosing a load
factor requires a careful balance of the cost of rehashing with the number of collisions in a full table. From this balance, I found that 0.75 worked well for both map 
implementations. 

From my data and observance 
of performance, I would choose OpenAddressingHashMap with a linear probing strategy and a load factor of 0.75 to use as my Map implementation for a search engine.


Data for reference to check (length not part of the above description which summarizes answers)
JDKHashMap reference 
Benchmark                                                                 (fileName)  Mode  Cnt           Score   Error   Units
JmhRuntimeTest.buildSearchEngine                                          apache.txt  avgt    2         225.359           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               apache.txt  avgt    2   284516632.000           bytes
JmhRuntimeTest.buildSearchEngine                                             jhu.txt  avgt    2           0.223           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                  jhu.txt  avgt    2    25961708.000           bytes
JmhRuntimeTest.buildSearchEngine                                          joanne.txt  avgt    2           0.090           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               joanne.txt  avgt    2    25899212.000           bytes
JmhRuntimeTest.buildSearchEngine                                          newegg.txt  avgt    2         101.191           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               newegg.txt  avgt    2    90933340.000           bytes
JmhRuntimeTest.buildSearchEngine                                       random164.txt  avgt    2         693.497           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc            random164.txt  avgt    2   761454792.000           bytes
JmhRuntimeTest.buildSearchEngine                                            urls.txt  avgt    2           0.028           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                 urls.txt  avgt    2    24098096.000           bytes

ChainingHashMap with load factor of 0.75 

JmhRuntimeTest.buildSearchEngine                                          apache.txt  avgt    2         244.110           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               apache.txt  avgt    2   131512480.000           bytes
JmhRuntimeTest.buildSearchEngine                                             jhu.txt  avgt    2           0.297           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                  jhu.txt  avgt    2    26379748.000           bytes
JmhRuntimeTest.buildSearchEngine                                          joanne.txt  avgt    2           0.132           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               joanne.txt  avgt    2    26125052.000           bytes
JmhRuntimeTest.buildSearchEngine                                          newegg.txt  avgt    2         133.593           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               newegg.txt  avgt    2    86638888.000           bytes
JmhRuntimeTest.buildSearchEngine                                       random164.txt  avgt    2         990.209           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc            random164.txt  avgt    2  1155209924.000           bytes
JmhRuntimeTest.buildSearchEngine                                            urls.txt  avgt    2           0.029           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                 urls.txt  avgt    2    24266920.000           bytes

OpenAddressingHashMap with load factor of 0.75

JmhRuntimeTest.buildSearchEngine                                          apache.txt  avgt    2         221.342           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               apache.txt  avgt    2   129655600.000           bytes
JmhRuntimeTest.buildSearchEngine                                             jhu.txt  avgt    2           0.241           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                  jhu.txt  avgt    2    25911744.000           bytes
JmhRuntimeTest.buildSearchEngine                                          joanne.txt  avgt    2           0.096           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               joanne.txt  avgt    2    25941104.000           bytes
JmhRuntimeTest.buildSearchEngine                                          newegg.txt  avgt    2         120.071           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               newegg.txt  avgt    2    93587892.000           bytes
JmhRuntimeTest.buildSearchEngine                                       random164.txt  avgt    2         737.649           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc            random164.txt  avgt    2   899497572.000           bytes
JmhRuntimeTest.buildSearchEngine                                            urls.txt  avgt    2           0.029           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                 urls.txt  avgt    2    24120616.000           bytes

ChainingHashMap with load factor of 0.5

JmhRuntimeTest.buildSearchEngine                                          apache.txt  avgt    2         250.507           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               apache.txt  avgt    2   156299024.000           bytes
JmhRuntimeTest.buildSearchEngine                                             jhu.txt  avgt    2           0.309           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                  jhu.txt  avgt    2    25856484.000           bytes
JmhRuntimeTest.buildSearchEngine                                          joanne.txt  avgt    2           0.119           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               joanne.txt  avgt    2    26787832.000           bytes
JmhRuntimeTest.buildSearchEngine                                          newegg.txt  avgt    2         126.752           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               newegg.txt  avgt    2    97496188.000           bytes
JmhRuntimeTest.buildSearchEngine                                       random164.txt  avgt    2        1075.636           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc            random164.txt  avgt    2  1342049492.000           bytes
JmhRuntimeTest.buildSearchEngine                                            urls.txt  avgt    2           0.029           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                 urls.txt  avgt    2    24381016.000           bytes

OpenAddressingHashMap with load factor of 0.5

JmhRuntimeTest.buildSearchEngine                                          apache.txt  avgt    2         212.666           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               apache.txt  avgt    2   150975112.000           bytes
JmhRuntimeTest.buildSearchEngine                                             jhu.txt  avgt    2           0.274           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                  jhu.txt  avgt    2    25716592.000           bytes
JmhRuntimeTest.buildSearchEngine                                          joanne.txt  avgt    2           0.102           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               joanne.txt  avgt    2    25663388.000           bytes
JmhRuntimeTest.buildSearchEngine                                          newegg.txt  avgt    2         110.240           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               newegg.txt  avgt    2    93489308.000           bytes
JmhRuntimeTest.buildSearchEngine                                       random164.txt  avgt    2         758.600           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc            random164.txt  avgt    2  1111277924.000           bytes
JmhRuntimeTest.buildSearchEngine                                            urls.txt  avgt    2           0.028           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                 urls.txt  avgt    2    24202508.000           bytes


