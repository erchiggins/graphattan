package walk.graphattan.builder.midtown;

import java.util.ArrayList;
import java.util.List;

import walk.graphattan.builder.GraphBuilder;
import walk.graphattan.exception.InvalidLocationException;
import walk.graphattan.graph.CityGraph;
import walk.graphattan.graph.Intersection;
import walk.graphattan.graph.Vertex;

/*
 * The MidtownGraphBuilder defines CityGraph construction for a uniform, rectangular grid
 * with spacing derived from New York City's street and avenue structure.
 * Streets are numbered >= 1 and run east-west, increasing in number from south to north. 
 * Avenues are numbered >= 1 and run north-south, increasing in number from east to west. 
 * There is no maximum value on street or avenue numbers. 
 * Intersections have four Vertices, at their NE, NW, SE, and SW corners. 
 * Note that "start" and "finish" are used in reference to desired traversal pattern, not 
 * the order of construction.
 */
public class MidtownGraphBuilder extends GraphBuilder {

	public MidtownGraphBuilder() {
		super();
	}

	// length in feet of an avenue crossing
	private final double c_ave = 100.0;
	// length in feet of a street crossing c_st
	private final double c_st = 60.0;
	// length in feet of a walkway between avenues w_ave
	private final double w_ave = 800.0;
	// length in feet of a walkway between streets w_st
	private final double w_st = 200.0;

	// location of starting point
	private int start_st;
	private int start_ave;
	// refers to corner within Intersection
	private CornerDesc start_corner;

	// location of finishing point
	private int finish_st;
	private int finish_ave;
	// refers to corner within Intersection
	private CornerDesc finish_corner;

	// starting Intersection
	private Intersection i_start;
	// starting Vertex within that Intersection
	private Vertex v_start;
	// which corner of map i_start occupies
	private CornerDesc c_i_start;
	// finishing Intersection
	private Intersection i_finish;
	// finishing Vertex within that Intersection
	private Vertex v_finish;

	// Intersection at SW corner of map, used to begin construction
	private Intersection i_init;

	// number of Vertices in horizontal (E-W) direction
	private int e_w_vertices;
	// number of Vertices in vertical (N-S) direction
	private int n_s_vertices;

	// graph representing city streets between starting point and finishing point
	private CityGraph cityGraph;

	public void setStart(int start_st, int start_ave, CornerDesc start_corner) throws InvalidLocationException {
		if (start_st > 0 && start_ave > 0 && start_corner != null) {
			this.start_st = start_st;
			this.start_ave = start_ave;
			this.start_corner = start_corner;
		} else {
			throw new InvalidLocationException("invalid starting point");
		}
	}

	public void setFinish(int finish_st, int finish_ave, CornerDesc finish_corner) throws InvalidLocationException {
		if (finish_st > 0 && finish_ave > 0 && finish_corner != null) {
			this.finish_st = finish_st;
			this.finish_ave = finish_ave;
			this.finish_corner = finish_corner;
		} else {
			throw new InvalidLocationException("invalid finishing point");
		}
	}

	public int calculateEastWestVertices() {
		this.e_w_vertices = 2 * (Math.abs(this.finish_ave - this.start_ave) + 1);
		return this.e_w_vertices;
	}

	public int calculateNorthSouthVertices() {
		this.n_s_vertices = 2 * (Math.abs(this.finish_st - this.start_st) + 1);
		return this.n_s_vertices;
	}

	private Intersection findSWIntersection() {
		Intersection i_init = null;
		if (start_st <= finish_st) {
			// start_st is South of finish_st (i_init is on start_st)
			if (start_ave >= finish_ave) {
				// i_start is SW corner
				i_init = i_start;
				c_i_start = CornerDesc.SW;
			} else {
				// i_start is SE corner
				i_init = new Intersection();
				i_init.setDescription(finish_ave + " Ave and " + start_st + " St");
				i_init.addRoute(finish_ave + " Ave");
				i_init.addRoute(start_st + " St");
				c_i_start = CornerDesc.SE;
			}
		} else {
			// start_st is North of finish_st (i_init is on finish_st)
			if (start_ave >= finish_ave) {
				// i_finish is SE Corner
				i_init = new Intersection();
				i_init.setDescription(start_ave + " Ave and " + finish_st + " St");
				i_init.addRoute(start_ave + " Ave");
				i_init.addRoute(finish_st + " St");
				c_i_start = CornerDesc.NW;
			} else {
				// i_finish is SW corner
				i_init = i_finish;
				c_i_start = CornerDesc.NE;
			}
		}
		return i_init;
	}

