package lib;

import java.util.logging.LogManager;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import be.AsyncBot;

public class KeyBindingManager  implements NativeKeyListener {
	private static KeyBindingManager instance;
	private KeyBindingManager() {
		initKeyBinding();
	}
	
	public static KeyBindingManager getInstance() {
		if(instance == null){
            instance = new KeyBindingManager();
        }
        return instance;
	}
	
	/* KEY EVENTS */
    
	private void initKeyBinding() {
		try {
			LogManager.getLogManager().reset();
			GlobalScreen.setEventDispatcher(new SwingDispatchService());
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(this);
	}

	
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		//System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

		if (e.getKeyCode() == NativeKeyEvent.VC_TAB) {
			AsyncBot.getInstance().interrupt();
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {	
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {	
	}   
    

}
