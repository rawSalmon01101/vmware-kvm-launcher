/*
 * DOCS:
 * the operations follow a simple rule
 * content@:
 *      (i) end (content@end): input this to denote that you have finished writing the file
 *      (ii) save (content@save): input this to save the file from cache to actual file.
 * 
 * 
 * Reworked docs: use content@end to denote the end of cache file writing,
 * indicating that the user has finished writing. possible actions after this
 * could be to save the cache to file.
 */


package simple;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.NullPointerException;

public class txtfile
{
    private static String filename;
    private static final String suffix = ".txt";

    public txtfile()
    {
        filename = "";
    }

    public static void setFilename()
    {
        filename = input.line("");
    }
    public static String getFilename()
    {
        return filename;
    }

    public static void createFile()
    {
        try
        {
            FileWriter fw = new FileWriter(filename + suffix);
            fw.close();
        }
        catch (IOException e)
        {
            System.out.println("failed to create file.");
            e.printStackTrace();
            System.exit(-1);
        }
    }
    public static void inputContents()
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename + suffix + "cache", true));

            while (true)
            {
                String str = input.line("");
                
                if (str == null || str.contains("content@end"))
                    break;

                bw.write(str + "\n");
            }

            bw.flush();
            bw.close();
        }
        catch (NullPointerException e)
        {
            System.out.println("Enountered NullPointerException");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println("Could not initialise IO properly.");
            e.printStackTrace();
            System.exit(-1);
        }
        
        transferFromCache();

        win_cmd.run_once("del " + filename + suffix + "cache");
    }
    private static void transferFromCache()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filename + suffix + "cache"));
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename + suffix, true));

            while (true)
            {
                String str = br.readLine();

                if (str == null)
                    break;
                
                bw.write(str + "\n");
            }

            bw.flush();
            bw.close();
            br.close();
        }
        catch (NullPointerException e)
        {
            System.out.println("NullPointerException occured.");
            e.printStackTrace();
            System.exit(-1);
        }
        catch (IOException e)
        {
            System.out.println("IOException occured");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}