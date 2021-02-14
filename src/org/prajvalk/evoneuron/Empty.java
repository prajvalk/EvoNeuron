package org.prajvalk.evoneuron;

public class Empty extends Entity {

	public Empty(Grid g, int posx, int posy) {
		super(g,posx,posy);
	}		

	@Override
	public void triggerFunction() {}
	
	@Override
	public String getName() { return "Empty"; }

}