package com.example.demo.elastic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.springframework.stereotype.Service;

@Service
public class MyTokenizer {
	
	public String cyrToLat(String s) {
		String x=s.toLowerCase();
		x=CyrillicLatinConverter.cir2lat(x);
		return x;
	}
	
	 /**
     * An array containing some common English words
     * that are usually not useful for searching.
     */
    public static final String[] STOP_WORDS =
    {
        "i","a","ili","ali","pa","te","da","u","po","na",
        "и","а","или","али","па","те","да","у","по","па"
    };
	
	public String[] splitToTokens(String s){
		StringTokenizer multiTokenizer = new StringTokenizer(s.toLowerCase(), ".;!?-:,");
		
//		Tokenizer source = new StandardTokenizer();
//		TokenStream result = new CyrilicToLatinFilter(source);
//		
//		result = new StopFilter(result,StopFilter.makeStopSet(STOP_WORDS));
//		
		List<String> ret = new ArrayList<String>();
		while (multiTokenizer.hasMoreTokens()){
		    ret.add(multiTokenizer.nextToken());
		}
		System.out.println("Pre izbacivanja stop reci: " + ret.size() + " tokena");
		
		List<String> ret2 = new ArrayList<String>();	
		List<String> stop_words = Arrays.asList(STOP_WORDS);
		for(String token : ret){
			//token = token.replaceAll("\\s+",""); //remove white space and \n
			token = token.trim().replaceAll(" +", " ");
			if(!stop_words.contains(token.toLowerCase())){ //to lower, contains
				ret2.add(token);
			}
		}
		
		System.out.println("Posle izbacivanja stop reci: " + ret2.size());
		
		String[] tokens = ret2.toArray(new String[ret2.size()]);
		for(String t : tokens)
			System.out.println(t);
		return tokens;
	}
}
