package Pieces;

public class Knight extends Piece{

	private String name = "Knight";
	
	public Knight(String color) {
		super(color, color=="White"?"/resources/white_knight.png":"/resources/black_knight.png");
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isLegalMove(int x1, int y1, int x2, int y2) {
		int dx = Math.abs(x1-x2);
		int dy = Math.abs(y1-y2);
		if((dx == 2 && dy == 1) || (dx == 1 && dy ==2))
			return true;
		return false;
	}
}
