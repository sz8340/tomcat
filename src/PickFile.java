import javax.swing.JFileChooser;

public class PickFile {
  public static void main(String[] s) {
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new java.io.File("/"));
    chooser.setDialogTitle("chooser");
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.setAcceptAllFileFilterUsed(false);

    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
      System.out.println("getSelectedFile(): " + chooser.getSelectedFile());
    } else {
      System.out.println("No selection");
    }
  }
}
