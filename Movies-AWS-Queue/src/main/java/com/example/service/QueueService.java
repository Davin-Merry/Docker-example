package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

@Service
public class QueueService {
	private AWSCredentials cred;
	private AmazonSQS sqs;
	
	//@Value("${access-key}")
	private String accessKey = "[KEY]";
	
	//@Value("${secret-key}")
	private String secretKey = "[SECRET KEY]";
	
	//@Value("${queueUrl}")
	private String url = "https://sqs.us-east-2.amazonaws.com/808878172550/TrainingQueue";
	
	public QueueService() {
		buildQueue();
	}
	
	public void buildQueue() {
		cred = new BasicAWSCredentials(accessKey, secretKey);
		sqs = AmazonSQSClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(cred))
				.withRegion(Regions.US_EAST_2)
				.build();
	}
	
	public String sendMessage(String message) {
		SendMessageRequest request = new SendMessageRequest()
				.withQueueUrl(url)
				.withMessageBody(message);
		sqs.sendMessage(request);
		return message;
	}
	
	public String getMessage() {
		return sqs.receiveMessage(url).getMessages().get(0).getBody();
	}
}
