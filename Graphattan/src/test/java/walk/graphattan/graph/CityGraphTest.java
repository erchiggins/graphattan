package walk.graphattan.graph;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class CityGraphTest {

	private Intersection i1;
	private Vertex v1;
	private Vertex v2;
	private Vertex v3;
	private Vertex v4;
	private Edge e1;
	private Edge e2;
	private Edge e3;
	private Edge e4;

	private CityGraph cityGraph = new CityGraph();

	/*
	 * Constructs single Intersection with four Vertices linked by four Edges
	 */
	@BeforeEach
	void initializeVertices(TestInfo info) {
		i1 = new Intersection("Tesla Corner");
		i1.addRoute("40th St");
		i1.addRoute("6th Ave");
		// add SW corner of intersection as "origin" vertex
		v1 = new Vertex(0, 0, i1);
		// add SE corner of intersection
		v2 = new Vertex(100, 0, i1);
		// add NE corner of intersection
		v3 = new Vertex(100, 60, i1);
		// add NW corner of intersection
		v4 = new Vertex(0, 60, i1);
		// link v1 and v2
		e1 = new Edge(100.0, EdgeType.CROSSING, v1, v2);
		// link v2 and v3
		e2 = new Edge(60.0, EdgeType.CROSSING, v2, v3);
		// link v3 and v4
		e1 = new Edge(100.0, EdgeType.CROSSING, v3, v4);
		// link v4 and v1
		e1 = new Edge(60.0, EdgeType.CROSSING, v4, v1);
		if (info.getDisplayName().equals("testAddSimpleVertex")) {
			return;
		} else {
			cityGraph.addVertex(v2);
			cityGraph.addVertex(v3);
			cityGraph.addVertex(v4);
		}
	}

	@Test
	@Order(1)
	void testAddSimpleVertex() {
		assertTrue(cityGraph.addVertex(v1));
		// if no failure, add remaining vertices
	}
	
	// add null vertex
	// add duplicate location vertex

	@Test
	void testAddEdge() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAdjacent() {
		fail("Not yet implemented");
	}

}
