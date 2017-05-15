package com.dart.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FileCleanUp {

	public static void main(String[] args) {
		try {
			File file = new File("src/test/resources/Bn106.txt");
			
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			Bn106Cleaner cleaner = new Bn106Cleaner(bufferedReader);
			StringBuffer stringBuffer = cleaner.clean();
			
			BufferedWriter bwr = new BufferedWriter(new FileWriter(new File("src/test/resources/cleaned-up.txt")));
            
            //write contents of StringBuffer to a file
            bwr.write(stringBuffer.toString());
           
            //flush the stream
            bwr.flush();
           
            //close the stream
            bwr.close();
			fileReader.close();
			System.out.println("Contents of file:");
			System.out.println(stringBuffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}