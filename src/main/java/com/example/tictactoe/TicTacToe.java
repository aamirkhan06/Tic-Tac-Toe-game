package com.example.tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TicTacToe extends Application {

    private Button buttons [][] = new Button[3][3];
    private Label playerXScoreLabel, playerOScoreLabel;

    private int playerXScore = 0, playerOScore = 0;
    private boolean playerXTurn = true;
    private BorderPane createContent(){
        BorderPane root=new BorderPane();
        root.setPadding(new Insets(20));
        //title
        Label titleLabel = new Label("Tic-Tac-toe");
        titleLabel.setStyle("-fx-font-size : 35pt; -fx-font-weight : bold;");
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setTop(titleLabel);

        //Game Board
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                Button button = new Button();
                button.setPrefSize(200,200);
                //button.setStyle("-fx-font-size : 24pt; -fx-font-weight : bold;");
                button.setOnAction(event->buttonClicked(button)); // to set the action on buttons
                buttons[i][j] = button;
                gridPane.add(button,j,i);
            }
        }

        root.setCenter(gridPane);
        //BorderPane.setAlignment(gridPane, Pos.CENTER);
        // Score
        HBox scoreBoard=new HBox(20);
        scoreBoard.setAlignment(Pos.CENTER);
        scoreBoard.setSpacing(350);
        playerXScoreLabel=new Label("Player X : 0");
        playerXScoreLabel.setStyle("-fx-font-size : 20pt; -fx-font-weight : bold; -fx-text-fill : blue");
        playerOScoreLabel=new Label("Player O : 0");
        playerOScoreLabel.setStyle("-fx-font-size : 20pt; -fx-font-weight : bold; -fx-text-fill : red");
        scoreBoard.getChildren().addAll(playerXScoreLabel,playerOScoreLabel);

        root.setBottom(scoreBoard);

        return root;
    }

    private void buttonClicked(Button button)
    {
        if(button.getText().isEmpty()) // if the button is not marked either "x" or "O"
        {
            if (playerXTurn) {
                button.setText("X");
                button.setStyle("-fx-font-size : 70pt; -fx-font-weight : bold; -fx-text-fill : blue");
            } else {
                button.setText("O");
                button.setStyle("-fx-font-size : 70pt; -fx-font-weight : bold; -fx-text-fill : red");
            }
            playerXTurn = !playerXTurn;
            checkWinner();
        }
    }

    private void checkWinner()
    {
        //row
        for (int row = 0; row < 3; row++)
        {
            if(buttons[row][0].getText().equals(buttons[row][1].getText())
                    && buttons[row][1].getText().equals(buttons[row][2].getText())
                    && !buttons[row][0].getText().isEmpty())
            {
                // we will have a winner
                String winner = buttons[row][0].getText();
                showWinnerDialog(winner);
                updateScore(winner);
                resetBoard();
                return;
            }
        }

        //column
        for (int col = 0; col < 3; col++)
        {
            if(buttons[0][col].getText().equals(buttons[1][col].getText())
                    && buttons[1][col].getText().equals(buttons[2][col].getText())
                    && !buttons[0][col].getText().isEmpty())
            {
                // we will have a winner
                String winner = buttons[0][col].getText();
                showWinnerDialog(winner);
                updateScore(winner);
                resetBoard();
                return;
            }
        }

        //diagonal
        if(buttons[0][0].getText().equals(buttons[1][1].getText())
                && buttons[1][1].getText().equals(buttons[2][2].getText())
                && !buttons[0][0].getText().isEmpty())
        {
            // we will have a winner
            String winner = buttons[0][0].getText();
            showWinnerDialog(winner);
            updateScore(winner);
            resetBoard();
            return;
        }
        if(buttons[0][2].getText().equals(buttons[1][1].getText())
                && buttons[1][1].getText().equals(buttons[2][0].getText())
                && !buttons[0][2].getText().isEmpty())
        {
            // we will have a winner
            String winner = buttons[0][2].getText();
            showWinnerDialog(winner);
            updateScore(winner);
            resetBoard();
            return;
        }

        //tie
        boolean tie=true;
        for(Button row[]: buttons)
        {
            for(Button button: row)
            {
                if(button.getText().isEmpty())
                {
                    tie = false;
                    break;
                }
            }
        }
        if(tie)
        {
            showTieDialog();
            resetBoard();
        }
    }

    private void showWinnerDialog(String winner)
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Winner");
        alert.setContentText("Congratulations " + winner + "! you won the game");
        alert.setHeaderText("");
        alert.showAndWait();
    }

    private void showTieDialog()
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Draw");
        alert.setContentText("Game Over! It's a Tie");
        alert.setHeaderText("");
        alert.showAndWait();
    }

    private void updateScore(String winner)
    {
        if(winner.equals("X"))
        {
            playerXScore++;
            playerXScoreLabel.setText("Player X : "+playerXScore);
        }
        else
        {
            playerOScore++;
            playerOScoreLabel.setText("Player O : "+ playerOScore);
        }
    }

    private void resetBoard()
    {
        for(Button row[]: buttons)
        {
            for(Button button: row)
            {
                button.setText("");
            }
        }
    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}