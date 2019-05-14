package com.speech.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lexruntime.AmazonLexRuntime;
import com.amazonaws.services.lexruntime.AmazonLexRuntimeClientBuilder;
import com.amazonaws.services.lexruntime.model.PostContentRequest;
import com.amazonaws.services.lexruntime.model.PostContentResult;
import com.amazonaws.services.lexruntime.model.PostTextRequest;

public class AwsLex {

	public static void main(String a[]) throws UnsupportedAudioFileException, IOException {
		
		AmazonLexRuntime client = AmazonLexRuntimeClientBuilder.standard()
                .withRegion(Regions.US_EAST_1).build();
        PostTextRequest textRequest = new PostTextRequest();
        PostContentRequest audioContent = new PostContentRequest();
        audioContent.setBotName("SpeechToText");
        audioContent.setBotAlias("speechtotext");
        audioContent.setUserId("testUser");
        audioContent.withContentType("audio/x-l16");
        InputStream audioBytes = AudioSystem.getAudioInputStream(new File("/Users/srivatsan.v/Downloads/16-05-46audio.wav").getAbsoluteFile());
        textRequest.setBotName("SpeechToText");
        audioContent.withInputStream(audioBytes);
        
        PostContentResult textResult = client.postContent(audioContent);
        System.out.println(textResult);
        
	}
	
}
