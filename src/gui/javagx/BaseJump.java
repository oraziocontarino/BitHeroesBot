package gui.javagx;

import java.io.File;
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
import lib.Webpack;
import netscape.javascript.JSObject;

@SuppressWarnings("restriction")
public class BaseJump extends Application {
	private StopBotTask testTask;
	private ExecutorService executorService;
	private WebEngine engine;
	private JavaBridge bridge;
	private JSObject window;
	private WebView webview;
	private Stage stage;
	private Scene scene;
	private String indexPath;
	
	public static void main(String[] args) {


		//applicationPagePath = BaseJump.class.getResource(APPLICATION_PAGE).toExternalForm();
		System.out.println("file:/"+Webpack.getInstance().getAssetsMap().getString("screens/index.html"));
		launch(args);
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		KeyBindingManager.getInstance().setApplication(this);
		this.stage = stage;
		this.webview = new WebView();
		this.engine = this.webview.getEngine();
		this.bridge = new JavaBridge(engine);
		this.window = (JSObject) this.engine.executeScript("window");
		this.initJavascriptBridges();

		//this.engine.load("file:/"+Webpack.getInstance().getAssetsMap().getString("screens/index.html"));
		File index = new File(Webpack.getInstance().getAssetsMap().getString("screens/index.html"));
		this.engine.load(index.toURI().toString());
		this.initWebview();
	}	
	
	private void initWebview() {
		this.webview.setMaxHeight(750);
		this.webview.setMinHeight(750);
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
		this.stage.setMaxHeight(750);
		this.stage.setMinHeight(750);
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
		this.stage.setAlwaysOnTop(true);
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
	//TODO: optimize kill flags
	//TODO: implement task order management
	//TODO: implement fishing event
	//TODO: handle reconnect
	//TODO: handle midnight event reset
	//TODO: LOG disabled to debug, comment 'return;' in script.js:257 to active log again!
}