package lib;

import java.awt.AWTException;
import java.util.logging.LogManager;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import be.BitHeroesBot;

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
			try {
				BitHeroesBot.getInstance().stop();
				//GlobalScreen.unregisterNativeHook();
				//System.exit(1);
			} catch (AWTException | InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		
	}   
    

}
