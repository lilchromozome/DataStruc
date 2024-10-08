# Homework 8

## Discussion 

As mentioned, `SystemRuntimeTest` and `MemoryMonitorTest` results in slightly different measurements. To account for this, 
each of these was ran 3 times, and the results were averaged. 

JHU to Druid Lake
Starting location: -76.6175,39.3296
Ending location: -76.6383,39.3206

--- Driver ---
Config: baltimore.streets.txt from -76.6175,39.3296 to -76.6383,39.3206
Network Loaded: 27598 roads, 9024 endpoints
Total Distance: 8818.5187
121.60 	45662:
137.15 	40816:
318.90 	40867:N_CHARLES_ST
60.49 	42002:E_33RD_ST
293.14 	8344:3200_BLK_N_CHARLES_ST
318.96 	11147:3200_BLK_N_CHARLES_ST
151.62 	39907:ART_MUSEUM_DR
665.00 	48094:UNIT__BLK_ART_MUSEUM_DR
129.55 	43910:ART_MUSEUM_DR
213.64 	46364:WYMAN_PARK_DR
255.02 	26692:2900_BLK_WYMAN_PARK_DR
42.03 	39554:N_HOWARD_ST
136.52 	26872:200_BLK_W_29TH_ST
146.67 	26712:200_BLK_W_29TH_ST
167.43 	15177:200_BLK_W_29TH_ST
230.86 	11871:200_BLK_W_29TH_ST
196.68 	14691:300_BLK_W_29TH_ST
224.61 	30101:300_BLK_W_29TH_ST
123.84 	5917:300_BLK_W_29TH_ST
79.80 	21125:300_BLK_W_29TH_ST
78.16 	21194:400_BLK_W_29TH_ST
115.90 	17656:400_BLK_W_29TH_ST
600.37 	26121:500_BLK_W_29TH_ST
480.41 	14609:2900_BLK_SISSON_ST
284.62 	23569:700_BLK_WYMAN_PARK_DR
394.12 	18109:800_BLK_WYMAN_PARK_DR
281.96 	31600:900_BLK_WYMAN_PARK_DR
39.03 	33121:900_BLK_WYMAN_PARK_DR
71.18 	34391:1000_BLK_WYMAN_PARK
1160.95 	41471:EAST_DR
190.65 	43386:UNNAMED_ST
1107.65 	41640:

--- SystemRuntimeTest ---
Config: baltimore.streets.txt from -76.6175,39.3296 to -76.6383,39.3206
Loading network took 440 milliseconds.
Finding shortest path took 59 milliseconds.
------     END     ------

--- MemoryMonitorTest ---
Config: baltimore.streets.txt from -76.6175,39.3296 to -76.6383,39.3206
	Used memory: 4806.89 KB (Δ = 0.000)
Instantiating empty Graph data structure
Instantiating empty StreetSearcher object
	Used memory: 4906.23 KB (Δ = 99.35)
Loading the network
	Used memory: 15368.17 KB (Δ = 10461.93)
Finding the shortest path
	Used memory: 16175.99 KB (Δ = 807.83)
Setting objects to null (so GC does its thing!)
	Used memory: 6011.22 KB (Δ = -10164.776)
-----     END     ------

7-11 to Druid Lake
Starting location: -76.6214,39.3212
Ending location: -76.6383,39.3206

--- Driver ---
Config: baltimore.streets.txt from -76.6214,39.3212 to -76.6383,39.3206
Network Loaded: 27598 roads, 9024 endpoints
Total Distance: 5827.3652
397.42 	24509:2800_BLK_REMINGTON_AVE
196.68 	14691:300_BLK_W_29TH_ST
224.61 	30101:300_BLK_W_29TH_ST
123.84 	5917:300_BLK_W_29TH_ST
79.80 	21125:300_BLK_W_29TH_ST
78.16 	21194:400_BLK_W_29TH_ST
115.90 	17656:400_BLK_W_29TH_ST
600.37 	26121:500_BLK_W_29TH_ST
480.41 	14609:2900_BLK_SISSON_ST
284.62 	23569:700_BLK_WYMAN_PARK_DR
394.12 	18109:800_BLK_WYMAN_PARK_DR
281.96 	31600:900_BLK_WYMAN_PARK_DR
39.03 	33121:900_BLK_WYMAN_PARK_DR
71.18 	34391:1000_BLK_WYMAN_PARK
1160.95 	41471:EAST_DR
190.65 	43386:UNNAMED_ST
1107.65 	41640:

