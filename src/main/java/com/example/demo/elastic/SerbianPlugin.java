///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package com.example.demo.elastic;
// 
//import org.elasticsearch.indices.analysis.AnalysisModule;
//import org.elasticsearch.plugins.Plugin;
//
//import com.example.demo.elastic.config.SerbianBinderProcessor;
//
///**
// *
// * @author Milan Deket
// */
//public class SerbianPlugin extends Plugin {
//
//    public String name() {
//	return "serbian-plugin";
//    }
//
//    public String description() {
//        return "Analyzer that converts cyrilic words into lowercase latinic";
//    }
//    
//    public void onModule(AnalysisModule analysisModule) {
//			analysisModule.addProcessor(new SerbianBinderProcessor());
//    }
//}
