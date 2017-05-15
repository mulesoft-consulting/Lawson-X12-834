package com.dart.utils;

import java.io.BufferedReader;
import java.io.IOException;

import com.dart.bn106.CleanUpContext;
import com.dart.bn106.strategies.BGN0CleanUpStrategy;
import com.dart.bn106.strategies.DMG0CleanUpStrategy;
import com.dart.bn106.strategies.DTP0CleanUpStrategy;
import com.dart.bn106.strategies.DTP1CleanUpStrategy;
import com.dart.bn106.strategies.DTP2CleanUpStrategy;
import com.dart.bn106.strategies.HDA0CleanUpStrategy;
import com.dart.bn106.strategies.HLH0CleanUpStrategy;
import com.dart.bn106.strategies.INS0CleanUpStrategy;
import com.dart.bn106.strategies.N1A0CleanUpStrategy;
import com.dart.bn106.strategies.N1B0CleanUpStrategy;
import com.dart.bn106.strategies.N3A0CleanUpStrategy;
import com.dart.bn106.strategies.N3A1CleanUpStrategy;
import com.dart.bn106.strategies.N4A0CleanUpStrategy;
import com.dart.bn106.strategies.N4A1CleanUpStrategy;
import com.dart.bn106.strategies.NM10CleanUpStrategy;
import com.dart.bn106.strategies.NM11CleanUpStrategy;
import com.dart.bn106.strategies.REF0CleanUpStrategy;
import com.dart.bn106.strategies.REF1CleanUpStrategy;
import com.dart.bn106.strategies.REF2CleanUpStrategy;
import com.dart.bn106.strategies.SEA0CleanUpStrategy;
import com.dart.bn106.strategies.STA0CleanUpStrategy;

//https://dzone.com/articles/design-patterns-strategy

public class Bn106Cleaner {
	
	private static final String INS = "INS";
	private static final String REF = "REF";
	private static final String DTP = "DTP";
	private static final String NM1 = "NM1";
	private static final String N3 = "N3";
	private static final String N4 = "N4";
	private static final String DMG = "DMG";
	private static final String HLH = "HLH";
	private static final String HD = "HD";
	
	private StringBuffer stringBuffer = new StringBuffer();
	private BufferedReader bufferedReader = null;
	
	private CleanUpContext ctx = new CleanUpContext();
	
	public Bn106Cleaner(BufferedReader bufferedReader){
		this.bufferedReader = bufferedReader;
	}
	
	/**
	 * clean method converts a BN106 Lawson file into a MuleSoft compliant flat file
	 * @return
	 * @throws IOException
	 */
	public StringBuffer clean() throws IOException{
		
		String line;
		
		line = bufferedReader.readLine();
		ctx.setCleanUpStrategy(new STA0CleanUpStrategy());			
		append(line);
		
		line = bufferedReader.readLine();
		ctx.setCleanUpStrategy(new BGN0CleanUpStrategy());			
		append(line);
		
		line = bufferedReader.readLine();
		ctx.setCleanUpStrategy(new DTP0CleanUpStrategy());			
		append(line);
		
		line = bufferedReader.readLine();
		ctx.setCleanUpStrategy(new N1A0CleanUpStrategy());			
		append(line);
		
		line = bufferedReader.readLine();
		ctx.setCleanUpStrategy(new N1B0CleanUpStrategy());			
		append(line);
		
		
		while ((line = appendMember()) == null) {
			//This is a Member record
		}
		
		//When line is not null, then we hit the last line in the file:
		ctx.setCleanUpStrategy(new SEA0CleanUpStrategy());			
		append(line);
		
		return stringBuffer;
	}
	
	
	/**
	 * appendMember
	 * @return
	 * @throws IOException
	 */
	private String appendMember() throws IOException{
		String line = bufferedReader.readLine();
		if (!line.startsWith(INS)){//This is not a Member record
			return line;
		}
		
		ctx.setCleanUpStrategy(new INS0CleanUpStrategy());			
		append(line);
		
		line = bufferedReader.readLine();
		ctx.setCleanUpStrategy(new REF0CleanUpStrategy());			
		append(line);
		
		line = bufferedReader.readLine();
		ctx.setCleanUpStrategy(new REF1CleanUpStrategy());			
		append(line);
		
		line = bufferedReader.readLine();
		if (line.startsWith(REF)){
			ctx.setCleanUpStrategy(new REF2CleanUpStrategy());			
			append(line);
			line = bufferedReader.readLine();
		}
		
		if (line.startsWith(DTP)){
			ctx.setCleanUpStrategy(new DTP1CleanUpStrategy());			
			append(line);
			line = bufferedReader.readLine();
		}
		
		if (line.startsWith(NM1)){
			ctx.setCleanUpStrategy(new NM10CleanUpStrategy());			
			append(line);
			line = bufferedReader.readLine();
		}

	
		if (line.startsWith(N3)){
			ctx.setCleanUpStrategy(new N3A0CleanUpStrategy());			
			append(line);
			line = bufferedReader.readLine();
		}
		
		if (line.startsWith(N4)){
			ctx.setCleanUpStrategy(new N4A0CleanUpStrategy());			
			append(line);
			line = bufferedReader.readLine();
		}
		
		if (line.startsWith(DMG)){
			ctx.setCleanUpStrategy(new DMG0CleanUpStrategy());			
			append(line);
			line = bufferedReader.readLine();
		}
		
		if (line.startsWith(HLH)){
			ctx.setCleanUpStrategy(new HLH0CleanUpStrategy());			
			append(line);
			line = bufferedReader.readLine();
		}
		
		if (line.startsWith(NM1)){
			ctx.setCleanUpStrategy(new NM11CleanUpStrategy());			
			append(line);
			line = bufferedReader.readLine();
		}
		
		if (line.startsWith(N3)){
			ctx.setCleanUpStrategy(new N3A1CleanUpStrategy());			
			append(line);
			line = bufferedReader.readLine();
		}
		
		if (line.startsWith(N4)){
			ctx.setCleanUpStrategy(new N4A1CleanUpStrategy());			
			append(line);
			line = bufferedReader.readLine();
		}

		if (line.startsWith(HD)){
			ctx.setCleanUpStrategy(new HDA0CleanUpStrategy());			
			append(line);
			line = bufferedReader.readLine();
		}
		
		if (line.startsWith(DTP)){
			ctx.setCleanUpStrategy(new DTP2CleanUpStrategy());			
			append(line);
		}
		
		return null;
		
	}
	
	/**
	 * append a line
	 * @param line
	 */
	private void append(String line){
		line = ctx.cleanupLine(line);
		stringBuffer.append(line);
		stringBuffer.append("\n");
	}

}
