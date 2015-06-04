import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class Graph {
	 //Map of adjacency lists for each node
    Map<Integer, LinkedList<Integer>> adj;
  
    public Graph(ArrayList<Integer> nodes) {
        adj = new HashMap<Integer, LinkedList<Integer>>();
        for (int node : nodes) {
            adj.put(node, new LinkedList<Integer>());
        }
    }

    public void addNeighbor(int v1, int v2) {
    	//System.out.println(" in addneightbour get" + adj.get(v1));
        adj.get(v1).add(v2);
    }

    public LinkedList<Integer> getNeighbors(int v) {
        return adj.get(v);
    }
    
    public static boolean isNumeric(String s) {
	    return s.matches("\\d+");
	}
    
    //public Map<Integer, LinkedList<Integer>> getGraph(){
    //}

	public Map<Integer, LinkedList<Integer>> getGraph() {
		return adj;
	}

	public float getClusteringCoefficient() {
		return 0;
	}
	
	
}
