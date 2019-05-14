package com.speech.api;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.transcribe.AmazonTranscribe;
import com.amazonaws.services.transcribe.AmazonTranscribeClient;
import com.amazonaws.services.transcribe.model.GetTranscriptionJobRequest;
import com.amazonaws.services.transcribe.model.LanguageCode;
import com.amazonaws.services.transcribe.model.Media;
import com.amazonaws.services.transcribe.model.StartTranscriptionJobRequest;
import com.amazonaws.services.transcribe.model.TranscriptionJob;
import com.amazonaws.services.transcribe.model.TranscriptionJobStatus;

public class Transcribe {

	
	public String convertSpeech(String fileName,String fullFileName) {
		System.setProperty("aws.accessKeyId","");
		System.setProperty("aws.secretKey", "");
		final String bucketName="transcribe-poly";
	
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTHEAST_1).
				withClientConfiguration(new ClientConfiguration()).
				withCredentials(new DefaultAWSCredentialsProviderChain() ).build();
		AmazonTranscribe client = AmazonTranscribeClient.builder().withRegion("ap-southeast-1").build();
		s3.putObject(bucketName, fileName, new File(fullFileName));
		StartTranscriptionJobRequest request = new StartTranscriptionJobRequest();
		GetTranscriptionJobRequest getRequest = new GetTranscriptionJobRequest();

	    request.withLanguageCode(LanguageCode.EnUS);

	    Media media = new Media();

	    media.setMediaFileUri(s3.getUrl(bucketName, fileName).toString());

	    request.withMedia(media);
	    
	    request.setTranscriptionJobName(fileName);
	    request.withMediaFormat("wav");

	    TranscriptionJob transcriptionJob=client.startTranscriptionJob(request).getTranscriptionJob();
	    getRequest.setTranscriptionJobName(fileName);

	    String output="";
	    while( true ){
	    	transcriptionJob=client.getTranscriptionJob(getRequest).getTranscriptionJob();
	    if(transcriptionJob.getTranscriptionJobStatus().equals(TranscriptionJobStatus.COMPLETED.toString())) {
	    	System.out.println();
	    	output=download(transcriptionJob.getTranscript().getTranscriptFileUri());
	    	break;
	    }else if( transcriptionJob.getTranscriptionJobStatus().equals(TranscriptionJobStatus.FAILED.name()) ){

            break;
    }
    //to not be so anxious
   synchronized ( this ) {
        try {
           this.wait(50);
           
        } catch (InterruptedException e) { }
   }
	    }
	    
	    return output;
	}
	
	public String download( String uri){
		@SuppressWarnings("deprecation")
		HttpClient httpclient= new DefaultHttpClient();
	     HttpGet httpGet= new HttpGet(uri);
	     String reshandler="";
	     try {
	    	 HttpResponse response=null;
				response = (HttpResponse) httpclient.execute(httpGet);
				 HttpEntity entity = null;
				 entity = ((org.apache.http.HttpResponse) response).getEntity();
				 if(null!=entity){
		            	try {
							reshandler =EntityUtils.toString(entity);
						} catch (org.apache.http.ParseException e) {
							System.out.println("Exception during Validation ");
						} catch (IOException e) {
							System.out.println("Exception during Validation ");
						}
		            }
			     /*int status = response.getStatusCode();
				 if(status >= 200 && status < 300){
					 entity = ((org.apache.http.HttpResponse) response).getEntity();
		            if(null!=entity){
		            	try {
							reshandler =EntityUtils.toString(entity);
						} catch (org.apache.http.ParseException e) {
							System.out.println("Exception during Validation ");
						} catch (IOException e) {
							System.out.println("Exception during Validation ");
						}
		            }
				 }*/
				if(null!= reshandler){
					try {
						System.out.println("Response ");
						JSONObject json = (new JSONObject(reshandler)).getJSONObject("results");

						JSONArray js = (JSONArray)(json.get("transcripts"));

						JSONObject json2=js.getJSONObject(0);
						return json2.get("transcript").toString();
					} catch (Exception e) {
						System.out.println("Exception during Validation "+e);
					}
				}
	     
			}catch (UnsupportedEncodingException e) {
				System.out.println("Exception during Validation ");
			}catch (ClientProtocolException e) {
				System.out.println("Exception during Validation ");
			} catch (IOException e) {
				System.out.println("Exception during Validation "+e);
			}
	     
	     return "The audio was not converted successfully !!! ";
	}
	    
	    
	}
	
