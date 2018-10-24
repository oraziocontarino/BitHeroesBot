package gui.javagx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BaseJump extends Application {
	private static final String APPLICATION_PAGE = "/screens/index.html";
	private static String applicationPagePath;

	@Override public void start(Stage stage) throws Exception {
		final WebView webview = new WebView();

		webview.setMaxHeight(700);
		webview.setMinHeight(700);
		webview.setMaxWidth(800);
		webview.setMinWidth(800);
		VBox layout = new VBox();
		layout.getChildren().addAll(
			webview
		);
		
		WebEngine engine = webview.getEngine();
		engine.load(applicationPagePath);
		engine.setOnAlert((WebEvent<String> event2) -> test(engine, event2.getData()));
		

		Scene scene = new Scene(layout);
		//stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);
		stage.setMaxHeight(700);
		stage.setMinHeight(700);
		stage.setMaxWidth(800);
		stage.setMinWidth(800);
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) { 
		applicationPagePath = BaseJump.class.getResource(APPLICATION_PAGE).toExternalForm();
		System.out.println(applicationPagePath);
		launch(args); 
	}

	public void test(WebEngine engine, String s) {
		ConfigurationRequestManager.handleRequest(engine, s);
	}
}