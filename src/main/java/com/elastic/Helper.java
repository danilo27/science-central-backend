//package com.elastic;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.model.Author;
//import com.model.Credentials;
//import com.model.Paper;
//import com.repositories.AuthorRepository;
//
//@Service
//public class Helper {
//
//	//private static String DATA_DIR_PATH;
//	
//	@Autowired
//	private Indexer indexer;
//	
//	@Autowired
//	private AuthorRepository authorRepo;
//	
//	/*static {
//		ResourceBundle rb=ResourceBundle.getBundle("application");
//		DATA_DIR_PATH=rb.getString("dataDir");
//	}*/
//	
//	public String saveUploadedFile(MultipartFile file) throws IOException {
//    	String retVal = null;
//        if (! file.isEmpty()) {
//            byte[] bytes = file.getBytes();
//            //Path path = Paths.get(getResourceFilePath(DATA_DIR_PATH).getAbsolutePath() + File.separator + file.getOriginalFilename());
//            Path path = Paths.get("./src/main/resources/static" + File.separator + file.getOriginalFilename());
//            
//            System.out.println(path);
//            Files.write(path, bytes);
//            retVal = path.toString();
//        }
//        return retVal;
//    }
//
//    public void saveAndIndex(Paper paper) throws IOException{
//    		IndexUnit indexUnit = new PDFHandler().getIndexUnit(new File(paper.getFilename()));
//            indexUnit.setTitle(obradi(paper.getTitle()));
//            indexUnit.setKeywords(obradi(paper.getKeywords()));
//           
//            Credentials c = paper.getAuthor();
//            String authorUsername = c.getUsername();
//            Author a = authorRepo.findByCredentialsUsername(authorUsername);
// 
//            indexUnit.setAuthorFirstName(obradi(a.getUserDetails().getFirstName()));
//            indexUnit.setAuthorLastName(obradi(a.getUserDetails().getLastName()));
//            
//            indexUnit.setFilename(paper.getFilename());
//            //indexUnit.setLanguage(e_book.getLanguage().getName());
//            indexer.add(indexUnit);
//    }
//	/*
//    private File getResourceFilePath(String path) {
//	    URL url = this.getClass().getClassLoader().getResource(path);
//	    File file = null;
//	    try {
//	        file = new File(url.toURI());
//	    } catch (URISyntaxException e) {
//	        file = new File(url.getPath());
//	    }   
//	    return file;
//	}*/
//    
//    private String obradi(String s) {
//		String x=s.toLowerCase();
//		x=CyrillicLatinConverter.cir2lat(x);
//		return x;
//	}
//}
