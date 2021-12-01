# Gold-Miner

An application of path finding algorithms: A* and BFS.

Members:

Capunitan, Jonaviene 

Tolentino, John Enrico

Villarica, Matthew 


Task

Design an intelligent agent that simulates a Miner  on a rectangular grid of nxn squares. We set n to be from 8 to 64.  

The starting point for the Miner is always the square 1,1, on the upper left-hand corner of the grid, at row 1 and column 1. 

Somewhere on the grid is a unique golden square that contains the POT OF GOLD.  The agent finds its way to the golden square, and the shorter the path travelled,

the more rational is the agent.

 

Setup

MINING AREA

Each location (square) in the mining area (the nxn grid) can have only one of the following objects:

the miner
the pot of gold (G)
a pit (P)
a beacon (B)
FRONT

The miner has a front, indicating the direction of the actions scan and move. The miner moves around the mining area by displacing 
itself one square to the front, unless it is over the edge of the grid, which should be programmed as a technical “impossibility”.

SCAN

To view what is in front of the miner all the way to the edge of the mining area, the miner uses the action scan. 
The scan action returns only one of the following : p for pit, g for gold, b for beacon, depending on which one of these 
is nearest to the miner. The distance of the miner to the object is not returned. If there is no pit, beacon nor gold in all the squares in front of the miner,
then the action scan returns “NULL”. 

ROTATE

The direction of its front can be altered through the action rotate, which moves 90 degrees clockwise each time rotate is invoked.

PIT

If the miner moves into square that is a pit, it is game-over! Remember that the miner cannot see the locations of the pits.

BEACON

A beacon on a square indicates that from that square, the golden square can be reached in m squares in any vertical or horizontal direction, where m < n, 
without ever falling into a pit. The value of m is not returned by the action scan.

GOLDEN SQUARE

If the miner moves into square that contains the pot of gold,   the miner stops, and delivers a “search successful” message. 

AGENT RATIONALITY

The locations of the pits and beacons are variables. The paths cannot be explored ahead before the miner moves.

The most intelligent agent will be determined by comparing the average number of squares that were traversed to reach the golden square. 

Design

It should be convenient to enter size of the grid (value for n), and to specify the locations of the pits, beacons and gold.
Feel free to design how your mining area would look like on screen, but by default, an nxn matrix of distinguishable squares would be minimum, that would indicate the current position of the miner and the direction of its front, as well as the locations of the pits, beacons, and pot of gold.
Feel free to design icons for how your miner, pits, beacons, and pot of gold will look like on screen. But the letters P, B and G would also suffice.
Feel free, also, to include sounds and tunes, as well as color and screen effects, to liven up the mining process.
Design the interface to allow a step-by-step or fast view of the miner as it moves around the mining area.
Include a dashboard of counters that keeps track of the number of rotates, scans, and moves that were performed. You may also include a counter for the number of “backtracks”.
Build at least two levels of “rational behavior”
Level R (Random): the miner makes random (but valid) moves, and wanders about the grid until it accidentally trips on the golden square.
Level S (Smart): the agent is smart that finds the “best” move given the current configuration. You can have memory of the past moves.
Note: You may use any programming language. 

Deliverables

1. Source code of the project 

2. Technical Report 

3. Screen shots of the running program to describe the interface 
