package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfirmBox
{
    //exit stage
    public static void exit()
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("warning!");
        window.setMinWidth(250);

        Label label = new Label("Are you sure?");
        label.setFont(Font.font("Aldhabi", 40));
        VBox vbox = new VBox();
        HBox hbox = new HBox();

        Button btn1 = new Button("Yes");
        btn1.setFont(Font.font("Comic Sans MS", 12));
        btn1.setStyle("-fx-background-color :#F08080 ");
        btn1.setEffect(new DropShadow());

        Button btn2 = new Button("No");
        btn2.setFont(Font.font("Comic Sans MS", 12));
        btn2.setStyle("-fx-background-color :#F08080 ");
        btn2.setEffect(new DropShadow());


        btn1.setOnAction(e -> System.exit(0));
        btn2.setOnAction(e -> window.close());
        hbox.getChildren().addAll(btn1, btn2);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);

        vbox.getChildren().addAll(label, hbox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        window.setResizable(false);
        window.setScene(new Scene(vbox, 300, 150));
        window.showAndWait();
    }

    //rules stage
    public static void rules()
    {
        Image image = new Image("file:texture2.jpg");
        ImageView iv = new ImageView(image);
        iv.setFitHeight(750);
        iv.setFitWidth(1000);

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("rules");
        window.setMinWidth(250);

        Text text = new Text();
        text.setText("Battleship is a classic two player game where players try to sink their opponent’s navy ships.\n" +
                " Most Battleship games come with two self contained storage units or “game boards.”\n" +
        "The basic object of the game of Battleship is to hide your beads somewhere on your board\n" +
                " and by choosing a place on board, find your opponent’s beads before they find yours.\n"+
        "Contents of Battleship:\n" +
                "2 boards, 5 soldiers, 3 cavaliers, 2 castles and a center.\n" +
                "each fighter, except soldier, needs a time to rest. Here's the time needed for each fighter : \n"+
                "cavalier : 1 turn , castle : 2 turns , center : 3 turns\n");

        text.setFont(Font.font("Aldhabi", FontWeight.BOLD, 35));
        HBox root = new HBox();
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(text);
        StackPane layout = new StackPane();
        layout.getChildren().addAll(iv, root);

        window.setResizable(false);
        window.setScene(new Scene(layout, 1000, 750));
        window.showAndWait();

    }

    //alert box(warnings)
    public static void sorry(String message)
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("warning!");
        window.setMinWidth(250);

        Label label = new Label(message);
        label.setFont(Font.font("Aldhabi", 35));
        VBox vbox = new VBox();
        HBox hbox = new HBox();

        Button alright = new Button("Alright");
        alright.setFont(Font.font("Comic Sans MS", 15));
        alright.setStyle("-fx-background-color :#F08080 ");
        alright.setEffect(new DropShadow());

        alright.setOnAction(e -> window.close());
        hbox.getChildren().addAll(alright);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);

        vbox.getChildren().addAll(label, hbox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        window.setResizable(false);
        window.setScene(new Scene(vbox, 450, 150));
        window.showAndWait();
    }

}
