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

	private void readSettings() { // Reading settings from settings file
		try {
			Scanner scanner = new Scanner(new File("settings.txt"));
			while (scanner.hasNext()) {
				String option = scanner.nextLine();
				if (!option.equals("") && option.trim().charAt(0) == '#') {
					String category = option.trim().replace("#", "");
					HashMap<String, String> tempmap = new HashMap<String, String>();
					do {
						option = scanner.nextLine();
						if (option.trim().charAt(0) != '#' && scanner.hasNext()) {
							String[] splitstring = option.split("=");
							tempmap.put(splitstring[0], splitstring[1]);
						}
					} while (option.trim().charAt(0) != '#' && scanner.hasNext());
					settingsmap.put(category, tempmap);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			settingsmap.putAll(StandardSettings.get());
			writeHashMapHashMaptoFile(settingsmap, "settings.txt");
		}

		for (String category : settingsmap.keySet())
			System.out.println(settingsmap.get(category).toString());

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
