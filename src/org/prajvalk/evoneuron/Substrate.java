package org.prajvalk.evoneuron;

import java.util.Vector;

public class Substrate {
	
	private static final int NN_DEPTH = 5;
	
	private static final int MAX_CELLS_PER_GENERATION = 35;
	
	private static final int CELL_RANKLIST_CUTOFF = 5;
	
	private static final float NUTRIENT_TO_CELL_RATIO = 3.1f;
	
	private static final float INSTANTANEOUS_MUTATION_FACTOR = 0.5f;
	
	private static final float NN_MUTATION_FACTOR = 1.8f;
	
	private static final float NNWT_MUTATION_FACTOR = 7.8f;
	
	private Grid substrate;
	
	private int w;
	
	private int h;
	
	private Cell[] nextGenCells;
	
	private float[] nextGenCellShare;
	
	public Substrate(int pw, int ph) {
		substrate = new Grid(pw,ph);
	}
	
	public void runGen0() {
		
	}
	
	public void runNextGen() {
		
	}
	
	public boolean checkForLivingCells() {
		for(int i=0;i<w;i++) for(int j=0;j<h;j++) if(substrate.getEntity(i,j).equals("Cell")) return true;
		return false;
	}
	
	public boolean checkForFood() {
		for(int i=0;i<w;i++) for(int j=0;j<h;j++) if(substrate.getEntity(i,j).equals("Food")) return true;
		return false;
	}
	
	public void rankCells() {
		int maxlife = 0;
		Vector<Cell> cells = new Vector<Cell>(1,1);
		Vector<Integer> lifetimes = new Vector<Integer>(1,1);
		for(int i=0;i<w;i++) {
			for(int j=0;j<h;j++) {
				if(substrate.getEntity(i,j).equals("Cell")) {
					Cell obj = (Cell) substrate.getEntity(i,j);
					cells.addElement(obj); // Check Unsafe Operations
					lifetimes.addElement(obj.getLifetime());
				}
			}
		}
		int n = lifetimes.size();
		Cell[] acref = new Cell[cells.size()];
		Integer[] arr = new Integer[lifetimes.size()];
        Integer temp1 = null;  
        Cell temp2 = null;  
        for(int i=0; i < n; i++){  
			for(int j=1; j < (n-i); j++){  
				if(arr[j-1] > arr[j]){ 
					temp1 = arr[j-1];  
					arr[j-1] = arr[j];  
					arr[j] = temp1;
					temp2 = acref[j-1];  
					acref[j-1] = acref[j];  
					acref[j] = temp2;
				}
			}  
        }
		Cell[] shortlist = new Cell[CELL_RANKLIST_CUTOFF];
		for(int i=0;i<CELL_RANKLIST_CUTOFF;i++) shortlist[i] = acref[i];
		nextGenCells = shortlist;
		int[] lifeshortlist = new int[CELL_RANKLIST_CUTOFF];
		for(int i=0;i<CELL_RANKLIST_CUTOFF;i++) lifeshortlist[i] = arr[i];
		double sum = 0.0d;
		for(int e:lifeshortlist) sum+=e;
		float[] cellshare = new float[lifeshortlist.length];
		for(int i=0;i<cellshare.length;i++) cellshare[i] = (float)(lifeshortlist[i])/(float)sum;
		nextGenCellShare = cellshare;
	}
	
	public void allocCellsAndFood() {
		for(int i=0;i<MAX_CELLS_PER_GENERATION;i++) {
			int rx = (int)(Math.random() * (w-1));
			int ry = (int)(Math.random() * (h-1));
			if(substrate.getEntity(rx,ry).equals("Cell")) {
				i--;
				continue;
			}			
			substrate.setEntity(rx, ry, new Cell(substrate, rx, ry, NN_DEPTH));
		}
		for(int i=0;i<(int)(MAX_CELLS_PER_GENERATION * NUTRIENT_TO_CELL_RATIO);i++) {
			int rx = (int)(Math.random() * (w-1));
			int ry = (int)(Math.random() * (h-1));
			if(substrate.getEntity(rx,ry).equals("Cell") || substrate.getEntity(rx,ry).equals("Food")) {
				i--;
				continue;
			}			
			substrate.setEntity(rx, ry, new Food(substrate, rx, ry));
		}
	}
	
}