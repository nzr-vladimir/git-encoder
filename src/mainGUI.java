import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class mainGUI extends JFrame {
    private JButton btn1, btn2, btn3, btn4; //ссылки на объекты кнопок
    private JTextArea text1, text2; // ссылки на объекты TextArea
    private JScrollPane scroller1, scroller2; //ссылки
    private JLabel lbl1, lbl2;                //ссылки
    private JFileChooser fileChooser; // ссылка

    public mainGUI (String title, int width, int height){
        setTitle(title);                                                                         // устанавливаем заголовок (название) окна в конструкторе
        setSize(width, height);                                                                  // устанавливаем размеры окна
        JPanel BTNpanel = new JPanel();                                                          // создаем экземпляр панели для кнопок
        BTNpanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));                            // задаем отступы между элементами панели и краями самой панели
        BTNpanel.setBackground(Color.lightGray);                                                 // устанавливаем цвет панели для кнопок серым, чтобы различать ее на фоне фрейма
        BTNpanel.setLayout(new BoxLayout(BTNpanel,BoxLayout.Y_AXIS));                            // меняем диспетчер компоновки панели с FlowLayout на BoxLayout
        BTNpanel.add(Box.createVerticalStrut(15));
        btn1 = new JButton("Зашифровать текст");                                             // создаем кнопку 1
        btn2 = new JButton("Расшифровать текст");                                            // создаем кнопку 2
        btn3 = new JButton("О программе");
        btn4 = new JButton("Сохр. заш-ный текст");
        BTNpanel.add(btn1);                                                                       // добавляем кнопку 1 на панель
        BTNpanel.add(Box.createVerticalStrut(5));                                          // создаем разделитель (пространство по вертикали) между кнопками
        BTNpanel.add(btn2);                                                                       // добавляем кнопку 2 на панель
        BTNpanel.add(Box.createVerticalStrut(5));                                          // создеаем разделитель (пространство по вертикали) между кнопками
        BTNpanel.add(btn3);
        BTNpanel.add(Box.createVerticalStrut(5));
        BTNpanel.add(btn4);
        getContentPane().add(BorderLayout.EAST,BTNpanel);                                         // размещаем нашу панель в области EAST компоновщика фрейма BorderLayout
        JPanel TEXTpanel = new JPanel();                                                          // создаем экземпляр панели для TextArea
        TEXTpanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        TEXTpanel.setBackground(Color.lightGray);
        TEXTpanel.setLayout(new BoxLayout(TEXTpanel,BoxLayout.Y_AXIS));
        text1 = new JTextArea(5,20);
        text2 = new JTextArea(5,20);
        scroller1 = new JScrollPane(text1);
        scroller2 = new JScrollPane(text2);
        text1.setLineWrap(true);
        text2.setLineWrap(true);
        scroller1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroller2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        lbl1 = new JLabel("Исходный текст");
        lbl2 = new JLabel("Зашифрованный текст");
        fileChooser = new JFileChooser();
        TEXTpanel.add(lbl1);
        TEXTpanel.add(scroller1);
        TEXTpanel.add(lbl2);
        TEXTpanel.add(scroller2);
        getContentPane().add(BorderLayout.CENTER,TEXTpanel);
                 //присоединяем обработчики к кнопкам
        ButtonActionCode codeAction = new ButtonActionCode();
        btn1.addActionListener(codeAction);
        ButtonActionRecode recodeAction = new ButtonActionRecode();
        btn2.addActionListener(recodeAction);
        ButtonActionHelp helpAction = new ButtonActionHelp();
        btn3.addActionListener(helpAction);
        ButtonActionSave saveTextAction = new ButtonActionSave();
        btn4.addActionListener(saveTextAction);
    }
    // ниже идут обработчики нажатий на кнопки
    private class ButtonActionCode implements ActionListener{
        public void actionPerformed (ActionEvent event){
            if (text1.getText().equals("")){JOptionPane.showMessageDialog(mainGUI.this,"Введите текст для шифрования!", "Уведомление", JOptionPane.WARNING_MESSAGE);}
            else {
                String text, codeText;
                byte[] byteArray;
                text = text1.getText();
                byteArray = text.getBytes(StandardCharsets.UTF_16);
                for (int i = 0; i < byteArray.length; i++) {
                    byteArray[i]++;
                }
                codeText = new String(byteArray, StandardCharsets.UTF_16);
                text2.setText(codeText);
            }
        }
    }
    private class ButtonActionRecode implements ActionListener{
        public void actionPerformed (ActionEvent event){
            if (text2.getText().equals("")) {JOptionPane.showMessageDialog(mainGUI.this, "Нет текста для расшифровки.", "Уведомление", JOptionPane.WARNING_MESSAGE);}
            else {
                String text, recodeText;
                byte[] byteArray;
                text = text2.getText();
                byteArray = text.getBytes(StandardCharsets.UTF_16);
                for (int i = 0; i < byteArray.length; i++) {
                    byteArray[i]--;
                }
                recodeText = new String(byteArray, StandardCharsets.UTF_16);
                text1.setText(recodeText);
            }
        }

    }
    private class ButtonActionHelp implements ActionListener{
        public void actionPerformed (ActionEvent event){
            JOptionPane.showMessageDialog(mainGUI.this,"<html>В принципе тут должно быть и так все интуитивно понятно. <br> Думаю, справка не нужна", "Справка", JOptionPane.INFORMATION_MESSAGE);
                //helpWND wnd = helpWND.getInstance("Справка", 300, 200, getX(), getY(), getWidth(), getHeight());
                //wnd.setVisible(true);
                //wnd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         }
    }
    private class ButtonActionSave implements ActionListener{
        public void actionPerformed (ActionEvent event){
            try{
                if (!text2.getText().equals("")){
                    fileChooser.setDialogTitle("Выбор директории");
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int result = fileChooser.showSaveDialog(mainGUI.this);
                    if (result==JFileChooser.APPROVE_OPTION) {
                          File file =  fileChooser.getSelectedFile();
                          System.out.println(file.getPath());
                          FileWriter writer = new FileWriter(file);
                          writer.write(text2.getText());
                          writer.close();
                    }
                } else { JOptionPane.showMessageDialog(mainGUI.this,"Нет зашифрованного текста для сохранения.", "Уведомление", JOptionPane.WARNING_MESSAGE);}
            } catch (IOException ex) {ex.printStackTrace();}
        }
    }

}