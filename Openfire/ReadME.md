使用方法：
在XMPPServer里绑定RMI即可：
try { 
	SendMessageService rhello = new SendMessageImpl("Start Server!"); 
    LocateRegistry.createRegistry(RmiFlag.OPENFIRE_JAVAEE_PORT); 
    Naming.bind(RmiFlag.OPENFIRE_JAVAEE_NAME, rhello); 
} catch (RemoteException e) { 
	e.printStackTrace(); 
} catch (AlreadyBoundException e) { 
    e.printStackTrace(); 
} catch (MalformedURLException e) { 
    e.printStackTrace(); 
} 