package myutil;

public class ProduceKey {
	public static String getKey(String name, String ip, String port) {
		return name + " " + ip + " " + port;
	}
	
	public static String getHashKey(String key) {
		if (null != key) {
			String[] data = key.split(" ");
			return data[1];
		}
		else {
			return "null";
		}
	}
}
