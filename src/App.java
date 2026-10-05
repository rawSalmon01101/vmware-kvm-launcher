import simple.*;

import javax.swing.*;
import javax.imageio.ImageIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

public class App
{
    final static String brand = "vmware-kvm Launcher";
    final static String iconPath = "/res/icon.png";
    final static String fontName = "/res/inter.ttf";
    final static String PREFERENCES = "launcher-options.conf";
    final static String TEMPLATE_CONF = "/res/launcher-template.conf";
    static String VM_FOLDER = "";
    static String VMWARE_PATH = "";

    //shitty ass jar forcing me to use extra mem.. not that I did good mem management ever
    static InputStream iconStream = App.class.getResourceAsStream(iconPath);
    static InputStream fontStream = App.class.getResourceAsStream(fontName);
    static InputStream templateStream = App.class.getResourceAsStream(TEMPLATE_CONF);

    final static int dim[] = {400, 190}; //window dimensions
    final static int center[] = {(1920 / 2) - (dim[0] / 2), (1080 / 2) - (dim[1] / 2)};

    final static Dimension max1 = new Dimension(dim[0], dim[1]);
    final static Rectangle max2 = new Rectangle(dim[0], dim[1]);

    static Color bgcolor = Color.WHITE;
    static Color fgcolor = Color.BLACK;
    static Color emphasis = new Color(240, 240, 240);
    static Color selectionBorder = new Color(70, 70, 70);
    static Color elevated = new Color(250, 250, 250);
    static Color border = new Color(230, 230, 230);

    static Font BASE_FONT;

    static
    {
        try
        {
            
            BASE_FONT = Font.createFont
            (
                Font.TRUETYPE_FONT, 
                fontStream
            );
        }
        catch (IOException | FontFormatException e)
        {
            System.out.println("Couldn't load font.");
        }
    }

    final static Font heading = BASE_FONT.deriveFont(Font.BOLD, 24);
    final static Font contentText = BASE_FONT.deriveFont(0, 12);
    final static Font controlText = BASE_FONT.deriveFont(Font.BOLD, 12);

    static String availableVMX[];
    static String allVMX[];

    //store control preferences
    static JComboBox <String> dropdown;
    static JButton launch;
    static JFrame frame;
    static JButton vm_config;
    static JButton containingFolder;
    static Container content;

    public static void main(String[] args) 
    {
        //get data from config file
        setOptionsFromFile();
        //check if dark mode
        isDarkMode();

        //UIManager override
        UIManager.put("ComboBox.selectionBackground", emphasis);
        UIManager.put("ComboBox.selectionForeground", fgcolor);

        UIManager.put
        (
            "Button.border",
            BorderFactory.createLineBorder(border, 1, true)
        );
        UIManager.put
        (
            "ComboBox.border",
            BorderFactory.createLineBorder(border, 1, true)
        );
        UIManager.put
        (
            "Button.focus",
            BorderFactory.createLineBorder(emphasis, 1, true)
        );

        

        //get all vmx files and populate
        getVMX();

        buildGUI();

        //then listen for button
        listenButton();
        listenOptionsButton();
    }

    public static void buildGUI()
    {
        frame = new JFrame(brand);
        frame.setIconImage(loadIcon());
        frame.setResizable(false);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(center[0], center[1], dim[0], dim[1]);
        frame.setBackground(bgcolor);
        frame.setForeground(bgcolor);

        content = frame.getContentPane();
        content.setForeground(bgcolor);

        JPanel bounding = new JPanel();
        bounding.add(helperText());
        bounding.add(dnb());
        bounding.add(optionsButton("VMware KVM Preferences \u2197", "Open VMware folder"));

        bounding.setBackground(bgcolor);

        content.add(bounding);

        frame.setVisible(true);
    }

