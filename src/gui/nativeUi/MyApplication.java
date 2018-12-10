package gui.nativeUi;
import java.awt.Desktop;
import java.io.File;

import lib.Webpack;

public class MyApplication {
	public static void main(String argv[]) throws Exception {
		System.out.println(Webpack.getInstance().getAssetsMap().getString("screens/popup.html"));
		File index = new File(Webpack.getInstance().getAssetsMap().getString("screens/popup.html"));
		if (Desktop.isDesktopSupported()) {
		    Desktop.getDesktop().browse(index.toURI());
		}
		RestletServer.getInstance().startServer();
	}
}
