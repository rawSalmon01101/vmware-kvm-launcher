import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class CustomComboBoxUI extends BasicComboBoxUI
{
    @Override
    protected JButton createArrowButton()
    {
        JButton chevron = new JButton("↕");

        chevron.setFont(App.contentText);
        chevron.setBackground(App.elevated);
        chevron.setForeground(App.fgcolor);
        chevron.setFocusPainted(false);
        chevron.setContentAreaFilled(false);

        chevron.setBorder(BorderFactory.createEmptyBorder());

        return chevron;
    }
}