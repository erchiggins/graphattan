package walk.graphattan.builder.midtown;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import walk.graphattan.builder.midtown.CornerDesc;
import walk.graphattan.builder.midtown.MidtownGraphBuilder;
import walk.graphattan.exception.InvalidLocationException;

class MidtownGraphBuilderTest {
	
	private MidtownGraphBuilder mgb = new MidtownGraphBuilder();
	private int start_st;
	private int start_av;
	private CornerDesc start_corner;
	private int finish_st;
	private int finish_av;
	private CornerDesc finish_corner;

	@BeforeEach
	void initializeGraph() throws Exception {
		this.start_st = 12;
		this.start_av = 5;
		this.start_corner = CornerDesc.SW;
		this.finish_st = 14;
		this.finish_av = 6;
		this.finish_corner = CornerDesc.NE;
		mgb.setStart(start_st, start_av, start_corner);
		mgb.setFinish(finish_st, finish_av, finish_corner);
	}

	@Test
	void testSetStart() {
		assertDoesNotThrow(() -> mgb.setStart(start_st, start_av, start_corner));
	}

	@Test
	void testSetFinish() {
		assertDoesNotThrow(() -> mgb.setFinish(finish_st, finish_av, finish_corner));
	}
	
	@Test
	void testSetInvalidStartSt() {
		assertThrows(InvalidLocationException.class, () -> mgb.setStart(-4, start_av, start_corner));
	}
	
	@Test
	void testSetInvalidStartAve() {
		assertThrows(InvalidLocationException.class, () -> mgb.setStart(start_st, -4, start_corner));
	}
	
	@Test
	void testSetInvalidStartCorner() {
		assertThrows(InvalidLocationException.class, () -> mgb.setStart(start_st, start_av, null));
	}
	
	@Test
	void testSetInvalidFinishSt() {
		assertThrows(InvalidLocationException.class, () -> mgb.setFinish(-4, finish_av, finish_corner));
	}
	
	@Test
	void testSetInvalidFinishAve() {
		assertThrows(InvalidLocationException.class, () -> mgb.setFinish(finish_st, -4, finish_corner));
	}
	
	@Test
	void testSetInvalidFinishCorner() {
		assertThrows(InvalidLocationException.class, () -> mgb.setFinish(finish_st, finish_av, null));
	}
	
	@Test 
	void testCalculateEastWestVertices() {
		assertEquals(mgb.calculateEastWestVertices(), 4);
	}
	
	@Test 
	void testCalculateNorthSouthVertices() {
		assertEquals(mgb.calculateNorthSouthVertices(), 6);
	}

	//test find init intersection and vertex coords, start is SW
	//test find init intersection and vertex coords, start is SE
	//test find init intersection and vertex coords, start is NE
	//test find init intersection and vertex coords, start is NW
	
	// test calculate NS/EW and find init for size zero
	

}
