package com.example.service;

import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.example.domain.Message;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class MyStompService {
    private static Logger logger = Logger.getLogger(MyStompService.class);

    StompSession stompSession; //このstompSesstionを最初に作って、リクエストの度に呼び出す

    public StompSession connect() {
        String url = "ws://localhost/websocketEndpoint/"; //endpointのURL

        //（1）sockJsClientの作成
        Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
        List<Transport> transports = Collections.singletonList(webSocketTransport);

        SockJsClient sockJsClient = new SockJsClient(transports);
        //sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());

        //（2）sockJsClientからstompClientの作成
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        //（3）connect
        StompSessionHandlerAdapter sessionHandler = new MyHandler();
        try {
        	stompSession = stompClient.connect(url, sessionHandler).get();
		} catch (InterruptedException | ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        return stompSession;
    }
    //topicに対するsubscribe登録は今回は省略
    //send(socketにpush)する処理。なければ不要
    //connect()を呼ぶ場所には悩んだ。endpointが登録された後でなければならないが、MyStompServiceのコンストラクタの中で呼ぶのはダメ。
    //bean登録はendpoint登録より先であった。よって、sendHelloメソッドの初回時とした。
    public void sendHello(Message message) {
    	if(stompSession==null){
    		connect();
    	}
        stompSession.send("/app/message/"+message.getRoomName(), message);
    }
    //StompSessionHandlerAdapterの拡張クラスが必要
    private class MyHandler extends StompSessionHandlerAdapter {
        public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
            logger.info("Now connected");
        }
    }
}