    public static JPanel dnb() // Dropdown and Button
    {
        dropdown = new JComboBox<>(allVMX);
        dropdown.setFont(controlText);
        dropdown.setBackground(elevated);
        dropdown.setForeground(fgcolor);
        dropdown.setEditable(true);
        dropdown.setAutoscrolls(true);
        dropdown.setUI(new CustomComboBoxUI());

        //weird border inside combo box
        JComponent editor = (JComponent) dropdown.getEditor().getEditorComponent();
        editor.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
        editor.setBackground(elevated);
        editor.setForeground(fgcolor);
        editor.addMouseListener
        (
            new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent event)
                {
                    dropdown.showPopup();
                }
            }
        );
        if (editor instanceof JTextField)
        {
            ((JTextField) editor).setCaretColor(fgcolor);
        }

        launch = new JButton("Launch & Exit");
        launch.setFont(controlText);
        launch.setBackground(elevated);
        launch.setForeground(fgcolor);
        launch.setPreferredSize(new Dimension(110, 30));
        launch.setFocusPainted(false);

        JPanel bottomRow = new JPanel(new BorderLayout(10, 0));
        bottomRow.add(dropdown, BorderLayout.CENTER);
        bottomRow.add(launch, BorderLayout.EAST);

        bottomRow.setBorder
        (
            BorderFactory.createEmptyBorder(0, 11, 5, 11)
        );

        bottomRow.setPreferredSize(new Dimension(390, 35));

        bottomRow.setBackground(bgcolor);

        return bottomRow;
    }

    public static JPanel helperText()
    {
        JLabel help = new JLabel("Select a VM");
        JLabel help2 = new JLabel("Only showing .vmx files found in " + VM_FOLDER);

        help.setFont(heading);
        help2.setFont(contentText);

        JPanel textArea = new JPanel( new BorderLayout(0, 10));
        textArea.setPreferredSize(new Dimension(dim[0] - 33, 60));
        textArea.setLayout(new BoxLayout(textArea, BoxLayout.Y_AXIS));
        textArea.setBorder
        (
            BorderFactory.createEmptyBorder(6, 0, 10, 12)
        );

        help.setAlignmentX(Component.LEFT_ALIGNMENT);
        help2.setAlignmentX(Component.LEFT_ALIGNMENT);

        help.setForeground(fgcolor);
        help2.setForeground(fgcolor);

        textArea.setBackground(bgcolor);

        textArea.add(help);
        textArea.add(help2);

        return textArea;
    }

    public static JButton optionsButton(String text1, String text2)
    {
        vm_config = new JButton(text1);
        vm_config.setPreferredSize(new Dimension(368, 30));

        vm_config.setFont(controlText);
        vm_config.setForeground(fgcolor);
        vm_config.setBackground(elevated);
        vm_config.setFocusPainted(false);
        //containingFolder = new JButton(text2);

        //JPanel actions = new JPanel();
        //actions.add(vm_config, BorderLayout.CENTER);
        //actions.add(containingFolder, BorderLayout.EAST);

        //actions.setPreferredSize(new Dimension(390, 30));
        //actions.setBackground(bgcolor);
        //actions.setForeground(fgcolor);

        return vm_config;
    }

    public static void isDarkMode()
    {
        String command = "reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize\" /v AppsUseLightTheme";

        String res = win_PowerShell.get_output(command).trim();
        //Integer.parseInt(res) == 1
        if (res.contains("0x0"))
        {
            bgcolor = new Color(32, 32, 32);
            fgcolor = Color.WHITE;
            emphasis = new Color(116, 116, 116);
            elevated = new Color(45, 45, 45);
            border = new Color(60, 60, 60);
        }
    }

    public static BufferedImage loadIcon()
    {
        BufferedImage icon = null;

        try
        {
            icon = ImageIO.read(iconStream);
        }
        catch (Exception e)
        {
            
        }

        return icon;
    }

    public static void listenButton()
    {
        launch.addActionListener
        (
            e ->
            {
                try
                {
                    String sel = availableVMX[dropdown.getSelectedIndex()];
                    System.out.println("\"" + VMWARE_PATH + "vmware-kvm.exe\" " + "\"" + sel + "\"");
                    String exec = VMWARE_PATH + "\\" + "vmware-kvm.exe";
                    
                    new ProcessBuilder(exec, sel).start();
                }
                catch (Exception exception)
                {
                    System.out.println(exception);
                    openInNotepad();
                }
                finally
                {
                    System.exit(1);
                }
            }
        );
    }
    public static void listenOptionsButton()
    {
        vm_config.addActionListener
        (
            e ->
            {
                try
                {
                    String exec = VMWARE_PATH + "\\" + "vmware-kvm.exe";
                    new ProcessBuilder(exec, "--preferences").start();
                }
                catch(Exception exception)
                {
                    System.out.println(exception);
                    openInNotepad();
                    System.exit(-1);
                }
            }
        );
    }

    public static void escListener()
    {
        
    }

    public static void getVMX()
    {
        java.nio.file.Path root = java.nio.file.Paths.get(VM_FOLDER);

        try 
        {
            availableVMX = java.nio.file.Files.walk(root)
                .filter(java.nio.file.Files::isRegularFile)
                .map(p -> p.toAbsolutePath().toString())
                .filter(s -> s.toLowerCase().endsWith(".vmx"))
                .toArray(String[]::new);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        //then populate a second array
        allVMX = new String[availableVMX.length];

        int ptr = 0;
        for (String path : availableVMX)
        {
            allVMX[ptr++] = path.substring(path.lastIndexOf('\\') + 1);
        }
    }

    public static void setOptionsFromFile()
    {
        BufferedReader br;
        boolean afterMargin = false;
        try
        {
            br = new BufferedReader(new FileReader(PREFERENCES));
            ArrayList<String> lines = new ArrayList<>();

            String currLine = br.readLine();

            while (currLine != null)
            {
                if (afterMargin)
                    lines.add(currLine);

                if (currLine.equals("================================================================================"))
                    afterMargin = true;

                currLine = br.readLine();
            }

            System.out.println(lines.toString());

            for (int i = 0; i < lines.size(); i++)
            {
                if (lines.get(i).startsWith("VMWARE_PATH"))
                    VMWARE_PATH = lines.get(i).substring(lines.get(i).indexOf('=') + 1);
                else if (lines.get(i).startsWith("VM_FOLDER"))
                    VM_FOLDER = lines.get(i).substring(lines.get(i).indexOf('=') + 1);
            }
        }
        catch (IOException e)
        {
            System.out.println("Cannot find " + PREFERENCES);
            e.printStackTrace();

            createPreferences();
        }

        if (VM_FOLDER.length() == 0 || VM_FOLDER.length() == 0)
        {
            openInNotepad();
            System.exit(-1);
        }
    }

    public static void createPreferences()
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PREFERENCES, true));
            BufferedReader br = new BufferedReader(new InputStreamReader(templateStream));

            String currLine = br.readLine();

            while (currLine != null)
            {
                bw.write(currLine + "\n");
                currLine = br.readLine();
            }

            bw.close();
            br.close();

            openInNotepad();

            System.exit(-1);
        }
        catch (IOException e)
        {
            System.out.println("Fatal IO error. Maybe allow Controlled Folder Access?");
            System.out.println(ansicolors.red + e + ansicolors.reset_text);
        }
    }

    public static void openInNotepad()
    {
        win_cmd.run_once("notepad.exe " + PREFERENCES);
    }
}