package com.justa.test.aws;

import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.Voice;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

// https://docs.aws.amazon.com/polly/latest/dg/examples-java.html
public class PollyDemo {
	
	private final AmazonPolly polly;
	private Voice voice ;
	private final String voiceId = "Justin";
	private static final String SAMPLE = "Congratulations. You have successfully built this AWS working demo"
			+ "	of Amazon Polly in Java.";

	public PollyDemo(Region region) {
		
		ProfileCredentialsProvider p = new ProfileCredentialsProvider("restricted");
		// must define  "restricted" section in C:\Users\[USERNAME]\.aws\credentials
		
		
		polly = AmazonPollyClientBuilder.standard()
				.withCredentials(p)
				.build();

		// Create describe voices request.
		DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();
		describeVoicesRequest.setLanguageCode("en-US");

		// Synchronously ask Amazon Polly to describe available TTS voices.
		DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);
		
		for(Voice v: describeVoicesResult.getVoices()) {
			if(v.getId().equals(voiceId)) {
				voice = v;
				break;
			}
		};
		if(voice == null) {
			voice = describeVoicesResult.getVoices().get(0);
		}
		System.out.println(voice.getId());
		System.out.println(voice.getGender());
		System.out.println(voice.getLanguageName());
		System.out.println(voice.getLanguageCode());
	}
	
	public InputStream synthesize(String text, OutputFormat format) throws IOException {
		SynthesizeSpeechRequest synthReq = 
		new SynthesizeSpeechRequest().withText(text)
		
		.withVoiceId(voice.getId())
		
		.withLexiconNames("awsLexicon")  // must upload it firstly
				.withOutputFormat(format);
		SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);

		return synthRes.getAudioStream();
	}
	
	public static void main(String args[]) throws Exception {
		//create the test class
		PollyDemo helloWorld = new PollyDemo(Region.getRegion(Regions.US_EAST_1));
		//get the audio stream
		InputStream speechStream = helloWorld.synthesize(SAMPLE, OutputFormat.Mp3);

		//create an MP3 player
		AdvancedPlayer player = new AdvancedPlayer(speechStream,
				javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

		player.setPlayBackListener(new PlaybackListener() {
			@Override
			public void playbackStarted(PlaybackEvent evt) {
				System.out.println("Playback started");
				System.out.println(SAMPLE);
			}
			
			@Override
			public void playbackFinished(PlaybackEvent evt) {
				System.out.println("Playback finished");
			}
		});
		
		
		// play it!
		player.play();
		
	}
}

