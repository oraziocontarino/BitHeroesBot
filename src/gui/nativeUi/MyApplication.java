package gui.nativeUi;
import java.awt.Desktop;
import java.io.File;

import lib.KeyBindingManager;
import lib.Webpack;

public class MyApplication {
	public static void main(String argv[]) throws Exception {
		KeyBindingManager.getInstance();
		RestletServer.getInstance().startServer();
		System.out.println(Webpack.getInstance().getAssetsMap().getString("screens/popup-open.html"));
		File index = new File(Webpack.getInstance().getAssetsMap().getString("screens/popup-open.html"));
		if (Desktop.isDesktopSupported()) {
		   Desktop.getDesktop().browse(index.toURI());
		}
	}
}
