import java.util.*;
//Comp 2631
//Maxime Sotsky
public class AdjList {
    private int numNodes;
    private List<List<Integer>> adjList;

    public AdjList(int nNodes, int nEdges, int[][] edges){
        numNodes = nNodes;
        adjList = new ArrayList<List<Integer>>();
        for (int i = 0; i < nNodes+1; i++){          
            adjList.add(new ArrayList<Integer>());
        }

        for (int i = 0; i < nEdges; i++){
            adjList.get(edges[i][0]).add(edges[i][1]);
        }
    }

    public List<Integer> getOutNeighbours(int n){
        if (n > adjList.size() || n < 0)
            throw new IllegalArgumentException("Invalid input.");
            
        return adjList.get(n);
    }
}
