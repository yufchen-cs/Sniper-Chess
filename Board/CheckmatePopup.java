package Board;

import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class CheckmatePopup {

	public static void display(String text) {
		System.out.println(text);
		
		Pane endPane = new Pane();
		Text endText = new Text(50, 50, text);
		endPane.getChildren().add(endText);
		
		Button exitButton = new Button("Exit Game");
		exitButton.setOnAction(e -> System.exit(0));
		exitButton.relocate(85, 75);
		endPane.getChildren().add(exitButton);
		
		Scene endScene = new Scene(endPane, 275, 120);	
		Stage endStage = new Stage();
		endStage.setTitle("Game Ended");
		endStage.setScene(endScene);
		endStage.setResizable(false);
		endStage.initModality(Modality.APPLICATION_MODAL);
		endStage.showAndWait();
	}
	
}
