package Pieces;

import Board.ChessBoard;

public class Queen extends Piece{

	private String name = "Queen";
	
	public Queen(String color) {
		super(color, color=="White"?"/resources/white_queen.png":"/resources/black_queen.png");
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isLegalMove(int x1, int y1, int x2, int y2) {
		int dx = x1-x2;
		int dy = y1-y2;
		if(dx == 0) { //move in y-direction
			int yMax = Math.max(y1, y2);
			int yMin = Math.min(y1, y2) + 1;
			for(; yMin<yMax; yMin++) {
					if(ChessBoard.hasPiece(x1, yMin))
						return false;
			}
			return true;
		}
		else if (dy == 0) { //move in x-direction
			int xMax = Math.max(x1, x2);
			int xMin = Math.min(x1, x2) + 1;
			for(; xMin<xMax; xMin++) {
					if(ChessBoard.hasPiece(xMin, y1))
						return false;
			}
			return true;
		}
		else if(Math.abs(dx) == Math.abs(dy)) {
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
