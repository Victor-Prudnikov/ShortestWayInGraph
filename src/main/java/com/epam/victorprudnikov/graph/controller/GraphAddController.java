package com.epam.victorprudnikov.graph.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.victorprudnikov.graph.model.Graph;
import com.epam.victorprudnikov.graph.repository.GraphRepository;
import com.epam.victorprudnikov.graph.repository.GraphTable;
import com.epam.victorprudnikov.graph.util.AdditionalMethods;

@RestController
@RequestMapping("/graph-add")
public class GraphAddController {

	@Autowired
	GraphRepository graphRepository;

	@Autowired
	AdditionalMethods additionalMethods;

	@GetMapping
	public String graphAdd(@RequestParam(required = false) String name, @RequestParam(required = false) String from,
			@RequestParam(required = false) String to, @RequestParam(required = false) String weight) {
		try {
			List<String> listOfGraphs = graphRepository.viewListOfGraphs();
			if (name == null) {
				if (listOfGraphs.isEmpty()) {
					return "There are no available graphs in the database.";
				} else {
					return additionalMethods.formingAnswerListOfGraphs(listOfGraphs);
				}
			} else {
				if ((from == null) || (to == null) || (from.equals(to)) || (weight == null)) {
					return "You must fill correct Name of graph, VertexFrom, VertexTo and Weight of edge.";
				} else {
					if (listOfGraphs.contains(name)) {
						Graph graph = new Graph(graphRepository.selectGraph(name));
						Set<String> vertexes = graph.getVertexes();
						vertexes.add(from);
						vertexes.add(to);
						if (vertexes.size() > 50) {
							return "Graph " + name + " already contains maximum vertexes.";
						}
						if (graph.getEdges().size() > 49) {
							return "Graph " + name + " already contains maximum edges.";
						}
					}
					GraphTable graphTable = new GraphTable(name, from, to, Integer.parseInt(weight));
					graphRepository.save(graphTable);
					return "New edge of the graph was succesfully saved.";
				}
			}
		} catch (NumberFormatException e) {
			return "Incorrect weight of the edge.";
		} catch (Exception e) {
			return "There is some error: " + e.toString();
		}
	}
}
