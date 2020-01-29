package Pieces;

import Board.ChessBoard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Piece extends ImageView {
	
	private String color;
	
	protected Piece(String color,String imgPath) {
		super(new Image(imgPath));
		this.setColor(color);
		super.setFitHeight(100);
		super.setFitWidth(100);
		
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public boolean isWhite() {
		return (color.equals("White"));
	}
	
	public boolean isBlack() {
		return (color.equals("Black"));
	}
	
	public abstract String getName();
	
	public abstract boolean isLegalMove(int x1, int y1, int x2, int y2);
	
}
