package com.epam.victorprudnikov.graph.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class GraphTable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "graph_name")
	private String graphName;

	@Column(name = "vertex_from")
	private String vertexFrom;

	@Column(name = "vertex_to")
	private String vertexTo;

	@Column(name = "weight")
	private int weight;

	public GraphTable(String graphName, String vertexFrom, String vertexTo, int weight) {
		this.graphName = graphName;
		this.vertexFrom = vertexFrom;
		this.vertexTo = vertexTo;
		this.weight = weight;
	}
}
