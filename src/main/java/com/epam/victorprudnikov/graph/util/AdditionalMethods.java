package com.epam.victorprudnikov.graph.util;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AdditionalMethods {
	
	public String formingAnswerListOfGraphs(List<String> listOfGraphs) {
		StringBuilder answerListOfGraphs = new StringBuilder("List of available graphs: ");
		for(int i = 0; i < listOfGraphs.size(); i++) {
			answerListOfGraphs.append(listOfGraphs.get(i)).append("; ");
		}
		return answerListOfGraphs.toString();
	}
}
