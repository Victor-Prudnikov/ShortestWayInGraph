package com.epam.victorprudnikov.graph.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GraphRepository extends JpaRepository<GraphTable, Long> {

	@Query("select distinct graphName from GraphTable")
	List<String> viewListOfGraphs();
	
	@Query("select g from GraphTable g where g.graphName=?1")
	List<GraphTable> selectGraph(String name);

}
