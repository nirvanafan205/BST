import java.util.*;
import java.io.*;

public class Travel 
{
	private static class Edge 
	{
		String dest;
		int weight;

		Edge(String dest, int weight) 
		{
			this.dest = dest;
			this.weight = weight;
		}
	}

	private final Map<String, List<Edge>> graph;

	public Travel() 
	{
		graph = new HashMap<>();
		loadGraph("travel.txt");
	}

	public Travel(String fileName) 
	{
		graph = new HashMap<>();
		loadGraph(fileName);
	}

	private void loadGraph(String fileName) 
	{
		try (Scanner sc = new Scanner(new File(fileName))) 
		{
			while (sc.hasNext()) 
			{
				String src = sc.next();
				String dest = sc.next();
				int weight = sc.nextInt();

				if(weight < 0) 
				{
					throw new IllegalArgumentException("Edge weights cant be negative: " + src + " -> " + dest);
				}

				graph.computeIfAbsent(src, k -> new ArrayList<>())
					.add(new Edge(dest, weight));
				graph.computeIfAbsent(dest, k -> new ArrayList<>());
			}
		} 

		catch(FileNotFoundException e) 
		{
			System.err.println("Could not open " + fileName + ": " + e.getMessage());
		}
	}

	public void quickTravel(String start, String goal) 
	{
		if(!graph.containsKey(start) || !graph.containsKey(goal)) 
		{
			System.out.println("Unknown planet name");
			return;
		}

		Map<String, Integer> dist = new HashMap<>();
		Map<String, String> prev = new HashMap<>();

		for(String v : graph.keySet()) 
		{
			dist.put(v, Integer.MAX_VALUE);
		}

		dist.put(start, 0);

		PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(dist::get));
		pq.add(start);

		while(!pq.isEmpty()) 
		{
			String u = pq.poll();

			if(u.equals(goal)) break; 

			int d = dist.get(u);

			for(Edge e : graph.get(u)) 
			{
				int alt = d + e.weight;

				if(alt < dist.get(e.dest)) 
				{
					dist.put(e.dest, alt);
					prev.put(e.dest, u);
					pq.remove(e.dest);
					pq.add(e.dest);
				}
			}
		}

		if(dist.get(goal) == Integer.MAX_VALUE) 
		{
			System.out.println("No path exists from " + start + " to " + goal + ".");
			return;
		}

		List<String> path = new LinkedList<>();

		for(String at = goal; at != null; at = prev.get(at)) 
		{
			path.add(0, at);
		}

		System.out.println("Path: " + String.join(", ", path));
		System.out.println("Total Travel Time: " + dist.get(goal));
	}
}
