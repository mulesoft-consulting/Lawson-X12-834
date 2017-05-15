package com.dart.bn106.strategies;

import com.dart.bn106.CleanUpContext;
import com.dart.bn106.CleanUpStrategy;

public class DTP0CleanUpStrategy implements CleanUpStrategy {

	@Override
	public String cleanupLine(String line) {
		String cleanedLine = line.substring(0,3)+"0"+line.substring(3);
		//Pad line:
		return String.format("%1$-" + CleanUpContext.prop.getProperty("DTP0") + "s", cleanedLine);
	}

}
