package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.nio.file.Paths;

public class Main extends Application {
    private String winner;
    private Player player1, player2;
    private Board board1, board2;
    private String Name1 = "", Name2 = "";
    private Stage window;
    private boolean endOftheGame;
    private MediaPlayer mediaPlayer;
    private boolean tryAgain;

    private Scene scene = new Scene(MainMenu(),1000, 750);
    private void music()
    {
        Media h = new Media(Paths.get("Mission.mp3").toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();
    }
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        music();
        window = new Stage();
        window.setTitle("Battle ship");
        window.setResizable(false);
        window.setScene(scene);
        window.show();
    }

    //_______Start Menu_____ first scene
    private Parent MainMenu()
    {
        Image image = new Image("file:test.jpg");
        ImageView iv = new ImageView(image);
        iv.setFitHeight(750);
        iv.setFitWidth(1000);

        VBox top = new VBox();

        Menu music = new Menu("music");
        MenuItem mute = new MenuItem("mute");
        mute.setOnAction(e -> mediaPlayer.setMute(true));
        MenuItem unmute = new MenuItem("unmute");
        unmute.setOnAction(e -> mediaPlayer.setMute(false));
        music.getItems().add(mute);
        music.getItems().add(unmute);
        MenuBar menu = new MenuBar();
        menu.getMenus().add(music);

        Label title = new Label("Battle Ship");
        title.setTextFill(Color.BLACK);
        title.setFont(Font.font("chiller", FontWeight.BOLD, 100));
        title.setEffect(new Glow());
        title.setPadding(new Insets(30));

        top.getChildren().addAll(menu, title);
        top.setAlignment(Pos.CENTER);

        VBox options = new VBox();

        Button startButton = new Button("Start");
        startButton.setFont(Font.font("chiller", FontWeight.BOLD,30));
        startButton.setPrefSize(120,23);
        startButton.setOnAction(e -> ChooseSize());
        startButton.setEffect(new InnerShadow());
        startButton.setStyle("-fx-background-color:#C0C0C0");

        Button rulesButton = new Button("Rules");
        rulesButton.setFont(Font.font("chiller", FontWeight.BOLD, 30));
        rulesButton.setPrefSize(120, 23);
        rulesButton.setOnAction(e -> ConfirmBox.rules());
        rulesButton.setEffect(new InnerShadow());
        rulesButton.setStyle("-fx-background-color:#C0C0C0 ");

        Button exitButton = new Button("Exit");
        exitButton.setFont(Font.font("chiller", FontWeight.BOLD, 30));
        exitButton.setPrefSize(120, 23);
        exitButton.setOnAction(e -> ConfirmBox.exit());
        exitButton.setEffect(new InnerShadow());
        exitButton.setStyle("-fx-background-color:#C0C0C0 ");

        options.getChildren().addAll(startButton, rulesButton, exitButton);
        options.setSpacing(70);
        options.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(options);

        StackPane layout = new StackPane();
        layout.getChildren().addAll(iv, root);
        return layout;

    }

    //_______choosing the board size
    private void ChooseSize()
    {
        Stage stage = new Stage();
        stage.setResizable(false);
        VBox layout = new VBox();
        HBox bottom = new HBox();

        TextField size = new TextField();
        size.setPromptText("min : 10 _ max : 40");

        Label alert = new Label("not valid");
        alert.setTextFill(null);
        bottom.getChildren().add(alert);

        Button pVp = new Button("submit");
        pVp.setFont(Font.font("Comic Sans MS"));
        pVp.setStyle("-fx-background-color: #F08080 ");
        pVp.setEffect(new DropShadow());
        pVp.setOnAction(e -> {
            try{
                alert.setTextFill(null);
                Board.SIZE = Integer.parseInt(size.getText());
                if(Board.SIZE > 40 || Board.SIZE < 10)
                    alert.setTextFill(Color.RED);
                else
                {
                    stage.close();
                    window.setScene(new Scene(playerVplayer(), 1000, 750));
                }

            }catch(NumberFormatException ex){
                alert.setTextFill(Color.RED);
            }

        });

        bottom.getChildren().addAll(pVp, size);
        bottom.setAlignment(Pos.CENTER_LEFT);
        bottom.setSpacing(13);
        bottom.setPadding(new Insets(5, 5, 5, 45));

        Label Question = new Label("please enter the size of the board (15 is suggested)");
        Question.setFont(Font.font("Aldhabi", 30));
        Question.setPadding(new Insets(20, 20, 20, 25));

        layout.getChildren().addAll(Question, bottom);

        stage.setScene(new Scene(layout, 450, 150));
        stage.showAndWait();
    }

