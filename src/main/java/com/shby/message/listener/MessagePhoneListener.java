package com.shby.message.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.shby.api.dto.msg.MsgPhoneMessageDto;

@Component
@RabbitListener(queues="queue.message.phone")
public class MessagePhoneListener extends BaseListener {

    @RabbitHandler
    public void recieved(MsgPhoneMessageDto msg) {
    	serviceFactory.getMessagePhoneService().execute(msg);
    }
    
}