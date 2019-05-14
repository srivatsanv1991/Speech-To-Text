package com.speech.api;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.VoiceId;

public class AWSPolly {
	 
	    private AmazonPollyClient client ;
	 
	    public String synthesizeSpeech(String text,String outputFile) {
	        String outputFileName = "OUTPUT_PATH"+outputFile;
	        client = new AmazonPollyClient(new DefaultAWSCredentialsProviderChain(), 
	        		new ClientConfiguration());
	        client.withRegion(Regions.AP_SOUTHEAST_1);
	        SynthesizeSpeechRequest synthesizeSpeechRequest = new SynthesizeSpeechRequest()
	                .withOutputFormat(OutputFormat.Mp3)
	                .withVoiceId(VoiceId.Joanna)
	                .withText(text);
	 
	        try {
	        	FileOutputStream outputStream = new FileOutputStream(new File(outputFileName));
	        	
	            SynthesizeSpeechResult synthesizeSpeechResult = client.synthesizeSpeech(synthesizeSpeechRequest);
	            byte[] buffer = new byte[2 * 1024];
	            int readBytes;
	 
	            try{
	            	InputStream in = synthesizeSpeechResult.getAudioStream();
	                while ((readBytes = in.read(buffer)) > 0) {
	                    outputStream.write(buffer, 0, readBytes);
	                }
	            }catch (Exception e) {
	            	System.err.println("Exception caught: " + e);
	            }
	        } catch (Exception e) {
	            System.err.println("Exception caught: " + e);
	        }
	        return "/resources/audio/"+outputFile;
	    }

}
