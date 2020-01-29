package Pieces;

import Board.ChessBoard;


public class Pawn extends Piece{
	
	private String name = "Pawn";
	private boolean moved = false;
	
	public Pawn(String color) {
		super(color, color=="White"?"/resources/white_pawn.png":"/resources/black_pawn.png");
		
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isLegalMove(int x1, int y1, int x2, int y2) {
		int dx = Math.abs(x1-x2);
		int dy = Math.abs(y1-y2);
		if((super.isBlack() && y2 > y1) || (super.isWhite() && y1 > y2)){
			if(!(moved)) {
				if((dy == 1 || dy == 2) && dx == 0) {
					return !(ChessBoard.hasPiece(x2, y2));
				}
				else if(dy == 1 && dx == 1) {
					return (ChessBoard.hasPiece(x2, y2));
				}
			}
			else {
				if(dy == 1 && dx == 0)
					return !(ChessBoard.hasPiece(x2, y2));
				else if(dy == 1 && dx == 1)
					return (ChessBoard.hasPiece(x2, y2));
			}
		}
		return false;
	}
	
	public boolean canPromo(int y) {
		return ((super.isBlack() && y == 7) || (super.isWhite() && y == 0));
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	
}
