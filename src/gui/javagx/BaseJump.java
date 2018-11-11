package gui.javagx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import lib.KeyBindingManager;

@SuppressWarnings("restriction")
public class BaseJump extends Application {
	private static final String APPLICATION_PAGE = "/screens/index.html";
	private static String applicationPagePath;
	private StopBotTask testTask;
	private ExecutorService executorService;
	private WebEngine engine;
	@Override
	public void start(Stage stage) throws Exception {
		final WebView webview = new WebView();

		webview.setMaxHeight(700);
		webview.setMinHeight(700);
		webview.setMaxWidth(800);
		webview.setMinWidth(800);
		VBox layout = new VBox();
		layout.getChildren().addAll(webview);

		engine = webview.getEngine();
		engine.load(applicationPagePath);
		ConfigurationRequestManager requestManager = new ConfigurationRequestManager();
		engine.setOnAlert((WebEvent<String> event2) -> requestManager.handleRequest(engine, event2.getData()));

		Scene scene = new Scene(layout);
		// stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);
		stage.setMaxHeight(700);
		stage.setMinHeight(700);
		stage.setMaxWidth(800);
		stage.setMinWidth(800);
		stage.setResizable(false);
		stage.setOnCloseRequest(event -> {
			try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e) {
				e.printStackTrace();
			}
			System.exit(0);
		});
		stage.show();		
		KeyBindingManager.getInstance().setApplication(this);
	}	

	public static void main(String[] args) {
		applicationPagePath = BaseJump.class.getResource(APPLICATION_PAGE).toExternalForm();
		System.out.println(applicationPagePath);
		launch(args);
	}

	public void executeStopTask() {
		testTask = new StopBotTask();

		testTask.setOnRunning((succeesesEvent) -> {
			//...
		});

		testTask.setOnSucceeded((succeededEvent) -> {
			engine.executeScript("stopBotTask()");
		});
		this.executorService = Executors.newFixedThreadPool(1);
		this.executorService.execute(testTask);
		this.executorService.shutdown();
	}
	//TODO: bug update raid multiple checkboxes
	//TODO: implement Test function in utils to select raid, it works!
}