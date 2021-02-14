package org.prajvalk.evoneuron;

public class Cell extends Entity {
	
	private NEATNeuralNet nnn;
	
	private int posx;
	
	private int posy;
	
	private Grid g;
	
	public Cell(Grid g, int posx, int posy, int depth) {
		super(g,posx,posy);
		nnn = new NEATNeuralNet(depth);
		lifetime = 0;
		posx = super.x;
		posy = super.y;
		g = super.reference;
	}
	
	private int lifetime;
	
	private int maxlife = 20;
	
	@Override
	public void triggerFunction() {
		// Scan eniv
		int[] data=new int[8];
		for(int i=0;i<data.length;i++) data[i] = 0;
		if(posx == 0) {
			data[5] = -1;
			data[6] = -1;
			data[7] = -1;
		}
		if(posy == 0) {
			data[0] = -1;
			data[1] = -1;
			data[7] = -1;
		}
		if(posx == g.width) {
			data[1] = -1;
			data[2] = -1;
			data[3] = -1;
		}
		if(posy == g.height) {
			data[3] = -1;
			data[4] = -1;
			data[5] = -1;
		}
		try {
			if(g.getEntity(posx+1,posy).equals("Food")) data[2] = 1;
			if(g.getEntity(posx-1,posy).equals("Food")) data[6] = 1;
			if(g.getEntity(posx,posy+1).equals("Food")) data[0] = 1;
			if(g.getEntity(posx,posy-1).equals("Food")) data[4] = 1;
			if(g.getEntity(posx+1,posy+1).equals("Food")) data[1] = 1;
			if(g.getEntity(posx-1,posy+1).equals("Food")) data[5] = 1;
			if(g.getEntity(posx+1,posy-1).equals("Food")) data[3] = 1;
			if(g.getEntity(posx-1,posy-1).equals("Food")) data[7] = 1;
			
			if(g.getEntity(posx+1,posy).equals("Cell")) data[2] = 2;
			if(g.getEntity(posx-1,posy).equals("Cell")) data[6] = 2;
			if(g.getEntity(posx,posy+1).equals("Cell")) data[0] = 2;
			if(g.getEntity(posx,posy-1).equals("Cell")) data[4] = 2;
			if(g.getEntity(posx+1,posy+1).equals("Cell")) data[1] = 2;
			if(g.getEntity(posx-1,posy+1).equals("Cell")) data[5] = 2;
			if(g.getEntity(posx+1,posy-1).equals("Cell")) data[3] = 2;
			if(g.getEntity(posx-1,posy-1).equals("Cell")) data[7] = 2;
		} catch(ArrayIndexOutOfBoundsException aioobe) {
		}
		
		int output = nnn.computeWNN(data);
		if(data[output] < 0) selfdestruct(1);
		int swposx = posx;
		int swposy = posy;
		if(output == 2) swposx++;
		if(output == 6) swposx--;
		if(output == 0) swposy++;
		if(output == 4) swposy--;
		if(output == 1) { swposx++; swposy--; }
		if(output == 5) { swposx--; swposy++; }
		if(output == 3) { swposx++; swposy--; }
		if(output == 7) { swposx--; swposy--; }
		if(output > 7) selfdestruct(1);
		
		if(data[output] == 0) {
			Entity e1 = this;
			Entity e2 = g.getEntity(swposx, swposy);
			g.setEntity(posx, posy, e2);
			g.setEntity(swposx, swposy, e1);
		}
		if(data[output] == 1) {
			Entity e1 = this;
			Entity e2 = new Empty(g, posx, posy);
			g.setEntity(posx, posy, e2);
			g.setEntity(swposx, swposy, e1);
			maxlife += 2;
		}
		if(data[output] == 2) {
			Entity e1 = this;
			Cell dc = (Cell) g.getEntity(swposx, swposy); // TODO Check Unsafe Casting
			dc.selfdestruct(0);
			Entity e2 = new Empty(g, posx, posy);
			g.setEntity(posx, posy, e2);
			g.setEntity(swposx, swposy, e1);
			maxlife += 3;
		}
		
		lifetime++;
		if(lifetime >= maxlife) selfdestruct(0);
	}
	
	public void selfdestruct(int anotherChanceQ) {
		
	}
	
	public NEATNeuralNet replicateNNWithMutation(double nnF, double nnwtF) {
		return null;
	}		
	
	@Override
	public String getName() { return "Cell"; }
	
	public int getLifetime() {
		return lifetime;
	}
	
	
}