    //_______entering the name of the players
    private Parent playerVplayer()
    {
        Image image = new Image("file:choosePos.jpg");
        ImageView iv = new ImageView(image);
        iv.setFitHeight(750);
        iv.setFitWidth(1000);

        Button next = new Button("next");
        next.setFont(Font.font("Comic Sans MS", 15));
        next.setStyle("-fx-background-color :#D2B48C");
        next.setEffect(new InnerShadow());

        Button back = new Button("back");
        back.setFont(Font.font("Comic Sans MS", 15));
        back.setStyle("-fx-background-color:#708090 ");
        back.setEffect(new InnerShadow());

        VBox vbox = new VBox();

        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();

        TextField player1name = new TextField();
        player1name.setPromptText("name");

        TextField player2name = new TextField();
        player2name.setPromptText("name");

        next.setOnAction(e -> {
            player1 = new Player(player1name.getText());
            player2 = new Player(player2name.getText());
            player1.setTurn(true);
            Name1 = player1name.getText();
            Name2 = player2name.getText();
            window.setScene(new Scene(choosePos(player1), 1000, 750));
        });
        back.setOnAction(e -> window.setScene(new Scene(MainMenu())));
        Label name1 = new Label("Player 1 : ");
        name1.setFont(Font.font("Chiller", 36));
        name1.setTextFill(Color.WHEAT);
        Label name2 = new Label("Player 2 : ");
        name2.setFont(Font.font("Chiller", 36));
        name2.setTextFill(Color.WHEAT);

        hbox1.getChildren().addAll(name1, player1name);
        hbox2.getChildren().addAll(name2, player2name);
        hbox3.getChildren().addAll(next, back);

        hbox1.setAlignment(Pos.CENTER);
        hbox2.setAlignment(Pos.CENTER);
        hbox3.setAlignment(Pos.CENTER);

        hbox1.setSpacing(20);
        hbox2.setSpacing(20);
        hbox3.setSpacing(30);

        vbox.getChildren().addAll(hbox1, hbox2, hbox3);
        vbox.setSpacing(50);
        vbox.setAlignment(Pos.CENTER);

        StackPane layout = new StackPane();
        layout.getChildren().addAll(iv, vbox);
        return layout;
    }

