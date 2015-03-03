package com.openfire.rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SendMessageService extends Remote {

	void getOFServer(String from, String to, String msg, String domain) throws RemoteException;
	void sendOnlineMessage(String from, String to, String msg, String domain) throws RemoteException;
	void sendBroadCastMessage(String from, String msg, String domain) throws RemoteException;

}
