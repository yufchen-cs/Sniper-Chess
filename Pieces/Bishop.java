package Pieces;

import Board.ChessBoard;

public class Bishop extends Piece{

	private String name = "Bishop";
	
	public Bishop(String color) {
		super(color, color=="White"?"/resources/white_bishop.png":"/resources/black_bishop.png");
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isLegalMove(int x1, int y1, int x2, int y2) {
		int dx = x1-x2;
		int dy = y1-y2;
		if(Math.abs(dx) == Math.abs(dy)) {
			int xMax = Math.max(x1, x2);
			int xMin = Math.min(x1, x2) + 1;
			if(dx*dy > 0) { // moving in the off-diagonal
				int yMin = Math.min(y1, y2) + 1;
				for(; xMin<xMax; xMin++, yMin++) {
						if(ChessBoard.hasPiece(xMin, yMin))
							return false;
				}
			}
			else { // moving in the main-diagonal
				int yMax = Math.max(y1, y2) - 1;
				for(; xMin<xMax; xMin++, yMax--) {
					if(ChessBoard.hasPiece(xMin, yMax))
						return false;
				}
			}
			return true;
		}
		return false;
	}
	
}