    //_______choosing the position of fighters : player1
    private Parent choosePos(Player player)
    {
        Image image = new Image("file:texture2.jpg");
        ImageView iv = new ImageView(image);
        iv.setFitHeight(750);
        iv.setFitWidth(1000);

        board1 = new Board(player, player2);
        board1.setPosMode(true);
        board1.ColorCells();
        player.setBoard(board1);
        board1.setDisable(true);

        ToggleGroup options = new ToggleGroup();

        ToggleButton soldier = new ToggleButton("soldier");
        soldier.setStyle("-fx-background-color : #BC8F8F ");
        soldier.setEffect(new DropShadow());
        soldier.setToggleGroup(options);
        soldier.setPrefSize(120, 40);
        soldier.setAlignment(Pos.CENTER);
        soldier.setFont(Font.font("Kristen ITC", FontWeight.BOLD, 16));
        soldier.setOnMouseClicked(e1 ->{
            board1.setDefault();
            board1.setSoldier(true);
            board1.setDisable(false);

        });

        ToggleButton cavalier = new ToggleButton("cavalier");
        cavalier.setStyle("-fx-background-color : #BC8F8F ");
        cavalier.setEffect(new DropShadow());
        cavalier.setToggleGroup(options);
        cavalier.setPrefSize(120, 40);
        cavalier.setAlignment(Pos.CENTER);
        cavalier.setFont(Font.font("Kristen ITC", FontWeight.BOLD, 16));
        cavalier.setOnMouseClicked(e ->{
            board1.setDefault();
            board1.setCavalier(true);
            board1.setDisable(false);
        });

        ToggleButton castle = new ToggleButton("castle");
        castle.setStyle("-fx-background-color : #BC8F8F ");
        castle.setEffect(new DropShadow());
        castle.setToggleGroup(options);
        castle.setPrefSize(120, 40);
        castle.setAlignment(Pos.CENTER);
        castle.setFont(Font.font("Kristen ITC", FontWeight.BOLD, 16));
        castle.setOnMouseClicked(e ->{
            board1.setDefault();
            board1.setCastle(true);
            board1.setDisable(false);
        });

        ToggleButton head = new ToggleButton("head");
        head.setStyle("-fx-background-color : #BC8F8F ");
        head.setEffect(new DropShadow());
        head.setToggleGroup(options);
        head.setPrefSize(120, 40);
        head.setAlignment(Pos.CENTER);
        head.setFont(Font.font("Kristen ITC", FontWeight.BOLD, 16));
        head.setOnMouseClicked(e ->{
            board1.setDefault();
            board1.setHeadQuarter(true);
            board1.setDisable(false);
        });


        BorderPane layout = new BorderPane();

        Button next = new Button("next");
        next.setStyle("-fx-background-color :#BC8F8F ");
        next.setEffect(new DropShadow());
        next.setFont(Font.font("Kristen ITC", 15));
        next.setOnAction(e -> {
            board1.setDefault();
            window.setScene(new Scene(choosePos2(player2), 1000, 750));
        });

        Label title = new Label(Name1);
        title.setFont(Font.font("Kristen ITC", FontWeight.BOLD, 35));

        VBox fighterBox = new VBox();
        fighterBox.getChildren().addAll(soldier, cavalier, castle, head, next);
        fighterBox.setAlignment(Pos.CENTER);
        fighterBox.setPadding(new Insets(50, 50, 10, 10));
        fighterBox.setSpacing(20);

        VBox board = new VBox();
        board.getChildren().add(board1);
        board.setAlignment(Pos.CENTER);
        board.setPadding(new Insets(20, 10, 10, 150));

        layout.setTop(title);
        layout.setRight(fighterBox);
        layout.setCenter(board);

        StackPane root = new StackPane();
        root.getChildren().addAll(iv, layout);

        return root;
    }

