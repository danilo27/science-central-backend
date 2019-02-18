package com.example.demo.elastic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import com.model.Paper;
import com.services.repo.MagazineService;

@Service
public class Helper {

	//private static String DATA_DIR_PATH;
	
	@Autowired
	private Indexer indexer;
	
	/*static {
		ResourceBundle rb=ResourceBundle.getBundle("application");
		DATA_DIR_PATH=rb.getString("dataDir");
	}*/
	
	@Autowired
	private MagazineService magazineService;
	
	public String saveUploadedFile(MultipartFile file) throws IOException {
    	String retVal = null;
        if (! file.isEmpty()) {
            byte[] bytes = file.getBytes();
            //Path path = Paths.get(getResourceFilePath(DATA_DIR_PATH).getAbsolutePath() + File.separator + file.getOriginalFilename());
            Path path = Paths.get("C:/elastic_repo" + File.separator + file.getOriginalFilename());
            
            System.out.println(path);
            Files.write(path, bytes);
            retVal = path.toString();
        }
        return retVal;
    }
    
    public void saveAndIndexNewPaper(Paper paper) throws IOException{
    	 
		IndexUnit indexUnit = new PDFHandler().getIndexUnit(new File(paper.getFilename())); //set text
        indexUnit.setTitle(cyrToLat(paper.getTitle()));
        indexUnit.setKeywords(cyrToLat(paper.getKeywords()));
        indexUnit.setAuthor(cyrToLat(paper.getAuthor()));
        indexUnit.setField(paper.getField().getName());
        indexUnit.setMagazine(magazineService.findOneByIssn(paper.getWantedMagazine()).getName());
        indexUnit.setFilename(paper.getFilename());
        indexUnit.setGeo_point(new GeoPoint(45.297050,19.814990)); //Autor je u NS
       
        
        System.out.println("save and index new paper");
        indexer.add(indexUnit);
    }
    
	/*
    private File getResourceFilePath(String path) {
	    URL url = this.getClass().getClassLoader().getResource(path);
	    File file = null;
	    try {
	        file = new File(url.toURI());
	    } catch (URISyntaxException e) {
	        file = new File(url.getPath());
	    }   
	    return file;
	}*/
    
    private String cyrToLat(String s) {
		String x=s.toLowerCase();
		x=CyrillicLatinConverter.cir2lat(x);
		return x;
	}
}
