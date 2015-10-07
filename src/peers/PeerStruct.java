package peers;

public class PeerStruct {
	private String name;
	private String ipAddress;
	private String port;	
	
	public PeerStruct(String name, String ipAddress, String port) {
		super();
		this.name = name;
		this.ipAddress = ipAddress;
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
}