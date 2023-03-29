package controller.admin;

import java.util.ArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import model.dto.ClientDto;

@ServerEndpoint("/alarm/{mid}")
public class Alarm {
	
	private static ArrayList<ClientDto> 알림명단 = new ArrayList<>();
	
	@OnOpen // 클라이언트 소켓이 들어왔을때 [연결]
	public void 서버입장(Session session , @PathParam("mid") String mid) throws Exception { 
		알림명단.add(new ClientDto(session,mid));
	}
	
	@OnClose // 클라이언트 소켓이 나갔을때 [연결]
	public void 서버나가기(Session session) throws Exception { 
		for(ClientDto c : 알림명단) {
			if(c.getSession() == session) { 알림명단.remove(c); }
		}
	}
	
	@OnError // 클라이언트 소켓이 에러가 발생했을 때 [연결] Throwable 인수필수
	public void 서버오류(Session session , Throwable e) throws Exception { 
		System.out.println(e);}
	
	@OnMessage // 클라이언트 소켓이 메세지를 보냈을 떄 매핑[연결] msg 필수로 정의할 인수
	public static void 서버메세지(Session session , String msg) throws Exception { 
	
		for(ClientDto c : 알림명단) {
			c.getSession().getBasicRemote().sendText("");
		}
	
	}
}

/*
  Session session : 요청한 클라이언트 소켓의 변수
  Throwable e : 에러 발생시 에러 이유가 저장된 변수
  String msg : 요청받은 메세지 내용
 
  서버 소켓
  	1. @ServerEndpoint("서버소켓경로/{매개변수명1}/{매개변수명2}")
  	2. @OnOpen 필수 매개변수 : Session session
  	3. @OnClose 필수 매개변수 : Session session
  	4. @OnError 필수 매개변수 : Session session, Throwable e
  	5. @OnMessage 필수 매개변수 : Session session, String msg
  	6. @Pa
*/