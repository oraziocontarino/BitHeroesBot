package gui.javagx;

import javafx.scene.web.WebEngine;

public class JavaBridge
{
	private final String[] BRIDGE = {
			"console.log = function(message) { java.log(message); };",
			"console.warn = function(message) { java.warn(message); };",
			"console.error = function(message) { java.error(message); };",
			//"sendJavaRequest = function(message) { java.sendJavaRequest(message); };"
	};
	private WebEngine engine;
	
	public JavaBridge(WebEngine engine) {
		this.engine = engine;
	}
	public String getBridge() {
		StringBuilder scriptList = new StringBuilder();
		for(String element : BRIDGE) {
			scriptList.append(element+" ");
		}
		return scriptList.toString();
	}
	
    public void log(String text)
    {
        System.out.println("[BROWSER LOG] - "+text);
    }
    public void warn(String text)
    {
        System.out.println("[BROWSER WARN] - "+text);
    }
    public void error(String text)
    {
        System.out.println("[BROWSER ERROR] - "+text);
    }
    /*
    public void sendJavaRequest(String message)
    {
    	System.out.println(message);
    	ConfigurationRequestManager.handleRequest(engine, message);
    }
    */
}
