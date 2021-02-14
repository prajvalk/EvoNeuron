public class Grid {
	
	private Entity[][] gridrepresentation;
	
	public int width;
	
	public int height;
	
	public Grid(int w, int h) {
		gridrepresentation = new Entity[w][h];
		width = w;
		height = h;
		for(int i=0;i<w;i++) for(int j=0;j<h;j++) gridrepresentation[i][j] = new Empty(this,i,j);
		
		// Fill Cells
	}
	
	public Entity getEntity(int x, int y) {
		return gridrepresentation[x][y];
	}
	
	public void setEntity(int x, int y, Entity e) {
		gridrepresentation[x][y] = e;
	}		
	
}