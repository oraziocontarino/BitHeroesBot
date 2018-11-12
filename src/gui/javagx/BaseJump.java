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
import netscape.javascript.JSObject;

@SuppressWarnings("restriction")
public class BaseJump extends Application {
	private static final String APPLICATION_PAGE = "/screens/index.html";
	private static String applicationPagePath;
	private StopBotTask testTask;
	private ExecutorService executorService;
	private WebEngine engine;
	private JavaBridge bridge;
	private JSObject window;
	private WebView webview;
	private Stage stage;
	private Scene scene;
	
	public static void main(String[] args) {
		applicationPagePath = BaseJump.class.getResource(APPLICATION_PAGE).toExternalForm();
		System.out.println(applicationPagePath);
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		KeyBindingManager.getInstance().setApplication(this);
		this.stage = stage;
		this.webview = new WebView();
		this.bridge = new JavaBridge(engine);
		this.engine = this.webview.getEngine();
		this.window = (JSObject) this.engine.executeScript("window");
		this.initJavascriptBridges();
		this.engine.load(this.applicationPagePath);
		this.initWebview();
	}	
	
	private void initWebview() {
		this.webview.setMaxHeight(700);
		this.webview.setMinHeight(700);
		this.webview.setMaxWidth(800);
		this.webview.setMinWidth(800);
		this.initScene();
	}
	
	private void initScene() {
		VBox layout = new VBox();
		layout.getChildren().addAll(this.webview);
		this.scene = new Scene(layout);
		this.initStage();
	}
	
	private void initStage() {
		// stage.initStyle(StageStyle.UNDECORATED);
		this.stage.setScene(scene);
		this.stage.setMaxHeight(700);
		this.stage.setMinHeight(700);
		this.stage.setMaxWidth(800);
		this.stage.setMinWidth(800);
		this.stage.setResizable(false);
		this.stage.setOnCloseRequest(event -> {
			try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e) {
				e.printStackTrace();
			}
			System.exit(0);
		});
		this.stage.show();		
	}
	
	private void initJavascriptBridges() {
		ConfigurationRequestManager requestManager = new ConfigurationRequestManager();
		engine.setOnAlert((WebEvent<String> event2) -> requestManager.handleRequest(engine, event2.getData()));
		engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) ->
		{
		    window.setMember("java", bridge);
		    engine.executeScript(bridge.getBridge());
		});
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
	//TODO: bug update raid/mission autocheck not working
	//TODO: bug start D4
}