package socialNetwork.src;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.io.*;

import socialNetwork.ui.Interface;
import socialNetwork.src.Friends;
import socialNetwork.src.Message;

public class Serveur {

	public static final int port = 5234;
	public static Interface ex;
	public static ArrayList<InetAddress> connectList = new ArrayList<InetAddress>();

	public Serveur(){}

	public void run(){
		ex = new Interface();
		ex.setVisible(true);
		listener();
	}

	public static void connect(){
		initConnect();
		for(int i = 0; i < Friends.friendList.size(); i++){
			try{
				Message.connect(InetAddress.getByName(Friends.friendList.get(i).getHost()));
			}catch(Exception e){}
		}		
	}

	public static void connect(String name, String host){
		System.out.println(host);
		try{
			connectList.add(InetAddress.getByName(host));
		}catch(Exception e){System.out.println("caca");}
		Friends friend = Friends.findFriend(name);
		friend.informFriend(friend.getStatus());
	}

	private static void initConnect(){
		try{
			for(int i = 0; i < Friends.friendList.size();i++)
				connectList.add(InetAddress.getByName((Friends.friendList.get(i).getHost())));
		}catch(Exception e){}
	}

	public void listener(){

		try{
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ServerSocket server = ssc.socket();
			Selector selector = Selector.open();

			server.bind(new InetSocketAddress(port));
			ssc.configureBlocking(false);
			ssc.register(selector, SelectionKey.OP_ACCEPT);

			while(true){
				selector.select();
				Set keys = selector.selectedKeys();
				Iterator it = keys.iterator();

				while (it.hasNext()){
					SelectionKey key = (SelectionKey) it.next();

					if (key.isAcceptable()){
						Socket listen = server.accept();
						SocketChannel sc = listen.getChannel();
						sc.configureBlocking(false);
						sc.register(selector, SelectionKey.OP_READ);
					}

					if(key.isReadable() && key.isValid()){
						ByteBuffer bb = ByteBuffer.allocate(512);
						SocketChannel st = (SocketChannel) key.channel();
						int byteRead = st.read(bb);
						bb.flip();
						if (byteRead == -1){
							key.cancel();
							st.close();

						}
						else {
							analyseData(bb);
							key.cancel();
						}
					}
				}
				keys.clear();
			}
		}catch (Exception e){}
	}


	private void analyseData(ByteBuffer bb){
		byte[] buff = new byte[bb.remaining()];
		bb.get(buff);
		String receiveData = new String(buff);
		String so1 = receiveData.substring(0, 2);
		int req = Integer.parseInt(so1);
		Protocole.treatmentProtocol(req, receiveData.substring(2));
	}
}