import java.io.*;

public class Command {

  public Command() { }

  public String execute(String command) {

    String s = "";
    String[] oldtag;
    String result = "success";

    try {
      Runtime rt = Runtime.getRuntime();
      Process proc = rt.exec(command);

      BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

      // read the output from the command
      while ((s = stdInput.readLine()) != null) {
        if ( s.contains("Loaded image ID:") ) {
          oldtag = s.split(":");
          result = "success," + oldtag[2];
        } else if ( s.contains("Loaded image:") ) {
          oldtag = s.split(":", 2);
          result = "success," + oldtag[1];
        } else {
          result = "success";
        }
      }
  
      // read any errors from the attempted command
      while ((s = stdError.readLine()) != null) {
        result = "failure";
      }
    } catch (Exception e) { 
      System.out.println(e);
    }
    return result;
  }
}
