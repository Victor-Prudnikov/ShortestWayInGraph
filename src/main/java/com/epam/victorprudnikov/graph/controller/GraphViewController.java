package com.epam.victorprudnikov.graph.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.victorprudnikov.graph.model.Graph;
import com.epam.victorprudnikov.graph.repository.GraphRepository;
import com.epam.victorprudnikov.graph.util.AdditionalMethods;

@RestController
@RequestMapping("/graph-view")
public class GraphViewController {

	@Autowired
	GraphRepository graphRepository;
	
	@Autowired
	AdditionalMethods additionalMethods;
	
	@GetMapping
	public String graphView(@RequestParam(required = false) String name) {
		try {
			List<String> listOfGraphs = graphRepository.viewListOfGraphs();
			if (name == null) {
				if (listOfGraphs.isEmpty()) {
					return "There are no available graphs in the database.";
				}
				else {
					return additionalMethods.formingAnswerListOfGraphs(listOfGraphs);
				}
			}
			else {
				if (listOfGraphs.contains(name)) {
					Graph graph = new Graph(graphRepository.selectGraph(name));
					return graph.getEdges().toString();
				}
				else {
					return additionalMethods.formingAnswerListOfGraphs(listOfGraphs);
				}
			}
		}
		catch (Exception e) {
			return "There is some error: " + e.toString();
		}

	}

}


