package info.fangjie.launch.common;

import android.os.Handler;
import android.util.Log;

import org.json.JSONObject;

import info.fangjie.launch.model.OrderInfo;

public class OrderOperation {

	public static final int MSG_LOGIN = 1;
	public static final int MSG_GETALL = 2;
	public static final int MSG_REVED = 3;
	public static final int MSG_FINISH = 4;
	public static final int MSG_STATUS = 5;
	public static final int MSG_ORDER = 6;

	public static final int STATUS_NOT = 1;
	public static final int STATUS_ING = 2;
	public static final int STATUS_END = 3;
	
	
	private Handler mHandler;
	
	public OrderOperation(Handler mHandler) {
		this.mHandler = mHandler;
	}
	
	/* �µ� */
	public void addOrder(OrderInfo orderInfo)
	{
		JSONObject jsonData = new JSONObject();
		try {
			jsonData.put("msgtype", MSG_ORDER);
			
			jsonData.put("title", orderInfo.title);
			jsonData.put("info", orderInfo.info);
			jsonData.put("time", orderInfo.time);
			jsonData.put("canteen", orderInfo.canteen);
			jsonData.put("dst", orderInfo.dst);
			jsonData.put("tel", orderInfo.tel);
			jsonData.put("pay", orderInfo.pay);
			jsonData.put("status", STATUS_NOT);
			jsonData.put("userfrom", orderInfo.userfrom);
			jsonData.put("userto", orderInfo.userto); //��
			
			Log.i("jsonData add order", jsonData.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DataTransport dataTransport = new DataTransport(mHandler,  jsonData, DataTransport.SENDONLY);
		dataTransport.start();
	}
	
	public void getAll()
	{
		Log.i("MSG TYPE", "msg get all");
		JSONObject jsonData = new JSONObject();
		try {
			jsonData.put("msgtype", MSG_GETALL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DataTransport dataTransport = new DataTransport(mHandler,  jsonData, DataTransport.SENDANDREV);
		dataTransport.start();
	}
	
	public void getReved(String username)
	{
		JSONObject jsonData = new JSONObject();
		try {
			jsonData.put("msgtype", MSG_REVED);
			jsonData.put("username", username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DataTransport dataTransport = new DataTransport(mHandler,  jsonData, DataTransport.SENDANDREV);
		dataTransport.start();
	}
	
	public void getFinish(String username)
	{
		JSONObject jsonData = new JSONObject();
		try {
			jsonData.put("msgtype", MSG_FINISH);
			jsonData.put("username", username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DataTransport dataTransport = new DataTransport(mHandler,  jsonData, DataTransport.SENDANDREV);
		dataTransport.start();
	}
	
	public void updateStatus(int id, String username, int status)
	{
		JSONObject jsonData = new JSONObject();
		try {
			jsonData.put("msgtype", MSG_STATUS);
			jsonData.put("id", id);
			jsonData.put("status", status);
			jsonData.put("userto", username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DataTransport dataTransport = new DataTransport(mHandler,  jsonData, DataTransport.SENDONLY);
		dataTransport.start();
	}
	
	public boolean isLogin(String username, String password)
	{
		/* ��¼��д */
		return true;
	}
	
}
