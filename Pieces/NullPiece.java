package Pieces;

public class NullPiece extends Piece{
	
	private String name ="Null";
	
	public NullPiece() {
		super("", "/resources/null.png");
	}

	public boolean isLegalMove(int x1, int y1, int x2, int y2) {
		return false;
	}

	public String getName() {
		return name;
	}
}