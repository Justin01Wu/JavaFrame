package cemc.s2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

// Mathematics and Computing Contests 2020 Junior question 5
//  https://www.cemc.uwaterloo.ca/contests/computing/2020/ccc/juniorEF.pdf
/*
 * Problem J5/S2: Escape Room
 * 
Problem Description
You have to determine if it is possible to escape from a room. The room is an M-by-N grid with
each position (cell) containing a positive integer. The rows are numbered 1, 2, . . . , M and the
columns are numbered 1, 2, . . . , N. We use (r, c) to refer to the cell in row r and column c.
You start in the top-left corner at (1, 1) and exit from the bottom-right corner at (M, N). If you
are in a cell containing the value x, then you can jump to any cell (a, b) satisfying a × b = x. For
example, if you are in a cell containing a 6, you can jump to cell (2, 3).
Note that from a cell containing a 6, there are up to four cells you can jump to: (2, 3), (3, 2), (1, 6),
or (6, 1). If the room is a 5-by-6 grid, there isn’t a row 6 so only the first three jumps would be
possible.

Input Specification
The first line of the input will be an integer M (1 ≤ M ≤ 1000). The second line of the input will
be an integer N (1 ≤ N ≤ 1000). The remaining input gives the positive integers in the cells of
the room with M rows and N columns. It consists of M lines where each line contains N positive
integers, each less than or equal to 1 000 000, separated by single spaces.
For 1 of the 15 available marks, M = 2 and N = 2.
For an additional 2 of the 15 available marks, M = 1.
For an additional 4 of the 15 available marks, all of the integers in the cells will be unique.
For an additional 4 of the 15 available marks, M ≤ 200 and N ≤ 200.

Output Specification
Output yes if it is possible to escape from the room. Otherwise, output no.
Sample Input
	3
	4
	3 10 8 14
	1 11 12 12
	6 2 3 9
	
Output for Sample Input
	yes

 */
public class PathFinder {
	
	
	static String file = "F:/projects/python_test/cemc/all_data/junior_data/j5_s2/j5.06.05.in";
	//static String file = "F:/projects/python_test/cemc/all_data/junior_data/j5_s2/j5.sample.in";
	//static String file = "F:/projects/python_test/cemc/all_data/junior_data/j5_s2/j5.06.06.in";

	public static void main(String[] args) throws IOException {
		
		Date since = new Date();
		
		CellNode cell = findPath(file);
		Date now = new Date();
		
		printCellChain(cell);
		
		System.out.println((now.getTime()-since.getTime())/1000 + " seconds");

	}
	
	static CellNode findPath(String file) throws IOException {
		
		int[][] grid = readData(file);
		Map<Integer, List<CellNode>> map = generateMap(grid);
		return findExit(map, grid);
		
		
	}

	static int[][] readData(String file) throws IOException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		Integer R = Integer.valueOf(br.readLine());
		Integer C = Integer.valueOf(br.readLine());
		int[][] grid = new int[R][C];

		for (int r = 0; r < R; r++) {
			String onerow = br.readLine();
			String[] rowRaw = onerow.split("\\W+");
			for (int c = 0; c < C; c++) {
				Integer value = Integer.valueOf(rowRaw[c]);
				grid[r][c] = value;
			}

			// print("--- %s seconds ---" % (time.time() - start_time))
		}
		br.close();
		fr.close();
		return grid;
	}

	static Map<Integer, List<CellNode>> generateMap(int[][] grid) {
		Map<Integer, List<CellNode>> ditc = new HashMap<>();
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				Integer num = grid[r][c];
				List<CellNode> list = ditc.get(num);
				if (list == null) {
					list = new ArrayList<>();
					ditc.put(num, list);
				}
				CellNode cell = new CellNode();
				cell.num = grid[r][c];
				cell.x = r + 1;
				cell.y = c + 1;
				cell.product = cell.x * cell.y;
				list.add(cell);

			}
		}
		return ditc;
	}
	
	static CellNode findLastCell(Map<Integer, List<CellNode>> ditc, int[][] grid) {
		int r = grid.length - 1;
		int c = grid[0].length - 1;
		List<CellNode> list = ditc.get(grid[r][c]);
		for(CellNode cell: list) {
			if(cell.x == r+1 && cell.y == c +1 ) {
				return cell;
			}
		}
		return null;
	}

	static CellNode findExit(Map<Integer, List<CellNode>> ditc, int[][] grid) {
		// start from the last cell
		CellNode last = findLastCell(ditc, grid);
		
		Set<Integer> finishedList = new HashSet<>();
		Stack<CellNode> posnum = new Stack<>();
		while (true) {
			List<CellNode> list = ditc.get(last.product);
			if (list != null) {
				for (CellNode cell : list) {
					cell.parent = last;
	
					if (cell.product == 1) {
						System.out.println("yes");
						
						// print("--- %s seconds ---" % (time.time() - start_time))
						return cell;
					}
					if (finishedList.contains(cell.product)) {
						continue;
					}
					posnum.push(cell);
					finishedList.add(cell.product);

				}
			}
			if (posnum.isEmpty()) {
				System.out.println("no");
				// print("--- %s seconds ---" % (time.time() - start_time))
				return null;
			}
			last = posnum.pop();
		}
	}
	
	static void printCellChain(CellNode cell) {
		while (cell != null) {
			System.out.println(cell.x + "," + cell.y);
			cell = cell.parent;
		}
		
	}

}
