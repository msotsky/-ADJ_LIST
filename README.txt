5. Carefully describe the structure of your program in a file name README.txt

Much of the code has been re-used from the prior topological sorting lab.
There are few modifications however, that were made to tailor the code for this assignment.
The Wrath object represents the graph object. Within it, the adjacency list is constructed using a external method in AdjList class.
The constructor throws the appropirate exceptions to make sure there data is correctly formated and valid.
Since this is a greedy algorithm for topo sort, revolving around the highest outdegree node, I made a compute
out degree method that returns a list of each outdegree of the nodes in the graph.
The positions of the list -similarly to the indegree list and edgelist, is the label of the node.
The sorting algorithm uses a queue to store the free nodes, and an iterator is used to find the appropriate
node to be removed, judging by its out degree (this is the 'greedy' part).
As usual, when a node is chosen (the highest out degree one), the nodes that transition out of it have their in degree decremented so the
algorithm can see that they have been 'freed up'. If the degree node whose in degree has reached zero, these are added
to the S list and await being processed by the algorithm (s is the queue).
An int variable is used to keep track of the largest size of the queue. It is updated when the size of the queue is
greater than the current value it holds. At the end of the algorithm (s is empty or graph has a loop) the integer value
stored in largestSize is returned.