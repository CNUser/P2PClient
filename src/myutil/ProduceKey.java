package myutil;

public class ProduceKey {
	public static String getKey(String name, String ip, String port) {
		return new String(name + " " + ip + " " + port);
	}
}
