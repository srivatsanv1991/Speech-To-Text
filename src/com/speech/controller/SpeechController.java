package com.speech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.speech.api.AWSPolly;
import com.speech.api.Transcribe;
import com.speech.vo.Result;

@Controller
public class SpeechController {

	
	@RequestMapping(value ="/welcome")
	public ModelAndView welcome() {		
		return new ModelAndView("Home","message","Hello World");
	}
	
	@RequestMapping(value="/callAPI")
	public ModelAndView callAPI(@ModelAttribute("SpringWeb")Result result) throws Exception {
		
		ModelAndView model=new ModelAndView();
		
		//QuickStartSample qs=new QuickStartSample();
		//String text=qs.convertSpeechToText("/Users/srivatsan.v/Downloads/"+result.getFileName());
		Transcribe transcribe=new Transcribe();
		AWSPolly polly=new AWSPolly();
		String text=transcribe.convertSpeech(result.getFileName(),"PATH-THE-FILE-TO-BE-Downloaded"+result.getFileName());
		model.setViewName("result");
		String filePath=polly.synthesizeSpeech(text,result.getFileName());
		model.addObject("Text",text);
		model.addObject("audioFile",filePath);
		return model;
		
	}
	
	
}
