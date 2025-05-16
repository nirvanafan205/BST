import java.util.*;
import java.io.*;

public class Patrol 
{
    private static class Edge implements Comparable<Edge> 
    {
        String planetOne;
        String planetTwo;
        int cost; 

        Edge(String planetOne, String planetTwo, int cost) 
	{
            this.planetOne = planetOne;
            this.planetTwo = planetTwo;
            this.cost = cost;
        }

        @Override public int compareTo(Edge o) 
	{
            return Integer.compare(this.cost, o.cost);
        }
        
	@Override public String toString() 
	{
            return "(" + planetOne + ", " + planetTwo + ", " + cost + ")";
        }
    }

    private static class DisjointUnionKruskal 
    {
        int[] p;
        int[] rank;

        DisjointUnionKruskal(int n) 
	{
            p = new int[n];
            rank = new int[n];
            for(int i = 0; i < n; i++) p[i] = i;
        }
        
	int find(int x) 
	{
            if(p[x] != x) 
	    {
                p[x] = find(p[x]);
            }
            
	    return p[x];
        }

        boolean union(int a, int b) 
	{
            int ra = find(a), rb = find(b);

            if(ra == rb) return false;

            if(rank[ra] < rank[rb]) 
	    {
                p[ra] = rb;
            }

	    else if(rank[ra] > rank[rb]) 
	    {
                p[rb] = ra;
            }

	    else 
	    {
                p[rb] = ra;
                rank[ra]++;
            }

            return true;
        }
    }

    private List<Edge> edges = new ArrayList<>();

    private Map<String,Integer> id = new HashMap<>();

    public Patrol()  
    { 
	    fileInput("patrol.txt"); 
    }

    public Patrol(String fileName) 
    {
	    fileInput(fileName); 
    }

    public void patrolEdges() 
    {
        if(edges.isEmpty()) 
	{
            System.out.println("No edges");
            return;
        }

        int n = id.size();
        DisjointUnionKruskal dsu = new DisjointUnionKruskal(n);
        List<Edge> spanningTree = new ArrayList<>();
        int total = 0;

        Collections.sort(edges);

        for(Edge e : edges) 
	{
            int a = id.get(e.planetOne);
            int b = id.get(e.planetTwo);

            if(dsu.union(a, b))
	    {  
                spanningTree.add(e);
                total += e.cost;
                if(spanningTree.size() == n - 1) break;
            }
        }

        if(spanningTree.size() != n - 1) 
	{
            System.out.println("Graph is disconnected");
            return;
        }

        System.out.println("Total Cost: " + total);

        for(int i = 0; i < spanningTree.size(); i++)
	{
            System.out.print(spanningTree.get(i));

            if(i < spanningTree.size() - 1) System.out.print(" ");
        }

        System.out.println();
    }

    private void fileInput(String fileName) 
    {
        Scanner sc = null;

        try 
	{
            sc = new Scanner(new File(fileName));

            while (sc.hasNext()) 
	    {
                String a = sc.next();
                String b = sc.next();
                int w = sc.nextInt();
                edges.add(new Edge(a, b, w));
                id.computeIfAbsent(a, k -> id.size());
                id.computeIfAbsent(b, k -> id.size());
            }
        } 

	catch (FileNotFoundException e) 
	{
            System.err.println("Could not open " + fileName + ": " + e.getMessage());
        }

	finally 

	{
            if(sc != null) sc.close();
        }
    }
}
