package simple;

public class misc
{
    public class $gate
    {
        public static boolean marriage (boolean male, boolean female)
        {
            return (male == false && female == false)? false : true;
        }
        public static String marriage_String(boolean male, boolean female)
        {
            return (male == false && female == false)? "Man's fault" : "Woman is right";
        }
    }

    public static String betterReplace (String str, int pos, char newChar)
    {
        if (pos < 0 || pos >= str.length()) 
            return str; // safety

        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(pos, newChar);
        return sb.toString();
    }

    public static void indeterminateColor (int duration, int refreshrate, String message)
    {
        boolean easein = false;

        for (int i = 1; i <= duration; i++)
        {
            int start = 0, end = 3;

            if (easein)
            {
                easein = false;
                start = -60;
                end = 0;
            }
            else
            {
                easein = true;
            }

            for (int k = 1; k <= 60; k++)
            {
                System.out.println(message + "\n\n");


                for (int j = 1; j <= 60; j++)
                {
                    if (j >= start && j <= end)
                        System.out.print(ansicolors.white_bg + " " + ansicolors.reset);
                    else
                        System.out.print(ansicolors.green_bg + " " + ansicolors.reset);
                }

                if (easein)
                {
                    end = end + 2;
                    if (start > 30)
                        start = start + 2;
                    else
                        start = start + 1;
                }
                else
                {
                    start = start + 3;
                    end = end + (Math.random() > 0.5? 2 : 1);
                }

                try { Thread.sleep((int)(refreshrate / 2)); }
                catch (InterruptedException e) { e.printStackTrace(); }

                win_cmd.run_once("cls");
            }
        }
    }

    public static void indeterminateChar (int duration, int refreshrate, String message)
    {
        boolean easein = false;

        for (int i = 1; i <= duration; i++)
        {
            int start = 0, end = 3;

            if (easein)
            {
                easein = false;
                start = -60;
                end = 0;
            }
            else
            {
                easein = true;
            }

            for (int k = 1; k <= 60; k++)
            {
                System.out.println(message + "\n\n");


                for (int j = 1; j <= 60; j++)
                {
                    if (j >= start && j <= end)
                        System.out.print("=");
                    else
                        System.out.print("-");
                }

                if (easein)
                {
                    end = end + 2;
                    if (start > 30)
                        start = start + 2;
                    else
                        start = start + 1;
                }
                else
                {
                    start = start + 3;
                    end = end + (Math.random() > 0.5? 2 : 1);
                }

                try { Thread.sleep((int)(refreshrate / 2)); }
                catch (InterruptedException e) { e.printStackTrace(); }

                win_cmd.run_once("cls");
            }
        }
    }
}