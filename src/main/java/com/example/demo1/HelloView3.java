package com.example.demo1;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;


public class HelloView3 extends StackPane {
    Parent root;
    Scene scene;

    Rectangle Stick;



    public HelloView3(Parent root, Scene scene){
        this.root = root;
        this.scene = scene;
    }
    public void increaselength(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE) {
            System.out.println("hello");
            double newlength = Stick.getHeight() + 5;
            Stick.setHeight(newlength);
            Stick.setLayoutY(Stick.getLayoutY()-5);

        }
    }
}

