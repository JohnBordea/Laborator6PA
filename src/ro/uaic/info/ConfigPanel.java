package ro.uaic.info;

import javax.swing.*;
import java.awt.*;

public class ConfigPanel extends JPanel {
    final MainFrame frame;
    JLabel sidesLabel;
    JSpinner sidesField;
    JLabel colorLabel;
    JComboBox colorCombo;
    JLabel sizeLabel;
    JSpinner sizeField;

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        sidesLabel = new JLabel("Number of sides:");
        sidesField = new JSpinner(new SpinnerNumberModel(3, 3, 100, 1));
        sidesField.setValue(6);

        colorLabel = new JLabel("Color:");
        colorCombo = new JComboBox();
        colorCombo.setBounds(10, 10, 250, 26);
        colorCombo.setEditable(true);
        colorCombo.addItem(new ComboItem("Blue", Color.blue));
        colorCombo.addItem(new ComboItem("Black", Color.BLACK));

        sizeLabel = new JLabel("Size:");
        sizeField = new JSpinner(new SpinnerNumberModel(5, 1, 100, 1));

        add(sidesLabel);
        add(sidesField);
        add(colorLabel);
        add(colorCombo);
        add(sizeLabel);
        add(sizeField);
    }

    public Color getColor() {
        return ((ComboItem) colorCombo.getItemAt(colorCombo.getSelectedIndex())).getValue();
    }

    public int getSides() {
        return (int) sidesField.getValue();
    }

    public int getSizeOfShape() {
        return (int) sizeField.getValue();
    }
}
