package com.justa.test.aws;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class GetLocalFile {

    public static void main(String[] args) throws IOException {
    	
    	if(args.length != 1) {
    		System.out.println("expected one argument: filename ");
    		return;
    	}
    	
    	System.out.println(new Date());
    	
        try {

            File f = new File(args[0]);

            FileReader fr = new FileReader(f);
            
            System.out.println(new Date());

            System.out.println("Reading file using Buffered Reader");
            System.out.println(new Date());
            
            displayTextInputStream(fr);
            
            System.out.println(new Date());

        } catch (IOException e) {
            e.printStackTrace();
        }

        
    }

    public static void displayTextInputStream(InputStreamReader isr) throws IOException {
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(isr);
        String line = null;
        int rows = 0;
        int size = 0;
        while ((line = reader.readLine()) != null) {
            //System.out.println(line);
        	size = size + line.length();  
            rows++;
            if(rows% 500000 ==0 ) {
            	System.out.println("adding rows = " + rows +", size = " + size);
            }
        }
        System.out.println("rows = " + rows +", size = " + size);
    }
}
