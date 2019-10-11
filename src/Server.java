import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server {
  
  public String getName() {
    String hostname = "";
    try {
      InetAddress ip = InetAddress.getLocalHost();
      hostname = ip.getHostName();
    } catch (UnknownHostException e) {
        e.printStackTrace();
    }
    return hostname;
  }

}
