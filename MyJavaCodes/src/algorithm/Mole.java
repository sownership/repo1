package algorithm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Mole {

	public static void main(String[] args) {
		
		int cellLength = input();
		
		solve(cellLength);
		
		print();
	}

	private static void solve(int cellLength) {
		for(int i=0; i<cellLength; i++) {
			for(int j=0; j<cellLength; j++) {
				if(cells[i][j]!=1) {
					continue;
				}
				holeCount.add(0);
				search(i, j);
			}
		}
	}

	private static int input() {
		int cellLength = Integer.valueOf(scanner.nextLine());
		cells = new int[cellLength][cellLength];
		for(int i=0; i<cellLength; i++) {
			String[] row = scanner.nextLine().split(" ");
			for(int j=0; j<cellLength; j++) {
				cells[i][j] = Integer.valueOf(row[j]);
			}
		}
		return cellLength;
	}

	private static Scanner scanner = new Scanner(System.in);
	
	private static int[][] cells;

	private static void print() {
		Collections.sort(holeCount);
		System.out.println(holeCount.size());
		for(int i=holeCount.size()-1; i>=0; i--) {
			System.out.println(holeCount.get(i));
		}
	}

	private static List<Integer> holeCount = new LinkedList<>();
	
	private static void search(int r, int c) {
		
		if(cells[r][c]!=1) {
			return;
		}
		
		cells[r][c] = holeCount.size() + 1;
		holeCount.set(holeCount.size()-1, holeCount.get(holeCount.size()-1)+1);
		
		//r
		if(isIn(r, c+1)) {
			search(r, c+1);
		}
		
		//b
		if(isIn(r+1, c)) {
			search(r+1, c);
		}
		
		//l
		if(isIn(r, c-1)) {
			search(r, c-1);
		}
		
		//t
		if(isIn(r-1, c)) {
			search(r-1, c);
		}
	}

	private static boolean isIn(int r, int c) {
		return r>=0 && r<cells.length && c>=0 && c<cells.length;
	}
}
