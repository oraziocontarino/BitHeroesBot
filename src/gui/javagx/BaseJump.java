package gui.javagx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
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
		ConfigurationRequestManager requestManager = new ConfigurationRequestManager();
		engine.setOnAlert((WebEvent<String> event2) -> requestManager.handleRequest(engine, event2.getData()));
		

		Scene scene = new Scene(layout);
		//stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);
		stage.setMaxHeight(700);
		stage.setMinHeight(700);
		stage.setMaxWidth(800);
		stage.setMinWidth(800);
		stage.setResizable(false);	
		//TODO: set on close operation
		stage.show();
	}

	public static void main(String[] args) { 
		applicationPagePath = BaseJump.class.getResource(APPLICATION_PAGE).toExternalForm();
		System.out.println(applicationPagePath);
		launch(args); 
	}
}