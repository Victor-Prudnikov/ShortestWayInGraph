package com.epam.victorprudnikov.graph.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.victorprudnikov.graph.model.Edge;
import com.epam.victorprudnikov.graph.model.Graph;
import com.epam.victorprudnikov.graph.model.Vertex;
import com.epam.victorprudnikov.graph.repository.GraphRepository;
import com.epam.victorprudnikov.graph.util.AdditionalMethods;

@RestController
@RequestMapping("/graph-dijkstra")
public class GraphDijkstraController {

	private static final int MAX = Integer.MAX_VALUE;

	@Autowired
	GraphRepository graphRepository;

	@Autowired
	AdditionalMethods additionalMethods;

	@GetMapping
	public String shortestWayDijkstra(@RequestParam(required = false) String name,
			@RequestParam(required = false) String from, @RequestParam(required = false) String to) {
		try {
			List<String> listOfGraphs = graphRepository.viewListOfGraphs();
			if (name == null) {
				if (listOfGraphs.isEmpty()) {
					return "There are no available graphs in the database.";
				} else {
					return additionalMethods.formingAnswerListOfGraphs(listOfGraphs);
				}
			} else {
				if ((from == null) || (to == null)) {
					return "You must fill correct Name of graph, VertexFrom and VertexTo.";
				} else {
					if (!listOfGraphs.contains(name)) {
						return "You must fill correct Name of graph";
					} else {
						Graph graph = new Graph(graphRepository.selectGraph(name));
						Set<String> vertexesNames = graph.getVertexes();
						if ((!vertexesNames.contains(from)) || (!vertexesNames.contains(to))) {
							return "You must fill correct VertexFrom and VertexTo.";
						} else {
							List<Edge> edges = graph.getEdges();
							int edgesListSize = edges.size();
							int vertexTempNumber;
							List<Vertex> vertexes = new ArrayList<>();
							for (String vertex : vertexesNames) {
								vertexes.add(new Vertex(vertex));
							}
							String minVertex = from;
							int minDist = 0;
							int vertexFromNumber = getVertexNumberByName(vertexes, from);
							int vertexToNumber = getVertexNumberByName(vertexes, to);
							Vertex vertexTemp = vertexes.get(vertexFromNumber);
							vertexTemp.setLabel(0);
							vertexes.set(vertexFromNumber, vertexTemp);

							// Main part of Dijkstra's algorithm
							while ((minDist < MAX) && (!vertexes.get(vertexToNumber).isMarked())) {
								for (int i = 0; i < edgesListSize; i++) {
									if (edges.get(i).getFrom().equals(minVertex)) {
										int neighbor = getVertexNumberByName(vertexes, edges.get(i).getTo());
										int neighborWeight = minDist + edges.get(i).getWeight();
										if ((!vertexes.get(neighbor).isMarked())
												&& (neighborWeight < vertexes.get(neighbor).getLabel())) {
											vertexTemp = vertexes.get(neighbor);
											vertexTemp.setLabel(neighborWeight);
											vertexTemp.setPrevious(minVertex);
											vertexes.set(neighbor, vertexTemp);
										}
									} else {
										if (edges.get(i).getTo().equals(minVertex)) {
											int neighbor = getVertexNumberByName(vertexes, edges.get(i).getFrom());
											int neighborWeight = minDist + edges.get(i).getWeight();
											if ((!vertexes.get(neighbor).isMarked())
													&& (neighborWeight < vertexes.get(neighbor).getLabel())) {
												vertexTemp = vertexes.get(neighbor);
												vertexTemp.setLabel(neighborWeight);
												vertexTemp.setPrevious(minVertex);
												vertexes.set(neighbor, vertexTemp);
											}
										}
									}
								}
								vertexTempNumber = getVertexNumberByName(vertexes, minVertex);
								vertexTemp = vertexes.get(vertexTempNumber);
								vertexTemp.setMarked(true);
								vertexes.set(vertexTempNumber, vertexTemp);
								minVertex = findMinVertex(vertexes);
								vertexTempNumber = getVertexNumberByName(vertexes, minVertex);
								vertexTemp = vertexes.get(vertexTempNumber);
								minDist = vertexTemp.getLabel();
							}

							// Forming answer
							if (vertexes.get(vertexToNumber).getLabel() == MAX) {
								return "Two vertexes are not connected.";
							} else {
								StringBuilder answer = new StringBuilder("Shortest distance between two graphs is ");
								answer.append(vertexes.get(vertexToNumber).getLabel());
								answer.append(". Shortest way is: ");
								List<String> tempListString = new ArrayList<>();
								tempListString.add(to);
								String tempVertexName = to;
								while (!tempVertexName.equals(from)) {
									vertexTempNumber = getVertexNumberByName(vertexes, tempVertexName);
									tempVertexName = vertexes.get(vertexTempNumber).getPrevious();
									tempListString.add(tempVertexName);
								}
								int tempListStringSize = tempListString.size();
								for (int i = tempListStringSize - 1; i >= 0; i--) {
									answer.append(tempListString.get(i));
									answer.append("; ");
								}
								return answer.toString();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			return "There is some error: " + e.toString();
		}
	}
	
	//Method returns an index of the vertex in the list of vertexes by its name.
	int getVertexNumberByName(List<Vertex> vertexes, String name) {
		int size = vertexes.size();
		for (int i = 0; i < size; i++) {
			if (vertexes.get(i).getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}
	
	//Method finds an unmarked vertex with a minimum label in the graph.
	String findMinVertex(List<Vertex> vertexes) {
		int minVertex = -1;
		int minLabel = 0;
		int size = vertexes.size();
		for (int i = 0; i < size; i++) {
			if ((minVertex == -1) && (!vertexes.get(i).isMarked())) {
				minLabel = vertexes.get(i).getLabel();
				minVertex = i;
			} else {
				if ((vertexes.get(i).getLabel() < minLabel) && (!vertexes.get(i).isMarked())) {
					minLabel = vertexes.get(i).getLabel();
					minVertex = i;
				}
			}
		}
		if (minVertex == -1) {
			return vertexes.get(0).getName();
		} else {
			return vertexes.get(minVertex).getName();
		}
	}
	
	//Method finds all neighbors of the vertex in the list of edges.
	ArrayList<String> findNeighbors(String name, List<Edge> edges) {
		int size = edges.size();
		ArrayList<String> neighbors = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			if (edges.get(i).getTo().equals(name)) {
				neighbors.add(edges.get(i).getFrom());
			}
			if (edges.get(i).getFrom().equals(name)) {
				neighbors.add(edges.get(i).getTo());
			}
		}
		return neighbors;
	}
}
