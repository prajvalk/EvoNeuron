public class Food extends Entity {
	
	public Food(Grid g, int posx, int posy) {
		super(g,posx,posy);
	}
	
	@Override
	public void triggerFunction() {}
	
	@Override
	public String getName() { return "Food"; } 
	
}