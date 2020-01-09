package com.justa.library;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
	
	public static String readFileFromClassRoot(String path ) throws IOException {
		
		URL url = FileUtil.class.getResource(path);
		File file = new File(url.getFile());		
		Charset encoding = StandardCharsets.UTF_8;
		byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		return new String(encoded, encoding);
	}
}
