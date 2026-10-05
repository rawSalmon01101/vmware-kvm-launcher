package simple;

public class gate
{
    public static boolean and(boolean a, boolean b)
    {
        return a && b;
    }
    public static boolean and(boolean a, boolean b, boolean c)
    {
        return a && b && c;
    }

    public static boolean or(boolean a, boolean b)
    {
        return a || b;
    }
    public static boolean or(boolean a, boolean b, boolean c)
    {
        return a || b || c;
    }

    public static boolean not(boolean a)
    {
        return !a;
    }

    public static boolean nor(boolean a, boolean b)
    {
        return !or(a, b);
    }
    public static boolean nor(boolean a, boolean b, boolean c)
    {
        return !or(a, b, c);
    }

    public static boolean nand(boolean a, boolean b)
    {
        return !and(a, b);
    }
    public static boolean nand(boolean a, boolean b, boolean c)
    {
        return !and(a, b, c);
    }

    public static boolean xor(boolean a, boolean b)
    {
        return (a && !b || !a && b);
    }
    public static boolean xor(boolean a, boolean b, boolean c)
    {
        return (a && !b && !c || !a && b && !c || !a && !b && c || a && b && c);
    }

    public static boolean xnor(boolean a, boolean b)
    {
        return (!a && !b || a && b);
    }
    public static boolean xnor(boolean a, boolean b, boolean c)
    {
        return (!a && !b && !c || a && b && c || !a && b && c || a && !b && c || a && b && !c);
    }
}