package Board;

import javafx.stage.*;
import Pieces.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.geometry.*;

public class BoardGui extends Application{
	
	private boolean selected = false;
	private int currentX, currentY; //To keep track of the position of the tile currently selected
	private int targetX, targetY; //To keep track of the position of the targeted tile
	private Button[][] tile;
	
	public void start(Stage primaryStage) {
		ChessBoard chessBoard = new ChessBoard();
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(10,10,10,10));
		pane.setPrefSize(800, 800);

		//A GridPane that uses buttons to represent tiles of a chess board 
		tile = new Button[8][8];
		for(int x=0;x<8;x++) {
			for(int y=0;y<8;y++) {
				tile[x][y] = new Button("", ChessBoard.board[x][y]);
				tile[x][y].setPrefSize(100, 100);

				if((x+y)%2 == 0) {
					tile[x][y].setStyle("-fx-background-color: navajowhite");
				}
				else {
					tile[x][y].setStyle("-fx-background-color: peru");
				}
				pane.add(tile[x][y],x,y);
			}
		}
		
		for(Button[] column : tile) {
			for(Button t: column) {
				t.setOnAction((ActionEvent e) -> {
					if(!selected) {
						selected = true;
						Node source = (Node)e.getSource();
						currentX = GridPane.getColumnIndex(source);
						currentY = GridPane.getRowIndex(source);
						t.setStyle("-fx-background-color: powderblue");
						System.out.println("select");
				        System.out.println(currentX + " " + currentY);
					}
					else {
						selected = false;
						Node source = (Node)e.getSource() ;
						targetX = GridPane.getColumnIndex(source);
						targetY = GridPane.getRowIndex(source);
						tile[currentX][currentY].setStyle(((currentX+currentY)%2 == 0) ? 
								"-fx-background-color: navajowhite":"-fx-background-color: peru");
				        System.out.println("target");
				        System.out.println(targetX + " " + targetY);
				        
				        //After selecting two tiles, evaluate if it is a valid move, update GUI accordingly
				        if(ChessBoard.move(currentX, currentY, targetX, targetY)) {
				    		ChessBoard.printBoard();
					        if(ChessBoard.capture)
					        	tile[targetX][targetY].setGraphic(new NullPiece());
					        else if(ChessBoard.hasPiece(targetX,targetY)){
					        	tile[targetX][targetY].setGraphic(ChessBoard.board[targetX][targetY]);
					        	tile[currentX][currentY].setGraphic(new NullPiece());
					        }
					        if(ChessBoard.board[targetX][targetY].getName().equals("Pawn")) {
					        	if(((Pawn)ChessBoard.board[targetX][targetY]).canPromo(targetY)) {
					        		ChessBoard.promotion(ChessBoard.board[targetX][targetY].getColor(), targetX, targetY);
					        		tile[targetX][targetY].setGraphic(ChessBoard.board[targetX][targetY]);
					        	}
					        }
					        if(ChessBoard.castle) {
					        	for(int i = 0; i < 8; i++) {
					        		tile[i][targetY].setGraphic(ChessBoard.board[i][targetY]);
					        	ChessBoard.castle = false;
					        	}
					        }
					        ChessBoard.turn ++;
					        
					    }
				        
				        //Check if either king is in check after each move and update the GUI accordingly
				        if(ChessBoard.isInCheck("White")) {
				        	System.out.println("White in Check");
				        	tile[ChessBoard.whiteKingX][ChessBoard.whiteKingY].setStyle(
				        			"-fx-background-color: red");
					        if(ChessBoard.isCheckmate("White")) 
					        	CheckmatePopup.display("Checkmate! Black Wins!");
				        }
				        else
				        	tile[ChessBoard.whiteKingX][ChessBoard.whiteKingY].setStyle(
				        			((ChessBoard.whiteKingX+ChessBoard.whiteKingY)%2 == 0) ? 
									"-fx-background-color: navajowhite":"-fx-background-color: peru");
				        
				        if(ChessBoard.isInCheck("Black")){
				        	System.out.println("Black in Check");
				        	tile[ChessBoard.blackKingX][ChessBoard.blackKingY].setStyle(
				        			"-fx-background-color: red");
				        	if(ChessBoard.isCheckmate("Black")) 
				        		CheckmatePopup.display("Checkmate! White Wins!");
				        }
				        else
				        	tile[ChessBoard.blackKingX][ChessBoard.blackKingY].setStyle(
				        			((ChessBoard.blackKingX+ChessBoard.blackKingY)%2 == 0) ? 
									"-fx-background-color: navajowhite":"-fx-background-color: peru");
					}
				});
			}
		}
		
		Scene scene = new Scene(pane);
		primaryStage.setTitle("Sniper Chess");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show(); 
		}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		
	}
	
}
