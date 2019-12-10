package walk.graphattan.graph;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	void initializeVertices() {
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
		cityGraph.addVertex(v1);
		cityGraph.addVertex(v2);
		cityGraph.addVertex(v3);
		cityGraph.addVertex(v4);
		// link v1 and v2
		e1 = new Edge(100.0, EdgeType.CROSSING, v1, v2);
		// link v2 and v3
		e2 = new Edge(60.0, EdgeType.CROSSING, v2, v3);
		// link v3 and v4
		e3 = new Edge(100.0, EdgeType.CROSSING, v3, v4);
		// link v4 and v1
		e4 = new Edge(60.0, EdgeType.CROSSING, v4, v1);
		cityGraph.addEdge(e1);
		cityGraph.addEdge(e2);
		cityGraph.addEdge(e3);
		cityGraph.addEdge(e4);
	}

	@Test
	void testAddSimpleVertex() {
		assertTrue(cityGraph.addVertex(new Vertex(2, 3, i1)));
	}

	@Test
	void testAddNullVertex() {
		assertFalse(cityGraph.addVertex(null));
	}

	@Test
	void testAddDuplicateLocationVertex() {
		Vertex v = new Vertex(100, 60, null);
		assertFalse(cityGraph.addVertex(v));
	}

	@Test
	void testAddSimpleEdge() {
		// imagine this a special pedestrian bridge over 6th Ave.
		Edge e5 = new Edge(110.0, EdgeType.SIDEWALK, v1, v4);
		assertTrue(cityGraph.addEdge(e5));
		// double check that new edge is part of both vertices' lists
		// source to destination
		assertTrue(cityGraph.getGraph().get(v1).contains(e5));
		// and destination to source
		Edge e6 = new Edge(110.0, EdgeType.SIDEWALK, v4, v1);
		assertTrue(cityGraph.getGraph().get(v4).contains(e6));
	}

	@Test
	void testAddNullEdge() {
		assertFalse(cityGraph.addEdge(null));
	}
	
	@Test
	void testAddNegativeWeightEdge() {
		assertFalse(cityGraph.addEdge(new Edge(-100.0, EdgeType.SIDEWALK, v1, v4)));
	}
	
	@Test
	void testAddEdgeInvalidSource() {
		assertFalse(cityGraph.addEdge(new Edge(100.0, EdgeType.CROSSING, new Vertex(3, 5, i1), v3)));
	}
	
	@Test
	void testAddEdgeInvalidDestination() {
		assertFalse(cityGraph.addEdge(new Edge(100.0, EdgeType.CROSSING, v3, new Vertex(3, 5, i1))));
	}
	
	@Test
	void testAddEdgeNullSource() {
		assertFalse(cityGraph.addEdge(new Edge(100.0, EdgeType.CROSSING, null, v3)));
	}
	
	@Test
	void testAddEdgeNullDestination() {
		assertFalse(cityGraph.addEdge(new Edge(100.0, EdgeType.CROSSING, v3, null)));
	}

}
