package simple;

import java.io.IOException;

public class legacyNotifs
{
    public static void send(String title, String message)
    {
        ProcessBuilder pb = new ProcessBuilder("powershell.exe", "-File", ".\\src\\simple\\.\\BalloonHelper.ps1", title, message, "None");
        Process process;
        
        try
        {
            process = pb.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void send(String title, String message, boolean debug)
    {
        System.out.println("-File .\\src\\simple\\.\\BalloonHelper.ps1");
        
        ProcessBuilder pb = new ProcessBuilder("powershell.exe", "-File", ".\\src\\simple\\.\\BalloonHelper.ps1", title, message, "None");
        Process process;
        
        try
        {
            process = pb.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