--- SystemRuntimeTest ---
Config: baltimore.streets.txt from -76.6214,39.3212 to -76.6383,39.3206
Loading network took 495 milliseconds.
Finding shortest path took 49 milliseconds.
------     END     ------

--- MemoryMonitorTest ---
Config: baltimore.streets.txt from -76.6214,39.3212 to -76.6383,39.3206
	Used memory: 4821.24 KB (Δ = 0.000)
Instantiating empty Graph data structure
Instantiating empty StreetSearcher object
	Used memory: 4920.84 KB (Δ = 99.61)
Loading the network
	Used memory: 15402.12 KB (Δ = 10481.27)
Finding the shortest path
	Used memory: 16206.34 KB (Δ = 804.22)
Setting objects to null (so GC does its thing!)
	Used memory: 6072.52 KB (Δ = -10133.83)
------     END     ------

Inner Harbor to JHU
Starting location: -76.6107,39.2866
Ending location: -76.6175,39.3296

--- Driver ---
Config: baltimore.streets.txt from -76.6107,39.2866 to -76.6175,39.3296
Network Loaded: 27598 roads, 9024 endpoints
Total Distance: 16570.4909
462.64 	20226:100_BLK_SOUTH_ST
163.04 	48137:UNIT__BLK_SOUTH_ST
71.81 	47386:UNIT__BLK_SOUTH_ST
158.76 	47419:UNIT__BLK_SOUTH_ST
271.10 	47548:UNIT__BLK_SOUTH_ST
268.59 	47459:UNIT__BLK_GUILFORD_AVE
343.86 	28621:200_BLK_E_FAYETTE_ST
296.82 	33813:100_BLK_N_CALVERT_ST
134.19 	28959:200_BLK_N_CALVERT_ST
299.37 	24432:200_BLK_N_CALVERT_ST
454.21 	23235:300_BLK_N_CALVERT_ST
147.99 	9313:300_BLK_N_CALVERT_ST
185.36 	5947:400_BLK_N_CALVERT_ST
151.15 	30373:100_BLK_ORLEANS_ST
156.75 	31819:100_BLK_ORLEANS_ST
165.18 	36462:400_BLK_SAINT_PAUL_PL
199.96 	33237:500_BLK_SAINT_PAUL_PL
50.14 	42343:SAINT_PAUL_PL
204.02 	35069:500_BLK_SAINT_PAUL_PL
369.08 	27667:600_BLK_SAINT_PAUL_ST
67.58 	23282:600_BLK_SAINT_PAUL_ST
63.19 	8359:700_BLK_SAINT_PAUL_ST
201.99 	31442:700_BLK_SAINT_PAUL_ST
120.04 	22312:700_BLK_SAINT_PAUL_ST
164.64 	24117:800_BLK_SAINT_PAUL_ST
58.65 	2462:800_BLK_SAINT_PAUL_ST
163.17 	14582:800_BLK_SAINT_PAUL_ST
347.58 	16051:900_BLK_SAINT_PAUL_ST
147.31 	17769:900_BLK_SAINT_PAUL_ST
159.48 	34028:1000_BLK_SAINT_PAUL_ST
341.45 	8766:1000_BLK_SAINT_PAUL_ST
383.17 	1691:1100_BLK_SAINT_PAUL_ST
386.86 	4356:1200_BLK_SAINT_PAUL_ST
338.91 	19656:1300_BLK_SAINT_PAUL_ST
42.19 	40957:SAINT_PAUL_ST
46.30 	44623:SAINT_PAUL_ST
216.46 	39136:RAMP
338.93 	43462:I_83___S
92.13 	45367:I_83___S
107.28 	40942:N_CHARLES_ST
123.62 	40898:N_CHARLES_ST
468.44 	13325:1500_BLK_N_CHARLES_ST
386.86 	4876:1700_BLK_N_CHARLES_ST
212.37 	22851:1800_BLK_N_CHARLES_ST
244.03 	8607:1800_BLK_N_CHARLES_ST
46.58 	20118:1800_BLK_N_CHARLES_ST
382.67 	19448:1900_BLK_N_CHARLES_ST
366.72 	11838:2000_BLK_N_CHARLES_ST
367.72 	1734:2100_BLK_N_CHARLES_ST
376.14 	26449:2200_BLK_N_CHARLES_ST
393.44 	5511:2300_BLK_N_CHARLES_ST
239.67 	18280:2400_BLK_N_CHARLES_ST
99.97 	2083:2400_BLK_N_CHARLES_ST
204.48 	30800:2400_BLK_N_CHARLES_ST
209.45 	28376:2500_BLK_N_CHARLES_ST
75.71 	10673:2500_BLK_N_CHARLES_ST
102.26 	18381:2500_BLK_N_CHARLES_ST
165.69 	25136:2500_BLK_N_CHARLES_ST
467.97 	8403:2600_BLK_N_CHARLES_ST
467.02 	21531:2700_BLK_N_CHARLES_ST
465.66 	21565:2800_BLK_N_CHARLES_ST
231.86 	42406:N_CHARLES_ST
166.74 	41710:N_CHARLES_ST
64.04 	39032:N_CHARLES_ST
240.14 	41330:N_CHARLES_ST
233.97 	44412:N_CHARLES_ST
195.68 	44001:N_CHARLES_ST
205.02 	44656:N_CHARLES_ST
202.15 	42144:N_CHARLES_ST
245.47 	42334:N_CHARLES_ST
318.90 	40867:N_CHARLES_ST
137.15 	40816:
121.60 	45662:

