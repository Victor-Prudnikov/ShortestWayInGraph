package com.epam.victorprudnikov.graph.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.epam.victorprudnikov.graph.repository.GraphTable;

import lombok.Data;

@Data
public class Graph {
	
	private String name;
	private Set<String> vertexes;
	private List<Edge> edges;
	
	public Graph(List<GraphTable> listOfGraphs) {
		int listOfGraphsSize = listOfGraphs.size();
		this.name = listOfGraphs.get(0).getGraphName();
		
		Set<String> vertexes1 = new HashSet<>();
		List<Edge> edges1 = new ArrayList<>();
		for (int i = 0; i < listOfGraphsSize; i++) {
			vertexes1.add(listOfGraphs.get(i).getVertexFrom());
			vertexes1.add(listOfGraphs.get(i).getVertexTo());
			Edge edge = new Edge(listOfGraphs.get(i).getVertexFrom(), listOfGraphs.get(i).getVertexTo(), listOfGraphs.get(i).getWeight());
			edges1.add(edge);
		}
		this.vertexes = vertexes1;
		this.edges = edges1;
		
	}
}