    //_______choosing the position of fighters : player2
    private Parent choosePos2(Player player)
    {
        Image image = new Image("file:texture2.jpg");
        ImageView iv = new ImageView(image);
        iv.setFitHeight(750);
        iv.setFitWidth(1000);

        board2 = new Board(player, player1);
        board2.setPosMode(true);
        board2.ColorCells();
        player.setBoard(board2);
        board2.setDisable(true);

        ToggleGroup options = new ToggleGroup();

        ToggleButton soldier = new ToggleButton("soldier");
        soldier.setStyle("-fx-background-color : #BC8F8F ");
        soldier.setEffect(new DropShadow());
        soldier.setToggleGroup(options);
        soldier.setPrefSize(120, 40);
        soldier.setAlignment(Pos.CENTER);
        soldier.setFont(Font.font("Kristen ITC", FontWeight.BOLD, 16));
        soldier.setOnMouseClicked(e1 ->{
            board2.setDefault();
            board2.setSoldier(true);
            board2.setDisable(false);

        });

        ToggleButton cavalier = new ToggleButton("cavalier");
        cavalier.setStyle("-fx-background-color : #BC8F8F ");
        cavalier.setEffect(new DropShadow());
        cavalier.setToggleGroup(options);
        cavalier.setPrefSize(120, 40);
        cavalier.setAlignment(Pos.CENTER);
        cavalier.setFont(Font.font("Kristen ITC", FontWeight.BOLD, 16));
        cavalier.setOnMouseClicked(e ->{
             board2.setDefault();
            board2.setCavalier(true);
            board2.setDisable(false);
        });

        ToggleButton castle = new ToggleButton("castle");
        castle.setStyle("-fx-background-color : #BC8F8F ");
        castle.setEffect(new DropShadow());
        castle.setToggleGroup(options);
        castle.setPrefSize(120, 40);
        castle.setAlignment(Pos.CENTER);
        castle.setFont(Font.font("Kristen ITC", FontWeight.BOLD, 16));
        castle.setOnMouseClicked(e ->{
            board2.setDefault();
            board2.setCastle(true);
            board2.setDisable(false);
        });

        ToggleButton head = new ToggleButton("head");
        head.setStyle("-fx-background-color : #BC8F8F ");
        head.setEffect(new DropShadow());
        head.setToggleGroup(options);
        head.setPrefSize(120, 40);
        head.setAlignment(Pos.CENTER);
        head.setFont(Font.font("Kristen ITC", FontWeight.BOLD, 16));
        head.setOnMouseClicked(e ->{
            board2.setDefault();
            board2.setHeadQuarter(true);
            board2.setDisable(false);
        });

        BorderPane layout = new BorderPane();

        Button next = new Button("next");
        next.setStyle("-fx-background-color : #BC8F8F ");
        next.setEffect(new DropShadow());
        next.setFont(Font.font("Kristen ITC", 15));
        next.setOnAction(e -> {
            board2.setDefault();
            window.setScene(new Scene(StartGame()));
        });

        Label title = new Label(Name2);
        title.setFont(Font.font("Kristen ITC", FontWeight.BOLD, 35));

        VBox fighterBox = new VBox();
        fighterBox.getChildren().addAll(soldier, cavalier, castle, head, next);
        fighterBox.setAlignment(Pos.CENTER);
        fighterBox.setSpacing(20);
        fighterBox.setPadding(new Insets(50, 50, 10, 10));

        VBox board = new VBox();
        board.getChildren().add(board2);
        board.setAlignment(Pos.CENTER);
        board.setPadding(new Insets(20, 10, 10, 150));

        layout.setTop(title);
        layout.setRight(fighterBox);
        layout.setCenter(board);

        StackPane root = new StackPane();
        root.getChildren().addAll(iv, layout);

        return root;
    }

    //_______starting the game
    private Parent StartGame()
    {
        HBox titles = new HBox();
        Label title = new Label(player2.getName() + "                      " + player1.getName());
        title.setTextFill(Color.BLACK);
        title.setFont(Font.font("chiller", FontWeight.BOLD, 60));
        title.setEffect(new Glow());
        title.setPadding(new Insets(20));

        titles.getChildren().addAll(title);
        titles.setAlignment(Pos.CENTER);

        Image image = new Image("file:5.jpg");
        ImageView iv = new ImageView(image);
        iv.setFitHeight(900);
        iv.setFitWidth(1600);

        window.setResizable(true);
        board1.setDefault();
        board2.setDefault();
        board1.setColor(Color.LIGHTBLUE);
        board2.setColor(Color.LIGHTBLUE);

        HBox container = new HBox();

        container.getChildren().addAll(board2, board1);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(50));
        container.setSpacing(50);

        VBox fighters = new VBox();

        ToggleGroup group = new ToggleGroup();
        RadioButton soldier = new RadioButton("soldier");
        soldier.setPrefSize(120, 50);
        soldier.setFont(Font.font("Kristen ITC", FontWeight.BOLD, 20));
        soldier.setToggleGroup(group);

        RadioButton cavalier = new RadioButton("cavalier");
        cavalier.setPrefSize(120, 50);
        cavalier.setFont(Font.font("Kristen ITC", FontWeight.BOLD, 20));
        cavalier.setToggleGroup(group);

        RadioButton castle = new RadioButton("castle");
        castle.setPrefSize(120, 50);
        castle.setFont(Font.font("Kristen ITC", FontWeight.BOLD, 20));
        castle.setToggleGroup(group);

