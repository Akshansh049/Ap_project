package com.example.demo1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class HelloController {
    @FXML
    public static Button increasebutton;
    @FXML
    public Rectangle Stick;

    public Button Audio;
    public ImageView Character;
    public Rectangle Rect_1;
    public Rectangle Rect_2;
    public Button ExitButt;
    private MediaPlayer mediaPlayer;
    private boolean isExtending = false;
    private boolean hasRotated = false;
    private double initialStickHeight = 70; // Store initial stick height
    private double initialCharacterX = 20;
    private Thread extensionThread;
    private boolean isRect2Generated = false;
    private Timeline gameLoop;

    @FXML
    private Stage stage;
    private boolean isPlaying = false;
    private Parent root;
    private Scene scene;


    private String musicfilpath = "C:/Users/Akshansh Astya/IdeaProjects/demo1/src/main/resources/Audio/Don Toliver - After Party (Lyrics).mp3";

    public void switchToscene1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToscene2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view2.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToscene3(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view3.fxml"));
        Parent root = loader.load();
        HelloController controller = loader.getController();
        controller.setRandomLayoutX();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToscene4(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view4.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToscene5(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view5.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToscene6(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view6.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
// A transparent button is there near the stick which initiates the space button function

    public void increaselength(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE) {
            if (!isExtending) {
                isExtending = true;
                hasRotated = false;

                // Start extension thread
                extensionThread = new Thread(() -> {
                    while (isExtending) {
                        Stick.setHeight(Stick.getHeight() + 2);
                        Stick.setLayoutY(Stick.getLayoutY() - 2);

                        if (!isExtending) { // Stop thread on space release
                            extensionThread.interrupt();
                        } else if (Character.getBoundsInParent().intersects(Rect_2.getBoundsInLocal())) {
                            resetGame();
                        }

                        try {
                            Thread.sleep(60); // Adjust delay for desired speed
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                });
                extensionThread.start();
            }
        } else {
            isExtending = false;
            if (!hasRotated) {
                hasRotated = true;

                Rotate rotate = new Rotate();
                rotate.setAngle(90);
                rotate.setPivotX(Stick.getX());
                rotate.setPivotY(Stick.getY() + Stick.getHeight());
                Stick.getTransforms().add(rotate);
                // Move character to the right
                TranslateTransition translate = new TranslateTransition();
                translate.setNode(Character);
                translate.setByX(Stick.getHeight() + 10); // Adjust distance based on desired gap
                translate.play();

                // Move Rect_2 to the left and generate Rect_1 in front
                if (isRect2Generated) {
                    TranslateTransition moveRect2 = new TranslateTransition();
                    moveRect2.setNode(Rect_2);
                    moveRect2.setByX(-Rect_2.getWidth() - 10); // Adjust distance based on desired gap
                    moveRect2.play();

                    // Calculate new X position for Rect_1 in front of Rect_2
                    double newX1 = Rect_2.getLayoutX() + Rect_2.getWidth() + 10;
                    Rect_1.setLayoutX(newX1);
                    isRect2Generated = false;
                }
            }
        }
    }

    private void resetGame() {
        // Reset stick and character to initial positions
        Stick.setHeight(initialStickHeight);
        Stick.setLayoutY(359);
        Character.setLayoutX(initialCharacterX);

        // Reset flags
        hasRotated = false;
        isExtending = false;
        isRect2Generated = true; // Assuming Rect_2 is generated initially
    }

    public void initialize() {
        Media media = new Media(new File(musicfilpath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }
    @FXML
    public void Audioplaypause() {
        if (isPlaying) {
            mediaPlayer.stop();
            isPlaying = false;
            Audio.setText("Play");
        }   else {
            mediaPlayer.play();
            isPlaying = true;
            Audio.setText("Stop");
        }
    }
    public void setRandomLayoutX() {
        if (isRect2Generated) {
            double randomX1 = Math.random() * 300;
            Rect_1.setLayoutX(randomX1);
            isRect2Generated = false;
        } else {
            double randomX2 = Math.random() * 300 + Rect_1.getLayoutX() + Rect_1.getWidth() + 10;
            Rect_2.setLayoutX(randomX2);
            isRect2Generated = true;
        }
    }

    public void ActionExit(ActionEvent event) {
        Platform.exit();
    }
}