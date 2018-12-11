package gui.nativeUi;
import java.util.Arrays;
import java.util.HashSet;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.service.CorsService;

import gui.nativeUi.request.CheckServerAliveRequest;
import gui.nativeUi.request.GetDefaultConfigurationRequest;
import gui.nativeUi.request.GetLogsRequest;
import gui.nativeUi.request.SetCoordsRequest;
import gui.nativeUi.request.StartBotRequest;
import gui.nativeUi.request.StopBotRequest;
import gui.nativeUi.request.StopServerRequest;
import gui.nativeUi.request.TestRequest;

public class RestletServer extends Application {
	private static RestletServer instance;
	private final Component component;
	private final Router router;
	
	public static RestletServer getInstance() throws Exception {
		if(instance == null) {	        
	        instance = new RestletServer();
		}
        return instance;
	}
	
	public void startServer() throws Exception {
        // attach the application to the interface
        component.getDefaultHost().attach(instance);
        // go to town
        component.start();
	}

    // just your everyday chaining constructor
    private RestletServer() throws Exception {
    	super();
    	component = new Component();
    	component.getServers().add(Protocol.HTTP, 12345);
    	router = new Router();
    	CorsService corsService = new CorsService();         
        corsService.setAllowedOrigins(new HashSet<String>(Arrays.asList("*")));
        corsService.setAllowedCredentials(true);
    	super.setContext(component.getContext().createChildContext());
        getServices().add(corsService);
    }

    @Override
    public Restlet createInboundRoot() {
    	router.setContext(getContext());
    	router.attach("/getDefaultConfiguration", GetDefaultConfigurationRequest.class);
    	router.attach("/getLogs", GetLogsRequest.class);
    	router.attach("/setCoords", SetCoordsRequest.class);
    	router.attach("/startBot", StartBotRequest.class);
    	router.attach("/stopBot", StopBotRequest.class);
    	router.attach("/isAlive", CheckServerAliveRequest.class);
    	router.attach("/stopServer", StopServerRequest.class);
    	router.attach("/test", TestRequest.class);
        return router;
    }

}