package Pieces;

import Board.ChessBoard;

public class Rook extends Piece{
	
	private String name = "Rook";
	private boolean moved = false;
	
	public Rook(String color) {
		super(color, color=="White"?"/resources/white_rook.png":"/resources/black_rook.png");
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isLegalMove(int x1, int y1, int x2, int y2) {
		if(x1 == x2) { //move in y-direction
			int yMax = Math.max(y1, y2);
			int yMin = Math.min(y1, y2) + 1;
			for(; yMin<yMax; yMin++) {
					if(ChessBoard.hasPiece(x1, yMin))
						return false;
			}
			return true;
		}
		else if (y1 == y2) { //move in x-direction
			int xMax = Math.max(x1, x2);
			int xMin = Math.min(x1, x2) + 1;
			for(; xMin<xMax; xMin++) {
					if(ChessBoard.hasPiece(xMin, y1))
						return false;
			}
			return true;
		}
		return false;
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	
}