--- SystemRuntimeTest ---
Config: baltimore.streets.txt from -76.6107,39.2866 to -76.6175,39.3296
Loading network took 454 milliseconds.
Finding shortest path took 124 milliseconds.
------     END     ------

--- MemoryMonitorTest ---
Config: baltimore.streets.txt from -76.6107,39.2866 to -76.6175,39.3296
	Used memory: 4823.80 KB (Δ = 0.000)
Instantiating empty Graph data structure
Instantiating empty StreetSearcher object
	Used memory: 4924.73 KB (Δ = 100.93)
Loading the network
	Used memory: 15417.64 KB (Δ = 10492.91)
Finding the shortest path
	Used memory: 16592.66 KB (Δ = 1175.01)
Setting objects to null (so GC does its thing!)
	Used memory: 6164.11 KB (Δ = -10428.55)
------     END     ------

Table of Analyzed Roads/Paths:
(Note that Memory Usage and Time Efficiency are both for Finding Shortest Path)
   Path Taken          |  Distance  | Memory Usage (KB) | Time Efficiency (ms) | 
  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
1. JHU to Druid Lake   | 8818.5187  |     16175.99      |         59           | 
2. 7-11 to Druid Lake  | 5827.3652  |     16206.34      |         49           |    
3. Inner Harbor to JHU | 16570.4909 |     16592.66      |         124          |     
																			
The analysis was performed on the three paths above. One unexpected observation from the data
was found in the memory usage across different route distances. Using the numbering in the table,
the memory usage was ranked 3 > 2 > 1, with distance ranking as 3 > 1 > 2. Contrary to what was 
initially thought, the memory usage did not linearly increase with distance. This can be seen in path 2,
which has shortest distance of the three, showing higher memory usage (16206.34 KB) compared to 
path 1, the middle place distance, which has memory usage 16175.99 KB. This suggests that variables
other than distance itself such as number of intersections may affect the memory requirement. Path 3 
was both the longest distance and most memory used, which aligned with what was expected, likely meaning
that distance, while a significant factor, is not the only determinant for memory usage. 

For time efficiency, results were more in line with expectations with ranking 3 > 1 > 2 (greater means more time).
This aligns with the distance ranking as mentioned earlier. This suggests that the time efficiency is likely linearly 
correlated with the distance of the route. Shorter routes generally demonstrated better time efficiency, with the path 2
being the fastest at 49 ms. The longest route, path 3, took the most time at 124 ms as it was the longest distance. This makes sense as
longer distances typically require more calculations or paths to evaluate and therefore take more time. Path 1 took 59 ms, slightly 
slower than path 2, which somewhat makes sense as it is decently larger in distance (8818.5187 - 5827.3652 = 
2991.1535 difference in distance). 
