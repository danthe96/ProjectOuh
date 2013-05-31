package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import com.jme3.system.AppSettings;

public class Settings {
	private AppSettings appsettings; // JME settings, which manage things like
																		// vsync, framerate, etc.
	private HashMap<String, HashMap<String, String>> settingsmap;
	// Project Ouh-specific settings
	private HashMap<String, HashMap<String, String>> settingsbackup;
	// backup used before executing applySettings()

	// We use a HashMap inside a HashMap to establish different categories

	public Settings() {
		appsettings = new AppSettings(true);
		settingsmap = new HashMap<String, HashMap<String, String>>();
		settingsbackup = new HashMap<String, HashMap<String, String>>();
		readSettings();
	}

	public AppSettings getAppSettings() { // JME settings are changed manually
		return appsettings;
	}

	public String getSetting(String category, String key) {
		return settingsmap.get(category).get(key);
	}

	public void setSetting(String category, String key, String value) {
		HashMap<String, String> temp_settingsbackup = new HashMap<String, String>();
		temp_settingsbackup.put(key, value);
		settingsbackup.put(category, temp_settingsbackup);
	}
	
	public HashMap<String,String> getSettingsMap(String category){
		return settingsmap.get(category);
	}
	
	// applying settings to both the currently used
	// data and the text file, method has to be
	// called manually after setting something
	public void applySettings() {
		for (String category : settingsmap.keySet())
			settingsmap.get(category).putAll(settingsbackup.get(category));
		settingsbackup.clear();
		new File("settings.txt").delete();

		writeHashMapHashMaptoFile(settingsmap, "settings.txt");

	}

	private void readSettings() { // Reading settings from settings file
		try {
			Scanner scanner = new Scanner(new File("settings.txt"));
			String option = scanner.nextLine().trim();
			while (scanner.hasNext()) {
				if (!option.equals("") && option.charAt(0) == '#') {
					String category = option.replace("#", "");
					HashMap<String, String> tempmap = new HashMap<String, String>();
					do {
						option = scanner.nextLine().trim();
						if (option.charAt(0) != '#') {
							String[] splitstring = option.split("=");
							tempmap.put(splitstring[0], splitstring[1]);
						}
					} while (option.charAt(0) != '#' && scanner.hasNext());
					settingsmap.put(category, tempmap);
				} else {
					option = scanner.nextLine().trim();
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			settingsmap.putAll(StandardSettings.get());
			writeHashMapHashMaptoFile(settingsmap, "settings.txt");
		}

	}
	
	private void writeHashMapHashMaptoFile(
			HashMap<String, HashMap<String, String>> map, String filepath) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
					filepath)));
			StringBuffer txtcontent = new StringBuffer();
			for (String category : map.keySet()) {
				txtcontent.append("\n#"
						+ category
						+ "\n"
						+ map.get(category).toString().replace("{", "").replace("}", "")
								.replace(", ", "\n"));
			}
			writer.write(txtcontent.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
