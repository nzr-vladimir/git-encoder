import javax.swing.*;

public class Main {
    public static void main (String[] args){
     mainGUI GUI = new mainGUI("Шифратор", 800, 500);
     GUI.setVisible(true);
     GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
