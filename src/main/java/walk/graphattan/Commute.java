package walk.graphattan;

import java.util.Iterator;

import walk.graphattan.builder.midtown.CornerDesc;
import walk.graphattan.builder.midtown.MidtownGraphBuilder;
import walk.graphattan.exception.InvalidLocationException;
import walk.graphattan.graph.CityGraph;

public class Commute {

	public static void main(String[] args) {
		// initialize MidtownGraphBuilder with starting and finishing locations - 1st
		// Ave and 49th St, NE corner to 3rd Ave and 53rd St, SE corner
		MidtownGraphBuilder mgb = new MidtownGraphBuilder();
		try {
			mgb.setStart(51, 1, CornerDesc.NE);
			mgb.setFinish(53, 3, CornerDesc.SE);
		} catch (InvalidLocationException e) {
			e.printStackTrace();
		}
		CityGraph cityGraph = mgb.build();
		Iterator it = cityGraph.getGraph().entrySet().iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}

}
