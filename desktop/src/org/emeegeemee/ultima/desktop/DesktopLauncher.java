package org.emeegeemee.ultima.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.google.gson.Gson;
import org.apache.commons.cli.*;
import org.emeegeemee.ultima.Ultima;
import org.emeegeemee.ultima.tiles.TileConfig;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class DesktopLauncher {
	public static void main (String[] args) throws ParseException {
		int logLevel = Application.LOG_NONE;
		String filename = "tileset.gif";
		String tileConfig = "tileset.json";
		int width = 16;
		int height = 16;
		int worldSize = 9;

		try {
			Options options = setupCli();
			CommandLine commandLine = new GnuParser().parse(options, args);

			if(commandLine.hasOption("help")) {
				new HelpFormatter().printHelp("DesktopLauncher", options);
				return;
			}
			else {
				if(commandLine.hasOption("log")) {
					logLevel = Integer.parseInt(commandLine.getOptionValue("log"));
				}

				if(commandLine.hasOption("verbose")) {
					logLevel = Application.LOG_DEBUG;
				}

				if(commandLine.hasOption("worldSize")) {
					worldSize = Integer.parseInt(commandLine.getOptionValue("worldSize"));
				}

				if(logLevel > 3 || logLevel < 0 || worldSize < 5) {
					new HelpFormatter().printHelp("DesktopLauncher", options);
					return;
				}
			}
		}
		catch(ParseException e) {
			System.err.printf("Unable to parse command line arguments because:%n%s", e.getMessage());
			return;
		}

		Gson gson = new Gson();
		TileConfig config;
		try {
			 config = gson.fromJson(new FileReader(tileConfig), TileConfig.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(config);

		LwjglApplicationConfiguration lwjglConfig = new LwjglApplicationConfiguration();
		new LwjglApplication(new Ultima(filename, config, width, height, worldSize), lwjglConfig);

		Gdx.app.setLogLevel(logLevel);
	}

	private static Options setupCli() {
		Option help = new Option("h", "help", false, "Prints this message");
		Option logLevel = new Option("l", "log", true, "Level of logging from 0-3");
		logLevel.setArgName("level");
		Option verbose = new Option("v", "verbose", false, "Sets the log level to 3");
		Option worldSize = new Option("w", "worldSize", true, "Sets the world's width and height to (2^n)+1 where the passed value n>=5");
		worldSize.setArgName("n");

		Options options = new Options();
		options.addOption(help);
		options.addOption(logLevel);
		options.addOption(verbose);
		options.addOption(worldSize);

		return options;
	}
}
