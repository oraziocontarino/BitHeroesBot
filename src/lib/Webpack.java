package lib;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.json.JSONObject;

public class Webpack {
	private static final String[] RESOURCES = { 
			"css/bootstrap-3.3.7.min.css",
			"css/bootstrap-theme-3.3.7.min.css",
			"css/bootstrap-toggle.min.css",
			"css/style.css",
			
			"fonts/glyphicons-halflings-regular.eot",
			"fonts/glyphicons-halflings-regular.svg",
			"fonts/glyphicons-halflings-regular.ttf",
			"fonts/glyphicons-halflings-regular.woff",
			"fonts/glyphicons-halflings-regular.woff2",
			"fonts/NotoSans-Black.ttf",
			"fonts/NotoSans-BlackItalic.ttf",
			"fonts/NotoSans-Bold.ttf",
			"fonts/NotoSans-BoldItalic.ttf",
			"fonts/NotoSans-Italic.ttf",
			"fonts/NotoSans-LICENSE_OFL.txt",
			"fonts/NotoSans-Light.ttf",
			"fonts/NotoSans-LightItalic.ttf",
			"fonts/NotoSans-Medium.ttf",
			"fonts/NotoSans-MediumItalic.ttf",
			"fonts/NotoSans-Regular.ttf",
			"fonts/NotoSans-Thin.ttf",
			"fonts/NotoSans-ThinItalic.ttf",

			"imgs/back_arrow.png",
			"imgs/computer.png",
			"imgs/details.png",
			"imgs/down_arrow.png",
			"imgs/file.png",
			"imgs/floppy_drive.png",
			"imgs/folder.png",
			"imgs/hard_drive.png",
			"imgs/home.png",
			"imgs/list.png",
			"imgs/loading.gif",
			"imgs/new_folder.png",
			"imgs/outlined_checked_box.png",
			"imgs/painted_checked_box.png",
			"imgs/radio_button_off.png",
			"imgs/radio_button_on.png",
			"imgs/right_arrow.png",
			"imgs/toggle_off.png",
			"imgs/toggle_on.png",
			"imgs/unchecked_box.png",
			"imgs/up_arrow.png",

			"js/bootstrap-3.3.7.min.js",
			"js/bootstrap-toggle.min.js",
			"js/java-requests.js",
			"js/jquery-3.3.1.min.js",
			"js/script.js",

			"screens/popup-open.html",
			"screens/popup-close.html",
			"screens/index.html"
	};

	private static final String[] FOLDERS = { "css", "fonts", "imgs", "js", "screens" };

	private static final String APP_TEMP_ANCHOR = "BitHeroesBotAnchor_";
	
	private String system_temp_folder;
	private static Webpack instance;
	
	private Webpack() {
		system_temp_folder = getAbsolutePath();
		initFiles();
		//System.out.println("TEMP: "+system_temp_folder);
	}
	
	public static Webpack getInstance() {
		if(instance == null) {
			instance = new Webpack();
		}
		return instance;		
	}
	
	public JSONObject getAssetsMap() {
		JSONObject assets = new JSONObject();
		for(String resource : RESOURCES) {
			assets.put(resource, this.system_temp_folder + File.separator + APP_TEMP_ANCHOR + File.separator + resource);
		}
		
		return assets;	
	}
	
	private boolean initFolders() {
		boolean success = true;

		for (String folder : FOLDERS) {
			File folderObject = new File(system_temp_folder + File.separator + APP_TEMP_ANCHOR + File.separator + folder);
			if (folderObject.exists()) {
				// System.out.println("Folder: "+basePath+File.separator+folder+" skipped!");
				continue;
			}

			success = success && folderObject.mkdirs();
			if (!success) {
				break;
			}
			// System.out.println("Folder: "+basePath+File.separator+folder+" created!");
		}
		return success;
	}

	private boolean initFiles() {
		boolean success = true;
		if (system_temp_folder == null) {
			System.out.println("Error occurred while getting temp folder path");
			return false;
		}
		if (!initFolders()) {
			System.out.println("Error occurred while initializing folders");
			return false;
		}

		for (String resource : RESOURCES) {
			try {
				InputStream resourceInputStream = accessFile(resource);
				if (resourceInputStream == null) {
					System.out.println("Error occurred while initializing resource: " + resource);
					return false;
				}
				File dest = new File(system_temp_folder + File.separator + APP_TEMP_ANCHOR + File.separator + resource);
				Files.copy(resourceInputStream, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
				resourceInputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		return success;
	}

	private String getAbsolutePath() {
		String path = null;
		try {
			File temp = File.createTempFile(APP_TEMP_ANCHOR, ".tmp");
			path = temp.getParentFile().getAbsolutePath();
			temp.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	private InputStream accessFile(String filePathFromResoucesFolder) {
		//REAL PATH: /resources/css/bootstrap-3.3.7.min.css
		// String filePathFromResoucesFolder = "css/bootstrap-3.3.7.min.css";

		// this is the path within the jar file
		//InputStream input = Webpack.class.getResourceAsStream("/resources/" + filePathFromResoucesFolder);
		//if (input == null) {
			// this is how we load file within editor (eg eclipse)
			//input = Webpack.class.getClassLoader().getResourceAsStream(filePathFromResoucesFolder);
		//}		
		return Webpack.class.getClassLoader().getResourceAsStream(filePathFromResoucesFolder);
	}

	
}
