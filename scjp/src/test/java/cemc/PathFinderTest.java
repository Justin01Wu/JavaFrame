package cemc;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class PathFinderTest {

	@Test
	public void testFindPathString0605() throws IOException {
		String file = "F:/projects/python_test/cemc/all_data/junior_data/j5_s2/j5.06.05.in";
		CellNode cell = PathFinder.findPath(file);
		assertNotNull(cell);
		PathFinder.printCellChain(cell);
	}
	
	@Test
	public void testFindPathString0606() throws IOException {
		String file = "F:/projects/python_test/cemc/all_data/junior_data/j5_s2/j5.06.06.in";
		CellNode cell = PathFinder.findPath(file);
		assertNotNull(cell);
		PathFinder.printCellChain(cell);
	}	
	
	@Test
	public void testFindPathString0703() throws IOException {
		String file = "F:/projects/python_test/cemc/all_data/junior_data/j5_s2/j5.07.03.in";
		CellNode cell = PathFinder.findPath(file);
		assertNotNull(cell);
		PathFinder.printCellChain(cell);
	}	
	
	@Test
	public void testFindPathString0303() throws IOException {
		String file = "F:/projects/python_test/cemc/all_data/junior_data/j5_s2/j5.03.03.in";
		CellNode cell = PathFinder.findPath(file);
		assertNotNull(cell);
		PathFinder.printCellChain(cell);
	}	
	
	@Test
	public void testFindPathString0102() throws IOException {
		String file = "F:/projects/python_test/cemc/all_data/junior_data/j5_s2/j5.01.02.in";
		CellNode cell = PathFinder.findPath(file);
		assertNotNull(cell);
		PathFinder.printCellChain(cell);
	}
	
	@Test
	public void testFindPathString0103() throws IOException {
		String file = "F:/projects/python_test/cemc/all_data/junior_data/j5_s2/j5.01.03.in";
		CellNode cell = PathFinder.findPath(file);
		assertNotNull(cell);
		PathFinder.printCellChain(cell);
	}
	
	@Test
	public void testFindPathString0104() throws IOException {
		String file = "F:/projects/python_test/cemc/all_data/junior_data/j5_s2/j5.01.04.in";
		CellNode cell = PathFinder.findPath(file);
		assertNull(cell);		
	}
	
	@Test
	public void testFindPathStringSample() throws IOException {
		String file = "F:/projects/python_test/cemc/all_data/junior_data/j5_s2/j5.sample.in";
		CellNode cell = PathFinder.findPath(file);
		assertNotNull(cell);
		PathFinder.printCellChain(cell);
	}

}
