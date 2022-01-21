import java.io.*;
import java.util.*;
//Comp 2631
//Maxime Sotsky
public class WrathMain {
    public static void main(String[] args) throws FileNotFoundException
    {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the full path of input file.");
        String inFilePath = sc.next();
        File inFile = new File(inFilePath);
        sc.close();

        sc = new Scanner(inFile);

        int n = sc.nextInt();
        int m = sc.nextInt();

        int[][] edgeList = new int[m][2];

        for (int i = 0; i < edgeList.length; i++){
            edgeList[i][0] = sc.nextInt();
            edgeList[i][1] = sc.nextInt();
        }

        Wrath sort = new Wrath(n, m, edgeList);
        //List<Integer> order = sort.topoSort();    //to check if topo was correct
        //System.out.println(checkTopologicalSort(n, m, edgeList, order));
        int largesSize = sort.topoSort();
        System.out.println("The maximum size of S is: " + largesSize);
        
        sc.close();

    }
    
    //Dr. Keliher's method with few modifications (node index -1) (used to check if topo was correct)

    // Method to check the correctness of a claimed topological sort of the
    // nodes of a digraph.  Returns true if correct, and false otherwise.

    private static boolean checkTopologicalSort(int numNodes, int numEdges, int[][] edgeList, List<Integer> topoSortedList) {
        // Check that the topologically sorted list contains numNodes nodes.
        if (topoSortedList.size() != numNodes) {
            return false;
        } // if

        // Record the position in the topologically sorted list of each node.
        // Also, check that each node has a correct label, and that no node
        // appears more than once in the list.
        int[] positionInList = new int[numNodes];
        //positionInList[0] = -1;    // node 0 does not exist, so it has no position in the list
        boolean[] alreadySeen = new boolean[numNodes];   // alreadySeen[0] will be ignored
        for (int pos = 0; pos < numNodes; pos++) {   // pos is the position in the list
            int node = topoSortedList.get(pos);
            if (node < 0 || node > numNodes) {
                return false;
            } // if
            positionInList[node] = pos;
            if (alreadySeen[node]) {
                return false;
            } // if
            alreadySeen[node] = true;
        } // for pos

        // For each edge, check that it goes from a node with an earlier position
        // in the list to a node with a later position.
        for (int j = 0; j < numEdges; j++) {
            int x = edgeList[j][0];
            int y = edgeList[j][1];
            if (positionInList[x] > positionInList[y]) {
                return false;
            } // if
        } // for j

        return true;
    }  // checkTopologicalSort(int,int,int[][],ArrayList<Integer>)
    //--------------------------------------------------------------------
}