        RadioButton head = new RadioButton("head");
        head.setPrefSize(150, 50);
        head.setFont(Font.font("Kristen ITC", FontWeight.BOLD, 20));
        head.setToggleGroup(group);
        Button attack = new Button("ATTACK");
        attack.setFont(Font.font("Comic Sans MS", 15));
        attack.setStyle("-fx-background-color :#F08080 ");
        attack.setEffect(new DropShadow());
        attack.setOnAction(e -> {
            if(! endOftheGame)
            {
                if(player1.isTurn())
                {
                     tryAgain = false;      //if the selected fighter is not available, tryAgain will be true
                     board2.setDefault();
                     attack(board2, soldier, cavalier, castle, head);
                     if(!tryAgain)          //if the chosen fighter is available
                         board2.AttackCell();
                }
                else if(player2.isTurn())
                {
                     tryAgain = false;
                     board1.setDefault();
                     attack(board1, soldier, cavalier, castle, head);
                     if(!tryAgain)
                         board1.AttackCell();
                }
            }
            if(theEnd())
            {
                endOftheGame = true;
                board1.setDisable(true);
                board2.setDisable(true);
                winnerBox();
            }
        });

        fighters.getChildren().addAll(soldier, cavalier, castle, head, attack);
        fighters.setSpacing(25);
        fighters.setAlignment(Pos.CENTER_LEFT);
        fighters.setPadding(new Insets(10, 10, 120, 10));

        BorderPane root = new BorderPane();
        root.setCenter(container);
        root.setRight(fighters);
        root.setTop(titles);

        StackPane s = new StackPane();
        s.getChildren().addAll(iv, root);
        return s;
    }

    //_______a method for handling radio buttons clicks
    private void attack(Board board, RadioButton r1, RadioButton r2, RadioButton r3, RadioButton r4)
    {
        board.setDisable(false);
        board.setAttackMode(true);
        board.getRival().getBoard().setDisable(true);
        if(r1.isSelected())
        {
            if(board.getRival().getNumberOfSoldiers() == 0)
            {
                ConfirmBox.sorry("Oops! you've ran out of soldiers");
                tryAgain = true;
            }
            else
                board.setSoldier(true);

        }
        else if(r2.isSelected())
        {
            if(board.getRival().getNumberOfCavaliers() == 0)
            {
                ConfirmBox.sorry("Oops! you've ran out of cavaliers");
                tryAgain = true;
            }
            else
                board.setCavalier(true);
        }
        else if(r3.isSelected())
        {
            if(board.getRival().getNumberOfCastles() == 0)
            {
                ConfirmBox.sorry("Oops! you've ran out of castles");
                tryAgain = true;
            }
            else
                board.setCastle(true);
        }
        else if(r4.isSelected())
        {
            if(board.getRival().getNumberOfHeads() == 0)
            {
                ConfirmBox.sorry("Oops! you've ran out of center");
                tryAgain = true;
            }
            else
                board.setHeadQuarter(true);
        }
        if(tryAgain)
            board.setDisable(true);



    }

    //________checking if the game has ended
    private boolean theEnd()
    {
        if(player1.getNumberOfWarriors() == 0)
            winner = player2.getName();
        else
            winner = player1.getName();

        return player1.getNumberOfWarriors() == 0 || player2.getNumberOfWarriors() == 0;
    }

    //________a window for showing the winner of the game
    private void winnerBox()
    {
        Stage Window = new Stage();
        Window.initModality(Modality.APPLICATION_MODAL);
        Window.setTitle("end!");
        Window.setMinWidth(250);

        Label label = new Label( winner + " is the winner of the battle!");
        label.setFont(Font.font("Aldhabi", 50));
        label.setTextFill(Color.CRIMSON);
        VBox vbox = new VBox();
        HBox hbox = new HBox();

        Button exit = new Button("Exit");
        exit.setFont(Font.font("Comic Sans MS", 15));
        exit.setStyle("-fx-background-color :#F08080 ");
        exit.setEffect(new DropShadow());

        Button menu = new Button("Main Menu");
        menu.setFont(Font.font("Comic Sans MS", 15));
        menu.setStyle("-fx-background-color :#F08080 ");
        menu.setEffect(new DropShadow());

        exit.setOnAction(e -> System.exit(0));
        menu.setOnAction(e -> {
            window.close();
            Window.setScene(scene);
        });

        hbox.getChildren().addAll(exit, menu);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(30);

        vbox.getChildren().addAll(label, hbox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        Window.setResizable(false);
        Window.setScene(new Scene(vbox, 600, 300));
        Window.showAndWait();
    }

}



