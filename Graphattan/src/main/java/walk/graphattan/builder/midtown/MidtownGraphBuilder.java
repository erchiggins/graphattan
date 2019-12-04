package walk.graphattan.builder.midtown;

import walk.graphattan.builder.GraphBuilder;
import walk.graphattan.exception.InvalidLocationException;
import walk.graphattan.graph.CityGraph;

/*
 * The MidtownGraphBuilder defines CityGraph construction for a uniform, rectangular grid
 * with spacing derived from New York City's street and avenue structure.
 * Streets are numbered >= 1 and run east-west, increasing in number from south to north. 
 * Avenues are numbered >= 1 and run north-south, increasing in number from east to west. 
 * There is no maximum value on street or avenue numbers. 
 * Intersections have four Vertices, at their NE, NW, SE, and SW corners. 
 */
public class MidtownGraphBuilder implements GraphBuilder {

	public MidtownGraphBuilder() {
		super();
	}

	// length in feet of an avenue crossing //TODO
	private final double c_ave = 100.0;
	// length in feet of a street crossing c_st
	// length in feet of a walkway between avenues w_ave
	// length in feet of a walkway between streets w_st

	// location of starting point
	private int start_st;
	private int start_ave;
	private CornerDesc start_corner;

	// location of finishing point
	private int finish_st;
	private int finish_ave;
	private CornerDesc finish_corner;

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

	@Override
	public CityGraph build() {
		// TODO Auto-generated method stub
		return null;
	}

}
