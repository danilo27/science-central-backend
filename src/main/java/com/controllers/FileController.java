package com.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.pdf.PdfReader;

 

@RestController
@RequestMapping(value = "/file")
@CrossOrigin(origins = "http://localhost:4200")
public class FileController {
	
	@PostMapping("/addFile")
	public HashMap<String, String> addFile(@RequestParam("file") MultipartFile file) {
		PdfReader reader;
		try {
			reader = new PdfReader(file.getBytes());
			
			if (reader.getMetadata() == null) {
			      System.out.println("No XML Metadata.");
			      HashMap<String, String> mmap=new HashMap<String,String>();
			      mmap.put("NOXML", "NOXML");
			      String filename=saveUploadedFile(file);
			      mmap.put("filename", filename);
			      return mmap;
			    } else {
			      @SuppressWarnings("unchecked")
			      HashMap<String, String> map=reader.getInfo();
			      String filename=saveUploadedFile(file);
			      map.put("filename", filename);
			      System.out.println(map.keySet());
			      return map;
			    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		HashMap<String, String> mmap=new HashMap<String,String>();
	    mmap.put("NOXML", "NOXML");
	    return mmap;

		
	}
	
	public String saveUploadedFile(MultipartFile file) throws IOException {
    	String retVal = null;
        if (! file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get("C:/elastic_repo" + File.separator + file.getOriginalFilename());
            
            System.out.println(path);
            Files.write(path, bytes);
            retVal = path.toString();
        }
        return retVal;
    }
	
}
