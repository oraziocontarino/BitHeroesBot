package gui.javagx;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import org.json.JSONObject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import lib.CustomRobot;

@SuppressWarnings("restriction")
public class BaseJump extends Application {
    private static final String APPLICATION_PAGE = "/screens/index.html";
    private static String applicationPagePath;
    private enum Anchor { progress, jumbotron, badges, pagination }

    @Override public void start(Stage stage) throws Exception {
        final WebView webview = new WebView();

        final ToolBar nav = new ToolBar();
        for (Anchor anchor : Anchor.values()) {
            nav.getItems().add(
                new NavButton(
                    anchor,
                    webview
                )
            );
        }

        VBox layout = new VBox();
        layout.getChildren().addAll(
            nav,
            webview
        );

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { 
    	applicationPagePath = BaseJump.class.getResource(APPLICATION_PAGE).toExternalForm();
    	System.out.println(applicationPagePath);
    	launch(args); 
    }

    private class NavButton extends Button {
        public NavButton(final Anchor anchor, final WebView webview) {
            setText(anchor.toString());
            setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    WebEngine engine = webview.getEngine();
                    engine.load(applicationPagePath);
                    engine.setOnAlert((WebEvent<String> event2) -> test(engine, event2.getData()));
                }
                public void test(WebEngine engine, String s) {
                	ConfigurationRequestManager.handleRequest(engine, s);
                }
            });
        }
    } 
}