	// represents the farthest offset to the West which any Vertex has relative to the starting location
	private double calculateXInit() {
		double x_pos = 0.0;
		if (c_i_start == CornerDesc.SW || c_i_start == CornerDesc.NW) {
			// starting location is on West side of graph, like initial vertex
			// now check location of start within its intersection
			if (start_corner == CornerDesc.SW || start_corner == CornerDesc.NW) {
				// initial vertex is at same x-value as starting location
				x_pos = 0.0;
			} else {
				// initial vertex is offset by one avenue crossing width
				x_pos = -c_ave;
			}
		} else {
			// starting location is on East side of graph, opposite initial vertex
			//check location of start within its intersection
			if (start_corner == CornerDesc.SW || start_corner == CornerDesc.NW) {
				x_pos = -(c_ave+w_ave)*Math.abs(start_ave-finish_ave);
			} else {
				// initial vertex is offset as far as possible
				x_pos = -((c_ave+w_ave)*Math.abs(start_ave-finish_ave)+c_ave);
			}
		}
		return x_pos;
	}

	// represents the farthest offset to the South which any Vertex has relative to the starting location
	private double calculateYInit() {
		double y_pos = 0.0;
		if (c_i_start == CornerDesc.SW || c_i_start == CornerDesc.SE) {
			// starting location is on South side of graph, like initial vertex
			// now check location of start within its intersection
			if (start_corner == CornerDesc.SW || start_corner == CornerDesc.SE) {
				// initial vertex is at same y-value as starting location
				y_pos = 0.0;
			} else {
				// initial vertex is offset by one street crossing width
				y_pos = -c_st;
			}
		} else {
			// starting location is on North side of graph, opposite initial vertex
			//check location of start within its intersection
			if (start_corner == CornerDesc.SW || start_corner == CornerDesc.SE) {
				y_pos = -(c_st+w_st)*Math.abs(start_st-finish_st);
			} else {
				// initial vertex is offset as far as possible
				y_pos = -((c_st+w_st)*Math.abs(start_st-finish_st)+c_st);
			}
		}
		return y_pos;
	}

	@Override
	public CityGraph build() {
		this.cityGraph = new CityGraph();
		// create i_start, i_finish
		i_start = new Intersection();
		i_start.setDescription(start_ave + " Ave and " + start_st + " St");
		i_start.addRoute(start_ave + " Ave");
		i_start.addRoute(start_st + " St");
		i_finish = new Intersection();
		i_finish.setDescription(finish_ave + " Ave and " + finish_st + " St");
		i_finish.addRoute(finish_ave + " Ave");
		i_finish.addRoute(finish_st + " St");
		// calculate number of vertices from S-N (including full Intersections, not just
		// start/finish corners)
		calculateNorthSouthVertices();
		// calculate number of vertices from E-W (including full Intersections, not just
		// start/finish corners)
		calculateEastWestVertices();
		// determine which intersection is starting point for construction
		i_init = findSWIntersection();
		// add horizontals, stacking S-N and starting with SW corner of starting
		// Intersection
		// first horizontal (0)
		boolean isNorth = false;
		Vertex[] latest = addHorizontal(null, isNorth);
		// horizontals 1-n_s_vertices-1
		for (int i=1; i<n_s_vertices-1; i++) {
			isNorth = !isNorth;
			latest = addHorizontal(latest, isNorth);
		}
		return this.cityGraph;
	}

	// starting from the NW vertex of current cityGraph
	// northSouth indicates whether this horizontal is the North or South side of a
	// Street (true=North, false=South)
	private Vertex[] addHorizontal(Vertex[] old_horizontal, boolean north) {
		// Array of new Vertices to be returned
		Vertex[] new_horizontal = new Vertex[this.e_w_vertices];
		// next Vertex to be added
		Vertex v_next = new Vertex();

		// check if old_horizontal is null, indicates first horizontal
		if (old_horizontal != null) {
			// TODO
			// add vertex directly to the north of old_horizontal[0]
			// determine location and intersection of v_next
			// add v_next to cityGraph
			// set v_next as new_horizontal[0]
			new_horizontal[0] = v_next;
			// add edge between old_horizontal[0] and new_horizontal[0]

			// iteratively add new Vertices to the farthest Eastern extent of this
			// horizontal
			// for each
			// assign v_next to new Vertex
			// determine location and intersection of v_next
			// add v_next to cityGraph
			// add v_next to new_horizontal[i]
			// add edge between new_horizontal[i-1] and new_horizontal[i]
			// add edge between old_horizontal[i] and new_horizontal[i]

		} else {
			// TODO
			// create starting vertex (SW corner of i_init)
			Vertex v_init = new Vertex(calculateXInit(), calculateYInit(), i_init);
		}

		return new_horizontal;
	}

}
