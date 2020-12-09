package com.epam.victorprudnikov.graph.model;

import lombok.Data;

@Data
public class Vertex {
	private String name;
	private int label = Integer.MAX_VALUE;
	private boolean isMarked = false;
	private String previous;
	
	public Vertex(String name) {
		this.name = name;
	}
	
}
