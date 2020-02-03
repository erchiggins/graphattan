package walk.graphattan.builder.midtown;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import walk.graphattan.exception.InvalidLocationException;
import walk.graphattan.graph.Vertex;

class MidtownGraphBuilderTest {

	private MidtownGraphBuilder mgb = new MidtownGraphBuilder();
	private int start_st;
	private int start_ave;
	private CornerDesc start_corner;
	private int finish_st;
	private int finish_ave;
	private CornerDesc finish_corner;

	@BeforeEach
	void initializeGraph() throws Exception {
		this.start_st = 12;
		this.start_ave = 5;
		this.start_corner = CornerDesc.SW;
		this.finish_st = 14;
		this.finish_ave = 6;
		this.finish_corner = CornerDesc.NE;
		mgb.setStart(start_st, start_ave, start_corner);
		mgb.setFinish(finish_st, finish_ave, finish_corner);
	}

	@Test
	void testSetStart() {
		assertDoesNotThrow(() -> mgb.setStart(start_st, start_ave, start_corner));
	}

	@Test
	void testSetFinish() {
		assertDoesNotThrow(() -> mgb.setFinish(finish_st, finish_ave, finish_corner));
	}

	@Test
	void testSetInvalidStartSt() {
		assertThrows(InvalidLocationException.class, () -> mgb.setStart(-4, start_ave, start_corner));
	}

	@Test
	void testSetInvalidStartAve() {
		assertThrows(InvalidLocationException.class, () -> mgb.setStart(start_st, -4, start_corner));
	}

	@Test
	void testSetInvalidStartCorner() {
		assertThrows(InvalidLocationException.class, () -> mgb.setStart(start_st, start_ave, null));
	}

	@Test
	void testSetInvalidFinishSt() {
		assertThrows(InvalidLocationException.class, () -> mgb.setFinish(-4, finish_ave, finish_corner));
	}

	@Test
	void testSetInvalidFinishAve() {
		assertThrows(InvalidLocationException.class, () -> mgb.setFinish(finish_st, -4, finish_corner));
	}

	@Test
	void testSetInvalidFinishCorner() {
		assertThrows(InvalidLocationException.class, () -> mgb.setFinish(finish_st, finish_ave, null));
	}

	@Test
	void testCalculateEastWestVertices() {
		assertEquals(mgb.calculateEastWestVertices(), 4);
	}

	@Test
	void testCalculateNorthSouthVertices() {
		assertEquals(mgb.calculateNorthSouthVertices(), 6);
	}
	
	@Test
	void testFindSWIntersection() {
		assertEquals("6 Ave and 12 St", mgb.findSWIntersection().getDescription());
	}

	// test find init vertex coords, start is SW corner of intersection
	@Test
	void testInitXPosSWStart() {
		mgb.findSWIntersection();
		assertEquals(-900.0, mgb.calculateXInit(), 0.001);
	}

	@Test
	void testInitYPosSWStart() {
		mgb.findSWIntersection();
		assertEquals(0.0, mgb.calculateYInit(), 0.001);
	}

	// test find init vertex coords, start is SE corner of intersection
	@Test
	void testInitXPosSEStart() {
		try {
			mgb.setStart(start_st, start_ave, CornerDesc.SE);
			mgb.findSWIntersection();
		} catch (InvalidLocationException e) {
			fail();
		}
		assertEquals(-1000.0, mgb.calculateXInit(), 0.001);
	}

	@Test
	void testInitYPosSEStart() {
		try {
			mgb.setStart(start_st, start_ave, CornerDesc.SE);
			mgb.findSWIntersection();
		} catch (InvalidLocationException e) {
			fail();
		}
		assertEquals(0.0, mgb.calculateYInit(), 0.001);

	}

	// test find init vertex coords, start is NE corner of intersection
	@Test
	void testInitXPosNEStart() {
		try {
			mgb.setStart(start_st, start_ave, CornerDesc.NE);
			mgb.findSWIntersection();
		} catch (InvalidLocationException e) {
			fail();
		}
		assertEquals(-1000.0, mgb.calculateXInit(), 0.001);
	}

	@Test
	void testInitYPosNEStart() {
		try {
			mgb.setStart(start_st, start_ave, CornerDesc.NE);
			mgb.findSWIntersection();
		} catch (InvalidLocationException e) {
			fail();
		}
		assertEquals(-60.0, mgb.calculateYInit(), 0.001);

	}

	// test find init vertex coords, start is NW corner of intersection
	@Test
	void testInitXPosNWStart() {
		try {
			mgb.setStart(start_st, start_ave, CornerDesc.NW);
			mgb.findSWIntersection();
		} catch (InvalidLocationException e) {
			fail();
		}
		assertEquals(-900.0, mgb.calculateXInit(), 0.001);
	}

	@Test
	void testInitYPosNWStart() {
		try {
			mgb.setStart(start_st, start_ave, CornerDesc.NW);
			mgb.findSWIntersection();
		} catch (InvalidLocationException e) {
			fail();
		}
		assertEquals(-60.0, mgb.calculateYInit(), 0.001);
	}

	@Test
	void testAddHorizontalWithNullAndNorthSide() {
		mgb.findSWIntersection();
		assertNull(mgb.addHorizontal(null, true));
	}
	
	@Test
	void testAddFirstHorizontal() {
		mgb.calculateEastWestVertices();
		mgb.calculateEastWestVertices();
		mgb.findSWIntersection();
		Vertex[] firstHorizontal = mgb.addHorizontal(null, false);
		assertEquals(4, firstHorizontal.length);
	}
	
	@Test
	void testAddFirstHorizontalInitXPos() {
		mgb.calculateEastWestVertices();
		mgb.calculateEastWestVertices();
		mgb.findSWIntersection();
		Vertex[] firstHorizontal = mgb.addHorizontal(null, false);
		assertEquals(-900.0, firstHorizontal[0].getPosX(), 0.001);
	}
	
	@Test
	void testAddFirstHorizontalInitYPos() {
		mgb.calculateEastWestVertices();
		mgb.calculateEastWestVertices();
		mgb.findSWIntersection();
		Vertex[] firstHorizontal = mgb.addHorizontal(null, false);
		assertEquals(0.0, firstHorizontal[0].getPosY(), 0.001);
	}
	
	@Test
	void testAddFirstHorizontalSecondXPos() {
		mgb.calculateEastWestVertices();
		mgb.calculateEastWestVertices();
		mgb.findSWIntersection();
		Vertex[] firstHorizontal = mgb.addHorizontal(null, false);
		assertEquals(-800.0, firstHorizontal[1].getPosX(), 0.001);
	}
	
	@Test
	void testAddSecondHorizontal() {
		mgb.calculateEastWestVertices();
		mgb.calculateEastWestVertices();
		mgb.findSWIntersection();
		Vertex[] firstHorizontal = mgb.addHorizontal(null, false);
		Vertex[] secondHorizontal = mgb.addHorizontal(firstHorizontal, true);
		assertEquals(4, secondHorizontal.length);
	}

}
