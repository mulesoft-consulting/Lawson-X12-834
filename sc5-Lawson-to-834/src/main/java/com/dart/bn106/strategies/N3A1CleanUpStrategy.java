package com.dart.bn106.strategies;

import com.dart.bn106.CleanUpContext;
import com.dart.bn106.CleanUpStrategy;

public class N3A1CleanUpStrategy implements CleanUpStrategy {

	@Override
	public String cleanupLine(String line) {
		String cleanedLine = line.substring(0,2)+"A1"+line.substring(2);
		//Pad line:
		return String.format("%1$-" + CleanUpContext.prop.getProperty("N3A1") + "s", cleanedLine);
	}

}
