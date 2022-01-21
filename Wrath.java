import java.util.*;
//Comp 2631
//Maxime Sotsky
public class Wrath {
    //extra notes

    //Different from lab is now choosing the highest outdegree instead of lowest indegree
    //instead of printing the sorted list we print the largest size of the queue

    //Khans algorithm creates a sorted list but were just using it to return 
    //the max possible source nodes during the algorithms execution
    //returning max source size

    //====================================================================================

    //katis

    //input: single directed graph
    //       first line contains n and m
    //       1 <= n <= 500, 0 <= m <= n(n-1)
    //       where n = numNodes , m = numEdges
    //  followed by m lines containing x and y (0 <= x,y <= (n-1)) x != y
    //  indicating that there is a directed edge from node x to node y
    //  nodes are indexed 0,1,2,3...(n-1) No edges listed more than once

    //Graph G , empty list L -repeatedly executes the following 3 steps

    // 1. Let S be the set of source nodes, ie. the nodes with no incoming edges.
    //    If S is empty, terminate the algorithm
    //
    // 2. Let alpha be any node in S.
    //    Remove alpha and all  its outgoing edges from G.
    //    If this removal of edges creates new source nodes, add these to S.
    // 3. Insert alpha at the end of L

    //Once Kahn's algorithm terminates, if G is now empty, then L contains the nodes of the initial graph
    //in topologically sorted order (may not be unique).
    //Otherwise, G has one or more cycles, and therefore topological sorting is impossible

    //examples: 
    /*
-----------------------------------
    IN      --->    OUT
    4 3              1
    0 1
    1 2
    2 3
-----------------------------------
    IN      --->    OUT
    5 5              3
    0 4
    1 2
    1 3
    2 4
    3 4
-----------------------------------
    */

    //Assignment
    //Idea behind flawed solution:
    //Goal: Maximize the number of nodes in S, run Khan's algorithm,
    //always choosing alpha in step 2 to be a node with the highest out-degree
    //(of all the current nodes in S), because removing a node with the highest out-degree has the best chance
    //of creating new source nodes.
    //If there are multiple nodes in S with the same highest-out degree, choose alpha to be the lowest
    //indexed node.
    //In the end, report the largest number of nodes that S contained during the entire process.

    //Program should be able to handle an input file of 100 000 nodes and 300 000 edges

    //Finnally, in order to prove that this solution approach is flawed,
    //create an input file named fail-in.txt that causes it to fail (smallish graph)
    //i.e. that causes the program to produce an answer that is not the true max size of S as described in
    //wrath.pdf.
    //put the correct answer in fail-out.txt, and describe the graph and why it causes your program to fail
    //in fail-desc.txt
    private int numNodes;
    private int numEdges;
    int[][] edgeList;
    private AdjList adj;
    //private int largestSize = 0;

    //Majority of code re-used from lab (Noah , Max)

    public Wrath(int nNodes, int nEdges, int[][] edges){
        if (nNodes < 0)
            throw new IllegalArgumentException("Number of nodes cannot be negative");
        if (nEdges < 0)
            throw new IllegalArgumentException("Number of edges cannot be negative");

        numNodes = nNodes;
        numEdges = nEdges;

        if (!checkEdgeList(numNodes, numEdges, edges))
            throw new IllegalArgumentException("Invalid edge list.");

        edgeList = new int[numEdges][2];
        this.edgeList = edges;
        adj = new AdjList(numNodes, numEdges, edgeList);
    }


    private boolean checkEdgeList(int nNodes, int nEdges, int[][] edges){
        if (edges == null || edges.length != nEdges)
            return false;

        for (int i = 0; i < edges.length; i++){
            if (edges[i] == null || edges[i].length != 2)
                return false;

            for (int j = 0; j < 2; j++){
                if (edges[i][j] < 0 || edges[i][j] > nNodes)
                    return false;
            }

        }
        return true;
    }

    private int getNumNodes(){
        return numNodes;
    }
    private int getNumEdges(){
        return numEdges;
    }

    private int[] computeInDegrees(){

        int[] inDegrees = new int[getNumNodes()];

        for (int i = 0; i < numEdges; i++)
            inDegrees[edgeList[i][1]]++;

        return inDegrees;
    }

    private int[] computeOutDegrees(){
        int[] outDegrees = new int[getNumNodes()];

        for(int i = 0; i < numEdges; i++)
            outDegrees[edgeList[i][0]]++;

        return outDegrees;
    }
  
    public int topoSort(){

        int largestSize = 0;
        Queue<Integer> s = new ArrayDeque<>();
        List<Integer> order = new ArrayList<>();
        int[] degrees = computeInDegrees();
        int[] outDegrees = computeOutDegrees();
        List<Integer> out = new ArrayList<>();

        for(int n : outDegrees)
            out.add(n);

        for (int i = 0; i < getNumNodes(); i++){
            if (degrees[i] == 0)
                s.add(i);     
        }
        while (!s.isEmpty()){
            if(s.size() > largestSize)
                largestSize = s.size();
                
            Iterator<Integer> value = s.iterator(); //greed
            int x = value.next();
            int z = 0;
            while(value.hasNext()){
                z = value.next();
                if(outDegrees[x] < outDegrees[z])
                    x = z;
            } 
            
            order.add(x);
            s.remove((Integer)x);
        
           for (int y : adj.getOutNeighbours(x)){
                degrees[y]--;
                if (degrees[y] == 0){
                    s.add(y);
                }
            }
            
        }
        return largestSize;

    }
}
