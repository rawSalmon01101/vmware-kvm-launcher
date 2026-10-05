package simple;

import javax.swing.JFileChooser;

public class files 
{
    public static String swingPicker()
    {
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(null);

        return jfc.getSelectedFile().toString();
    }
    public static String swingPicker(String rootDir)
    {
        JFileChooser jfc = new JFileChooser(rootDir);
        jfc.showOpenDialog(null);

        return jfc.getSelectedFile().toString();
    }
}
