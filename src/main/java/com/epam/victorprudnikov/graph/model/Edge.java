package com.epam.victorprudnikov.graph.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Edge {
	private String from;
    private String to;
    private int weight;
}
