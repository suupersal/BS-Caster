package com.gutierrez.salvador.bscaster.helpers;

public class CommandBuilder {
	// build command with variable string arguments
	public static String build(int command, String userID, String... args) {
		String strComm = Integer.toString(command);
		String argCount = Integer.toString(args.length);
		String result = strComm + "@" + userID + "@" + argCount;
		for (String arg : args) {
			result += "@" + arg;
		}
		return result;
	}

	// build command with variable int arguments
	public static String build(int command, String userID, int... args) {
		String strComm = Integer.toString(command);
		String argCount = Integer.toString(args.length);
		String result = strComm + "@" + userID + "@" + argCount;
		for (int arg : args) {
			result += "@" + Integer.toString(arg);
		}
		return result;
	}

	// build command with no arguments
	public static String build(int command, String userID) {
		String strComm = Integer.toString(command);
		return strComm + "@" + userID;
	}

	// build gommand with arguments already formated, requires arg count
	public static String build(int command, String userID, int argCount,
			String arguments) {
		String strComm = Integer.toString(command);
		return strComm + "@" + userID + "@" + strConvert(argCount) + arguments;
	}

	public static String strConvert(int i) {
		return Integer.toString(i);
	}

	public static int intConvert(String s) {
		return Integer.parseInt(s);
	}
}
