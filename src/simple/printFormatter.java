package simple;

public class printFormatter 
{
    //toolbar printers
    public static void toolbar_no_anim (String toolbar[], String customSeparator)
    {
        //thanos snaps out of existence
        win_cmd.run_once("cls");

        for (int i = 0; i < toolbar.length; i++)
            if (i + 1 == toolbar.length)
                System.out.print(toolbar[i]);
            else
                System.out.print(toolbar[i].concat(customSeparator));
    }
    public static void toolbar_ease_out (String toolbar[], int spacing)
    {
        win_cmd.run_once("cls"); //the terminal cries WAaAAaAaAaaAaAaAaAaAaAAaAAaaaAA

        for (int i = 1; i <= spacing; i++)
        {
            for (int j = 0; j < toolbar.length; j++)
            {
                System.out.print(toolbar[j]);
                
                for (int k = 1; k <= i; k++)
                    System.out.print(" ");
            }

            try
            {
                // *snore* zzzzzzzzz
                Thread.sleep((10 * (i * (i / 2))));
                //the terminal cries WaAaaaaAaaaAAaaAaaA again
                win_cmd.run_once("cls"); 
            }
            catch (Exception e)
            {
                //todo nothing to enter catch
            }
        }

        for (int i = 0; i < toolbar.length; i++)
        {
            System.out.print(toolbar[i]);
            
            for (int j = 1; j <= spacing; j++)
                System.out.print(" ");
        }

        System.out.println();
    }
}