package com.rattrapage.microserviceapi.notifications;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;

public interface MessageSource {

	@Output("messageChannel")
    MessageChannel fileMessage();

}
