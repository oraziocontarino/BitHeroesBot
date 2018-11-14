package lib;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Webpack {
	private static final String[] resources = {
			"resources/css/bootstrap-3.3.7.min.css",
			"resources/css/bootstrap-theme-3.3.7.min.css",
			"resources/css/bootstrap-toggle.min.css",
			"resources/css/style.css"
	};
	private static final String[] folders = {
			"BitHeroesBot",
			"BitHeroesBot/resources",
			"BitHeroesBot/resources/css",
			"BitHeroesBot/resources/fonts",
			"BitHeroesBot/resources/imgs",
			"BitHeroesBot/resources/js",
			"BitHeroesBot/resources/screens"
	};

	private static final String TEMP_FOLDER = "/E:/Desktop/test";
	private static final String APP_TEMP_ANCHOR = "BitHeroesBotAnchor_";
	public static void main(String[] argv) {
		initFiles();
	}
	
	private static void initFolders() {
		for(String folder : folders) {
			//TODO: implement mkdirs as follow:
			/*
			success = (new File("../potentially/long/pathname/without/all/dirs")).mkdirs();
			if (!success) {
			    // Directory creation failed
			}
			*/
		}
	}
	private static void initFiles() {
		try{
			String temp_absolute_folder = getAbsolutePath();
			System.out.println(temp_absolute_folder);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		initFolders();
		System.out.println("GONNA CRASH DUE TO MISSING FOLDERS THREE!");
		for(String resource : resources) {
			try {
				File source = new File(resource);
				File dest = new File(TEMP_FOLDER+"/"+resource);
				if(dest.exists()) {
					System.out.println("EXISTS!");
				}else {
					System.out.println("NOT EXISTS");
				}
				System.out.println("Gonna copy: "+source.toPath()+" to: "+dest.toPath());
				Files.copy(source.toPath(), dest.toPath());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static String getAbsolutePath() throws IOException{
		//TODO: store string and delete file
		File temp = File.createTempFile(APP_TEMP_ANCHOR, ".tmp");
		return temp.getAbsolutePath();
	}
}
