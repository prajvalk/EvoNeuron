package org.prajvalk.evoneuron;

public abstract class Entity {
	
	protected Grid reference;
	
	protected int x;
	
	protected int y;
	
	public Entity(Grid g, int posx, int posy) {
		reference = g;
		x = posx;
		y = posy;
	}		
	
	public abstract void triggerFunction();
	
	public abstract String getName();
	
}