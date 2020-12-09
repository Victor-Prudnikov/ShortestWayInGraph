Shortest Way In Graph
===================

This is a training project, which helps to find the shortest way between two vertexes of the graph. The project uses Spring Boot and implements Dijkstra's algorithm. By default there are 3 graphs in the database, which are illustrated by pictures Graph1.png, Graph2.jpg and Graph3.jpg. There are three controllers:  
1) **/graph-view** (with parameter *name*) - can view list of available graphs from the database or list of the edges of selected graph;  
2) **/graph-add** (with parameters *name, from, to, weight*) - can add new edge of the graph to the database (no more than 50 vertexes and 50 edges in one graph);  
3) **/graph-dijkstra** (with parameters *name, from, to*) - can find the shortest way between vertexes *from* and *to*.  

Author - Victor Prudnikov (december 2020).