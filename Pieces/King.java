package Pieces;

import Board.ChessBoard;

public class King extends Piece{

	private String name = "King";
	private boolean moved = false;
	
	public King(String color) {
		super(color, color=="White"?"/resources/white_king.png":"/resources/black_king.png");
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isLegalMove(int x1, int y1, int x2, int y2) {
		//Check if the move is a special move - castle
		if(!(moved) && ChessBoard.board[x2][y2].getName().equals("Rook")) {
			if(ChessBoard.isInCheck(super.getColor()))
				return false;
			Rook r = (Rook) ChessBoard.board[x2][y2];
			if(super.getColor().equals(r.getColor()) && !(r.isMoved())) {
				int xMax = Math.max(x1, x2);
				int xMin = Math.min(x1, x2) + 1;
				int step = 1;
				for(; xMin<xMax; xMin++) {
						if(ChessBoard.hasPiece(xMin, y1))
							return false;
						if(step < 3 && x1>x2) { //If castle to the left
							ChessBoard.updatePos(super.getColor(),x1-step,y1);
							step ++;
							if(ChessBoard.isInCheck(super.getColor())) {
								ChessBoard.updatePos(super.getColor(),x1,y1);
								return false;
							}
						}
						else if(step < 3 && x2>x1) { //If castle to the right
							ChessBoard.updatePos(super.getColor(),x1+step,y1);
							step ++;
							if(ChessBoard.isInCheck(super.getColor())) {
								ChessBoard.updatePos(super.getColor(),x1,y1);
								return false;
							}
						}
				}
				ChessBoard.castle = true;
				return true;
			}
			return false;
		}
		int dx = Math.abs(x1-x2);
		int dy = Math.abs(y1-y2);
		if((dx + dy) == 1 || (dx == 1 && dy == 1)) {
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
