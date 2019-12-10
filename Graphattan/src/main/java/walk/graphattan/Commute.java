package walk.graphattan;

import walk.graphattan.builder.midtown.CornerDesc;
import walk.graphattan.builder.midtown.MidtownGraphBuilder;
import walk.graphattan.exception.InvalidLocationException;

public class Commute {

	public static void main(String[] args) {
		// initialize MidtownGraphBuilder with starting and finishing locations - 1st
		// Ave and 49th St, NE corner to 3rd Ave and 53rd St, SE corner
		MidtownGraphBuilder mgb = new MidtownGraphBuilder();
		try {
			mgb.setStart(46, 1, CornerDesc.NE);
			mgb.setFinish(53, 3, CornerDesc.SE);
		} catch (InvalidLocationException e) {
			e.printStackTrace();
		}
		// build()
		// TODO
		// print finished graph to console
		// TODO
	}

}
