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
	private HashMap<String, String> settingsmap; // Project Ouh-specific settings
	private HashMap<String, String> settingsbackup; // backup used before
																									// executing applySettings()

	public Settings() {
		appsettings = new AppSettings(true);
		settingsmap = new HashMap<String, String>();
		settingsbackup = new HashMap<String, String>();
		readSettings();
	}

	public AppSettings getAppSettings() { // JME settings are changed manually
		return appsettings;
	}

	public String getSetting(String key) {
		return settingsmap.get(key);
	}

	public void setSetting(String key, String value) {
		settingsbackup.put(key, value);
	}

	private void readSettings() { // Reading settings from settings file
		try {
			Scanner scanner = new Scanner(new File("settings.txt"));
			while (scanner.hasNext()) {
				String option = scanner.nextLine();
				if (option.trim().charAt(0) != '#') { // '#' means the line will be
																							// ignored
					String[] splitstring = option.split("=");
					settingsmap.put(splitstring[0], splitstring[1]);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			try {
				new File("settings.txt").createNewFile();
			} catch (IOException e1) {
			}
		}

	}

	// applying settings to both the currently used
	// data and the text file, method has to be
	// called manually after setting something
	public void applySettings() {
		settingsmap.putAll(settingsbackup);
		settingsbackup.clear();
		new File("settings.txt").delete();

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
					"settings.txt")));
			String txtcontent = settingsmap.toString().replace("{", "")
					.replace("}", "").replace(",", "\n");
			writer.write(txtcontent);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
