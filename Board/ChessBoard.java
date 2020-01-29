package Board;

import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.NullPiece;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;


public class ChessBoard {
	
	public static Piece[][] board;
	public static boolean capture;
	public static boolean castle = false;
	public static int whiteKingX = 4;
	public static int whiteKingY = 7;
	public static boolean whiteInCheck = false;
	public static int blackKingX = 4;
	public static int blackKingY = 0;
	public static boolean blackInCheck = false;
	public static boolean ValidMove = false;
	public static boolean undo = false;
	public static int turn = 0;
	
	public ChessBoard() { //Position of the tiles: [column][row] or [x][y]
		board = new Piece[8][8];
		newBoard();
	}
	
	public void newBoard() {
		
		for(int i=0; i< board.length; i++){
			board[i][1] = new Pawn("Black");
			board[i][6] = new Pawn("White");
		}
		
		board[0][0]= new Rook("Black");
		board[7][0]= new Rook("Black");
		board[1][0]= new Knight("Black");
		board[6][0]= new Knight("Black");
		board[2][0]= new Bishop("Black");
		board[5][0]= new Bishop("Black");
		board[4][0]= new King("Black");
		board[3][0]= new Queen("Black");
		
		board[0][7]= new Rook("White");
		board[7][7]= new Rook("White");
		board[1][7]= new Knight("White");
		board[6][7]= new Knight("White");
		board[2][7]= new Bishop("White");
		board[5][7]= new Bishop("White");
		board[4][7]= new King("White");
		board[3][7]= new Queen("White");
		
		
		for(int i=0;i<64;i++) {
			if(board[i%8][i/8] == null)
				board[i%8][i/8] = new NullPiece();
		}
		printBoard();
	}
	
	public static boolean hasPiece(int x, int y) {
		return !(board[x][y].getName().equals("Null"));
	}
	
	public static Piece getPiece(int x, int y) {
		return board[x][y];
	}
	
	//Attempt to perform a move with [x1][y1] currently selected and targeted position is [x2][y2]
	public static boolean move(int x1, int y1, int x2, int y2) {
		//Check if the current player is moving a piece of their color
		String color = board[x1][y1].getColor();
		if(turn%2 == 0 && !color.equals("White") || turn%2 == 1 && !color.equals("Black"))
			return false;
		
		//Evaluate if the move is legal, if yes, determine what type of move it is
		if(hasPiece(x1,y1) && board[x1][y1].isLegalMove(x1, y1, x2, y2)) {
			capture = hasPiece(x2,y2) && !(color.equals(board[x2][y2].getColor()));
			if(capture) { //Capture an opponent piece in the target position
				Piece temp = board[x2][y2];
				board[x2][y2] = new NullPiece();
				if(isInCheck(color)){ //Not allow to put player's own king in check
					board[x2][y2] = temp;
					return false;
				}
				if(undo) { //Undo the current move (use to test possible moves for king if in check)
					board[x2][y2] = temp;
					ValidMove = true;
				}
				else
					updateMovedCondition(x1,y1);
				return true;
			}
			else if(!hasPiece(x2,y2)){ //Move selected piece to target location
				Piece temp = board[x2][y2];
				board[x2][y2] = board[x1][y1];
				board[x1][y1] = new NullPiece();
				if(board[x2][y2].getName().equals("King"))
					updatePos(color,x2,y2);
				if(isInCheck(color)){ //Not allow to put player's own king in check
					board[x1][y1] = board[x2][y2];
					board[x2][y2] = temp;
					if(board[x1][y1].getName().equals("King"))
						updatePos(color,x1,y1);
					return false;
				}
				if(undo) { //Undo the current move (use to test possible moves for king if in check)
					board[x1][y1] = board[x2][y2];
					board[x2][y2] = temp;
					if(board[x1][y1].getName().equals("King"))
						updatePos(color,x1,y1);
					ValidMove = true;
				}
				else
					updateMovedCondition(x2,y2);
				return true;
			}
			else if(castle) {//King's special move - castle
				((King)board[x1][y1]).setMoved(true);
				((Rook)board[x2][y2]).setMoved(true);
				castle(color,x1,y1,x2,y2);
				return true;
			}
		}
		return false;
	}
	
	public static void promotion(String color,int x, int y) {
		board[x][y] = new Queen(color);
	}
	
	public static void castle(String color,int x1, int y1, int x2, int y2) {
		if(x1>x2) {
			board[x1-2][y1] = board[x1][y1];
			board[x1-1][y1] = board[x2][y2];
			board[x1][y1] = new NullPiece();
			board[x2][y2] = new NullPiece();
		}
		else {
			board[x1+2][y1] = board[x1][y1];
			board[x1+1][y1] = board[x2][y2];
			board[x1][y1] = new NullPiece();
			board[x2][y2] = new NullPiece();
		}
	}
	
	public static void updatePos(String color, int x, int y) {
		if(color.equals("White")) {
			whiteKingX = x;
			whiteKingY = y;
		}
		else {
			blackKingX = x;
			blackKingY = y;
		}
	}
	
	public static void updateMovedCondition(int x, int y) {
		if(board[x][y].getName().equals("Pawn"))
			((Pawn)board[x][y]).setMoved(true);
		else if(board[x][y].getName().equals("King"))
			((King)board[x][y]).setMoved(true);
		else if(board[x][y].getName().equals("Rook"))
			((Rook)board[x][y]).setMoved(true);
	}
	
	public static boolean isInCheck(String color) {
		if(color.equals("White")) {
			for(int i=0;i<64;i++) {
				if(board[i%8][i/8].getColor().equals("Black") 
						&& board[i%8][i/8].isLegalMove(i%8, i/8, whiteKingX, whiteKingY)){
					whiteInCheck = true;
					return true;
				}	
			}
			whiteInCheck = false;
			return false;
		}
		else {
			for(int i=0;i<64;i++) {
				if(board[i%8][i/8].getColor().equals("White") 
						&& board[i%8][i/8].isLegalMove(i%8, i/8, blackKingX, blackKingY)){
					blackInCheck = true;
					return true;
				}
			}
			blackInCheck = false;
			return false;	
		}
	}
	
	public static boolean isCheckmate(String color) {
		undo = true;
		if(color.equals("White")) {
			for(int i=0;i<64;i++) {
				if(board[i%8][i/8].getColor().equals("White")){
					for(int j=0;j<64;j++) {
						if(move(i%8, i/8, j%8, j/8) && ValidMove) {
							ValidMove = false;
							undo = false;
							return false;
						}
					}
				}
			}
			undo = false;
			return true;
		}
		else {
			for(int i=0;i<64;i++) {
				if(board[i%8][i/8].getColor().equals("Black")){
					for(int j=0;j<64;j++) {
						if(move(i%8, i/8, j%8, j/8) && ValidMove) {
							ValidMove = false;
							undo = false;
							return false;
						}
					}
				}
			}
			undo = false;
			return true;
		}	
	}
	
	public static void printBoard() {
		for(int i=0;i<64;i++) {
			System.out.print(board[i%8][i/8].getName() + " ");
			if(i%8 == 7) {
				System.out.print("\n");
			}
		}
	}

}
