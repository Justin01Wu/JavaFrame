package algorithm.cemc.year2020.s2;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import algorithm.cemc.year2020.s2.CellNode;
import algorithm.cemc.year2020.s2.PathFinder;

public class PathFinderTest {

	@Test
	public void testFindPathString0605() throws IOException {
		String file = getFilePathFromClassPath("j5.06.05.in");
		CellNode cell = PathFinder.findPath(file);
		assertNotNull(cell);
		PathFinder.printCellChain(cell);
	}
	
	@Test
	public void testFindPathString0606() throws IOException {
		String file = getFilePathFromClassPath("j5.06.06.in");
		CellNode cell = PathFinder.findPath(file);
		assertNotNull(cell);
		PathFinder.printCellChain(cell);
	}	
	
	@Test
	public void testFindPathString0703() throws IOException {
		String file = getFilePathFromClassPath("j5.07.03.in");
		CellNode cell = PathFinder.findPath(file);
		assertNotNull(cell);
		PathFinder.printCellChain(cell);
	}	
	
	@Test
	public void testFindPathString0303() throws IOException {
		String file = getFilePathFromClassPath("j5.03.03.in");
		CellNode cell = PathFinder.findPath(file);
		assertNotNull(cell);
		PathFinder.printCellChain(cell);
	}	
	
	@Test
	public void testFindPathString0102() throws IOException {
		String file = getFilePathFromClassPath("j5.01.02.in");
		CellNode cell = PathFinder.findPath(file);
		assertNotNull(cell);
		PathFinder.printCellChain(cell);
	}
	
	@Test
	public void testFindPathString0103() throws IOException {
		String file = getFilePathFromClassPath("j5.01.03.in");
		CellNode cell = PathFinder.findPath(file);
		assertNotNull(cell);
		PathFinder.printCellChain(cell);
	}
	
	@Test
	public void testFindPathString0104() throws IOException {
		String file = getFilePathFromClassPath("j5.01.04.in");
		CellNode cell = PathFinder.findPath(file);
		assertNull(cell);		
	}
	
	@Test
	public void testFindPathStringSample() throws IOException {
		String file = getFilePathFromClassPath("j5.sample.in");
		
		CellNode cell = PathFinder.findPath(file);
		assertNotNull(cell);
		PathFinder.printCellChain(cell);
	}
	
	private String getFilePathFromClassPath(String file) {
		URL url = this.getClass().getResource(file);
		return url.getFile();
	}

}
