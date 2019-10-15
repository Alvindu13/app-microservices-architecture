package com.microservice.mnotifs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableBinding(Sink.class)
public class MnotifsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MnotifsApplication.class, args);
	}


	@StreamListener(Sink.INPUT)
	public void handleMessage(Message message){
		System.out.println("Received Message is: " + message);
	}

	public static class Message{
		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return "Message{" +
					"message='" + message + '\'' +
					'}';
		}
	}
}
