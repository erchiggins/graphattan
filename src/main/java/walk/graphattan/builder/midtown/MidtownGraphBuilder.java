package walk.graphattan.builder.midtown;

import walk.graphattan.builder.GraphBuilder;
import walk.graphattan.exception.InvalidLocationException;
import walk.graphattan.graph.CityGraph;
import walk.graphattan.graph.Edge;
import walk.graphattan.graph.EdgeType;
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
 * the order of construction. "Initial" is used in reference to construction order.
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
	// street and avenue numbers of i_init
	private int st_init;
	private int ave_init;
	// most recently constructed street
	private int st_current;
	// most recently constructed avenue
	private int ave_current;
	// x,y coordinates of first vertex to be constructed
	private double x_init;
	private double y_init;

	// number of Vertices in horizontal (E-W) direction
	private int e_w_vertices;
	// number of Vertices in vertical (N-S) direction
	private int n_s_vertices;

	// graph representing city streets between starting point and finishing point
	private CityGraph cityGraph = new CityGraph();

	public void setStart(int start_st, int start_ave, CornerDesc start_corner) throws InvalidLocationException {
		if (start_st > 0 && start_ave > 0 && start_corner != null) {
			this.start_st = start_st;
			this.start_ave = start_ave;
			this.start_corner = start_corner;
			i_start = new Intersection();
			i_start.setDescription(start_ave + " Ave and " + start_st + " St");
			i_start.addRoute(start_ave + " Ave");
			i_start.addRoute(start_st + " St");
		} else {
			throw new InvalidLocationException("invalid starting point");
		}
	}

	public void setFinish(int finish_st, int finish_ave, CornerDesc finish_corner) throws InvalidLocationException {
		if (finish_st > 0 && finish_ave > 0 && finish_corner != null) {
			this.finish_st = finish_st;
			this.finish_ave = finish_ave;
			this.finish_corner = finish_corner;
			i_finish = new Intersection();
			i_finish.setDescription(finish_ave + " Ave and " + finish_st + " St");
			i_finish.addRoute(finish_ave + " Ave");
			i_finish.addRoute(finish_st + " St");
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

	// return Intersection from which construction will begin, and set where on the
	// CityGraph
	// the Intersection of the starting location for the traversal (c_i_start) is
	// located (NE, NW, SE, SW)
	// construction will always begin with the SW intersection
	// also sets other initial construction values for street, avenue, and xy coords
	public Intersection findSWIntersection() {
		if (start_st <= finish_st) {
			// start_st is South of finish_st (i_init is on start_st)
			if (start_ave >= finish_ave) {
				// i_start is SW intersection
				i_init = i_start;
				st_init = start_st;
				ave_init = start_ave;
				c_i_start = CornerDesc.SW;
			} else {
				// i_start is SE intersection
				i_init = new Intersection();
				i_init.setDescription(finish_ave + " Ave and " + start_st + " St");
				i_init.addRoute(finish_ave + " Ave");
				i_init.addRoute(start_st + " St");
				st_init = start_st;
				ave_init = finish_ave;
				c_i_start = CornerDesc.SE;
			}
		} else {
			// start_st is North of finish_st (i_init is on finish_st)
			if (start_ave >= finish_ave) {
				// i_finish is SE intersection
				i_init = new Intersection();
				i_init.setDescription(start_ave + " Ave and " + finish_st + " St");
				i_init.addRoute(start_ave + " Ave");
				i_init.addRoute(finish_st + " St");
				st_init = finish_st;
				ave_init = start_ave;
				c_i_start = CornerDesc.NW;
			} else {
				// i_finish is SW intersection
				i_init = i_finish;
				st_init = finish_st;
				ave_init = finish_ave;
				c_i_start = CornerDesc.NE;
			}

		}
		// setup for graph construction
		st_current = st_init;
		ave_current = ave_init;
		x_init = calculateXInit();
		y_init = calculateYInit();
		return i_init;
	}

	// represents the farthest offset to the West which any Vertex has relative to
	// the starting location
	public double calculateXInit() {
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
			// check location of start within its intersection
			if (start_corner == CornerDesc.SW || start_corner == CornerDesc.NW) {
				x_pos = -(c_ave + w_ave) * Math.abs(start_ave - finish_ave);
			} else {
				// initial vertex is offset as far as possible
				x_pos = -((c_ave + w_ave) * Math.abs(start_ave - finish_ave) + c_ave);
			}
		}
		return x_pos;
	}

	// represents the farthest offset to the South which any Vertex has relative to
	// the starting location
	public double calculateYInit() {
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
			// check location of start within its intersection
			if (start_corner == CornerDesc.SW || start_corner == CornerDesc.SE) {
				y_pos = -(c_st + w_st) * Math.abs(start_st - finish_st);
			} else {
				// initial vertex is offset as far as possible
				y_pos = -((c_st + w_st) * Math.abs(start_st - finish_st) + c_st);
			}
		}
		return y_pos;
	}

	@Override
	public CityGraph build() {
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
		for (int i = 1; i < n_s_vertices; i++) {
			isNorth = !isNorth;
			latest = addHorizontal(latest, isNorth);
		}
		return this.cityGraph;
	}

	// starting from the NW vertex of current cityGraph
	// northSouth indicates whether this horizontal is the North or South side of a
	// Street (true=North, false=South)
	public Vertex[] addHorizontal(Vertex[] old_horizontal, boolean north) {
		// old_horizontal==null should always occur with north==false, otherwise GTFO
		if (old_horizontal == null && north)
			return null;
		// Array of new Vertices to be returned
		Vertex[] new_horizontal = new Vertex[this.e_w_vertices];
		for (int i = 0; i < this.e_w_vertices; i++) {
			Vertex v_next = new Vertex();
			// what type of corner is v_next?
			CornerDesc corner = null;
			// determine x position
			if (i == 0) {
				v_next.setPosX(x_init);
			} else {
				double x_offset = (i % 2 == 0) ? w_ave : c_ave;
				v_next.setPosX(new_horizontal[i - 1].getPosX() + x_offset);
			}
			// determine y position
			if (old_horizontal != null) {
				if (north) {
					// across a street from the last horizontal
					v_next.setPosY(old_horizontal[i].getPosY() + c_st);
				} else {
					// a block north from the last horizontal
					v_next.setPosY(old_horizontal[i].getPosY() + w_st);
				}
			} else {
				v_next.setPosY(y_init);
			}
			// determine intersection
			if (i % 2 == 0) {
				// western side of an avenue, need to update ave_current
				ave_current = ave_init - i/2;
				if (north) {
					// in same intersection as old_horizontal vertex in same position
					// begin first horizontal on south side, so safe from NullPointerExceptions
					v_next.setIntersection(old_horizontal[i].getIntersection());
					// v_next is NW corner of intersection
					corner = CornerDesc.NW;
				} else {
					// create new intersection
					if (i == 0 && st_current == st_init) {
						// case of very first vertex to be added
						v_next.setIntersection(i_init);
					} else {
						Intersection i_next = new Intersection();
						i_next.setDescription(ave_current + " Ave and " + st_current + " St");
						i_next.addRoute(ave_current + " Ave");
						i_next.addRoute(st_current + " St");
						v_next.setIntersection(i_next);
					}
					// v_next is SW corner of intersection
					corner = CornerDesc.SW;
				}
			} else {
				// eastern side of an avenue, will have "passed through" construction of
				// intersection already regardless of whether north or south side of street
				v_next.setIntersection(new_horizontal[i - 1].getIntersection());
				if (north) {
					// v_next is NE corner of intersection
					corner = CornerDesc.NE;
				} else {
					// v_next is SE corner of intersection
					corner = CornerDesc.SE;
				}
			}
			// add to CityGraph
			cityGraph.addVertex(v_next);
			// check whether v_next is a starting or finishing vertex
			if (st_current == start_st && ave_current == start_ave && corner.equals(start_corner)) {
				cityGraph.setStart(v_next);
			} else if (st_current == finish_st && ave_current == finish_ave && corner.equals(finish_corner)) {
				cityGraph.setFinish(v_next);
			}
			// add to new_horizontal
			new_horizontal[i] = v_next;

			// if not at border of graph..
			// add edge between new_horizontal[i-1] and new_horizontal[i]
			if (i != 0) {
				Edge e_east_west = new Edge();
				e_east_west.setSource(new_horizontal[i - 1]);
				e_east_west.setDestination(new_horizontal[i]);
				if (i % 2 != 0) {
					// e_east_west is an avenue crossing
					e_east_west.setType(EdgeType.CROSSING);
					e_east_west.setWeight(c_ave);
				} else {
					// e_east_west is a sidewalk between avenues
					e_east_west.setType(EdgeType.SIDEWALK);
					e_east_west.setWeight(w_ave);
				}
				cityGraph.addEdge(e_east_west);
			}
			// if old_horizontal is not null..
			// add edge between old_horizontal[i] and new_horizontal[i]
			if (old_horizontal != null) {
				Edge e_north_south = new Edge();
				e_north_south.setSource(old_horizontal[i]);
				e_north_south.setDestination(new_horizontal[i]);
				if (north) {
					// e_north_south is a street crossing
					e_north_south.setType(EdgeType.CROSSING);
					e_north_south.setWeight(c_st);
				} else {
					// e_north_south is a sidewalk between streets
					e_north_south.setType(EdgeType.SIDEWALK);
					e_north_south.setWeight(w_st);
				}
				cityGraph.addEdge(e_north_south);
			}
		}
		// update st_current ONLY IF north
		if (north) {
			// we're done with this street, next horizontal will be one higher
			st_current++;
		}
		return new_horizontal;
	}

}
