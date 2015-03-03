package com.openfire.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.jivesoftware.openfire.MessageRouter;
import org.jivesoftware.openfire.XMPPServer;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;

public class SendMessageImpl extends UnicastRemoteObject implements SendMessageService {

	
	private static final long serialVersionUID = -2189287547324039146L;
	
	protected SendMessageImpl() throws RemoteException {
		System.out.println("start rmi server");
	}


	public SendMessageImpl(String msg) throws RemoteException {
		System.out.println(msg);
	}
	@Override
	public void getOFServer(String from, String to, String msg, String domain) throws RemoteException {
		XMPPServer server = XMPPServer.getInstance();

		MessageRouter messageRouter = server.getMessageRouter();
		Message message = new Message();
		message.setFrom(new JID(from + "@" + domain));
		message.setTo(new JID(to + "@" + domain));
		message.setBody(msg);
		message.setType(Message.Type.chat);
		messageRouter.route(message);

	}

	@Override
	public void sendBroadCastMessage(String from, String msg, String domain) throws RemoteException {
		System.out.println("send broadcast");
		XMPPServer server = XMPPServer.getInstance();
		MessageRouter messageRouter = server.getMessageRouter();
		Message message = new Message();
		message.setFrom(new JID(from + "@" + domain));
		message.setTo("all@broadcast."+domain);
		message.setBody(msg);
		message.setType(Message.Type.chat);
		messageRouter.route(message);
		System.out.println("send broadcast successfully");

	}
	
	@Override
	public void sendOnlineMessage(String from, String to, String msg, String domain) throws RemoteException {
		System.out.println(msg);
		XMPPServer server = XMPPServer.getInstance();

		MessageRouter messageRouter = server.getMessageRouter();
		Message message = new Message();
		message.setFrom(new JID(from + "@" + domain));
		message.setTo(new JID(to + "@" + domain));
		message.setBody(msg);
		message.setType(Message.Type.headline);
		messageRouter.route(message);
		
	}

}
