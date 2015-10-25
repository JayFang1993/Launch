package info.fangjie.launch.common;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class DataTransport extends Thread{
	
	final static int SENDANDREV = 1;
	final static int SENDONLY = 2;
	
	JSONObject jsonObject;
	int transType;
	Handler mHandler;
	
	public DataTransport(Handler mHandler, JSONObject jsonObject, int transType) {
		this.mHandler = mHandler;
		this.jsonObject = jsonObject;
		this.transType = transType;
	}
	
	@Override
	public void run() {
		try {
			Socket clientSocket = new Socket("192.168.191.1", 8080);
			OutputStreamWriter out = new OutputStreamWriter(clientSocket.getOutputStream(),"utf-8");
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "utf-8"));
			
			Log.i("send data", jsonObject.toString());
			
			out.write(jsonObject.toString() + "\n"); //�������
			out.flush();  //ˢ�»�������������
			
			if(SENDANDREV == transType)
			{
				Log.i("send and recv", "yes");
				String result = in.readLine();
				Log.i("result", result);
				
				Message msg = new Message();
				msg.what = 1;
				msg.obj = new JSONObject(result);
				mHandler.sendMessage(msg);
			}

			in.close();
			out.close();
			clientSocket.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
