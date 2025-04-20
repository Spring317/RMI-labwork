// filepath: /home/spring/OS/Exercises/rmi-subject/GUI.java
import java.awt.*;
import java.awt.event.*;
import java.rmi.*;
import javax.swing.*;

public class GUI extends JFrame {
    TextField name, email;
    Choice pads;
    Label message;
    
    public GUI() {
        setSize(300,200);
        setLayout(new GridLayout(6,2));
        add(new Label("  Name : "));
        name = new TextField(30);
        add(name);
        add(new Label("  Email : "));
        email = new TextField(30);
        add(email);
        add(new Label("  Pad : "));
        pads = new Choice();
        pads.addItem("Pad1");
        pads.addItem("Pad2");
        add(pads);
        add(new Label(""));
        add(new Label(""));
        Button Abutton = new Button("add");
        Abutton.addActionListener(new AButtonAction());
        add(Abutton);
        Button Cbutton = new Button("consult");
        Cbutton.addActionListener(new CButtonAction());
        add(Cbutton);
        message = new Label();
        add(message);
    }

    // Helper method to get the selected pad
    private Pad getPad(String padName) {
        try {
            return (Pad) Naming.lookup("//localhost/" + padName);
        } catch (Exception e) {
            message.setText("Error connecting to " + padName + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    class CButtonAction implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            String n, c;
            n = name.getText();
            c = pads.getSelectedItem();
            message.setText("consult("+n+","+c+")        ");
            
            try {
                Pad selectedPad = getPad(c);
                if (selectedPad != null) {
                    RRecord record = selectedPad.consult(n, true);
                    if (record != null) {
                        message.setText("Found: " + record.getName() + ", " + record.getEmail());
                        email.setText(record.getEmail());
                    } else {
                        message.setText("Record not found for: " + n);
                    }
                }
            } catch (Exception e) {
                message.setText("Error during consult: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    class AButtonAction implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            String n, e, c;
            n = name.getText();
            e = email.getText();
            c = pads.getSelectedItem();
            message.setText("add("+n+","+e+","+c+")");
            
            try {
                Pad selectedPad = getPad(c);
                if (selectedPad != null) {
                    SRecord newRecord = new SRecordImpl(n, e);
                    selectedPad.add(newRecord);
                    message.setText("Added record for: " + n);
                }
            } catch (Exception ex) {
                message.setText("Error during add: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    public static void main(String args[]) {
        GUI s = new GUI();
        s.setSize(400,200);
        s.setVisible(true);
        s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
