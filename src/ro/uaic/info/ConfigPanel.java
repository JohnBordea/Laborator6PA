package ro.uaic.info;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ConfigPanel extends JPanel {
    final MainFrame frame;

    JLabel modeLabel;
    JComboBox modeCombo;
    JLabel shapeChoiceLabel;
    JComboBox shapeCombo;
    JLabel sidesLabel;
    JSpinner sidesField;
    JLabel colorLabel;
    JComboBox colorCombo;
    JLabel sizeLabel;
    JSpinner sizeField;

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        initialization();
    }

    private void initialization() {
        modeLabel = new JLabel("Mode:");
        modeCombo = new JComboBox<ComboItemMode>();
        modeCombo.setBounds(10, 10, 250, 26);
        modeCombo.setEditable(false);
        modeCombo.addItem(new ComboItemMode("Set Mode", 0));
        modeCombo.addItem(new ComboItemMode("Free Mode", 1));
        modeCombo.addActionListener(this::modeInitialization);


        shapeChoiceLabel = new JLabel("Shape:");
        shapeCombo = new JComboBox<ComboItemShape>();
        shapeCombo.setBounds(10, 10, 250, 26);
        shapeCombo.setEditable(false);
        shapeCombo.addItem(new ComboItemShape("Free Shape", 0));
        shapeCombo.addItem(new ComboItemShape("Circle", 1));
        shapeCombo.addItem(new ComboItemShape("Square", 2));
        shapeCombo.addActionListener(this::shapeInitialization);

        sidesLabel = new JLabel("Number of sides:");
        sidesField = new JSpinner(new SpinnerNumberModel(3, 3, 100, 1));
        sidesField.setEnabled(true);
        sidesField.setValue(6);

        colorLabel = new JLabel("Color:");
        colorCombo = new JComboBox<ComboItemColor>();
        colorCombo.setBounds(10, 10, 250, 26);
        colorCombo.setEditable(false);
        colorCombo.addItem(new ComboItemColor("Blue", Color.BLUE));
        colorCombo.addItem(new ComboItemColor("Black", Color.BLACK));
        colorCombo.addItem(new ComboItemColor("Pink", Color.PINK));

        sizeLabel = new JLabel("Size:");
        sizeField = new JSpinner(new SpinnerNumberModel(25, 1, 100, 1));

        add(modeLabel);
        add(modeCombo);
        add(shapeChoiceLabel);
        add(shapeCombo);
        add(sidesLabel);
        add(sidesField);
        add(colorLabel);
        add(colorCombo);
        add(sizeLabel);
        add(sizeField);
    }

    public void modeInitialization(ActionEvent e) {
        switch (getModeMode()) {
            case 0 -> {
                shapeCombo.setEnabled(true);
                switch (getShapeMode()) {
                    case 0 -> sidesField.setEnabled(true);
                    case 1, 2 -> sidesField.setEnabled(false);
                }
            }
            case 1 -> {
                shapeCombo.setEnabled(false);
                sidesField.setEnabled(false);
            }
        }
    }

    public void shapeInitialization(ActionEvent e) {
        switch (getShapeMode()) {
            case 0 -> sidesField.setEnabled(true);
            case 1, 2 -> sidesField.setEnabled(false);
        }
    }

    public int getModeMode() {
        return ((ComboItemMode) modeCombo.getItemAt(modeCombo.getSelectedIndex())).getValue();
    }

    public int getShapeMode() {
        return ((ComboItemShape) shapeCombo.getItemAt(shapeCombo.getSelectedIndex())).getValue();
    }

    public Color getColor() {
        return ((ComboItemColor) colorCombo.getItemAt(colorCombo.getSelectedIndex())).getValue();
    }

    public int getSides() {
        return (int) sidesField.getValue();
    }

    public int getSizeOfShape() {
        return (int) sizeField.getValue();
    }
}
