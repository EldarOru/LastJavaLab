
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Locale;
import java.util.ResourceBundle;


import javax.swing.*;

public class Auto extends Methods {


    JLabel l1, l2, l3;
    JTextField tf1;
    JButton btn1, btn2;
    JPasswordField p1;
    JButton rus,eng;

    Auto() {
        setVisible(false);
        JFrame frame = new JFrame("Login Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.cyan);
        l1 = new JLabel("Авторизация");
        l1.setForeground(Color.blue);
        l1.setFont(new Font("Serif", Font.BOLD, 20));
        l2 = new JLabel(resourceBundle.getString("login"));
        l3 = new JLabel(resourceBundle.getString("password"));
        tf1 = new JTextField();
        p1 = new JPasswordField();
        btn1 = new JButton("Login");
        btn2 = new JButton("Registration");
        rus = new JButton("Rus");
        eng = new JButton("Eng");
        l1.setBounds(100, 30, 400, 30);
        l2.setBounds(80, 70, 200, 30);
        l3.setBounds(80, 110, 200, 30);
        tf1.setBounds(300, 70, 200, 30);
        p1.setBounds(300, 110, 200, 30);
        btn1.setBounds(150, 160, 100, 30);
        btn2.setBounds(260,160,130,30);
        rus.setBounds(350,200,130,30);
        eng.setBounds(350,300,130,30);

        frame.add(l1);
        frame.add(l2);
        frame.add(tf1);
        frame.add(l3);
        frame.add(p1);
        frame.add(btn1);
        frame.add(btn2);
        frame.add(rus);
        frame.add(eng);
        frame.setSize(600, 600);
        frame.setLayout(null);
        frame.setVisible(true);
        rus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 locale = new Locale("ru","RU");
                resourceBundle = ResourceBundle.getBundle("language",locale);
                SwingUtilities.updateComponentTreeUI(frame);
                frame.invalidate();
                frame.validate();
                frame.repaint();
            }
        });
        eng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //locale = new Locale("en","US");
                //resourceBundle = ResourceBundle.getBundle("language",locale);
            }
        });
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String uname = tf1.getText();
                String pass = p1.getText();
                if(!uname.equals("") && !pass.equals("")) {

                    send("login;" + uname + ";" + pass);

                    //если сервер не работает, то все будет четко

                    String x = receive();
                    System.out.println(x);

                    //авторизация робит
                    if (x.startsWith("Вы успешно вошли!")) {
                        JOptionPane.showMessageDialog(null, "eee boy)");
                        Methods.name = uname;
                        new UserPage1();
                        frame.setVisible(false);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Что-то не так", "Упс!", JOptionPane.ERROR_MESSAGE);
                        Shake.shakeButton(btn2);
                        Shake.shakeButton(btn1);
                    }
                }

                else
                {
                    JOptionPane.showMessageDialog(null,"Incorrect login or password",
                            "Error",JOptionPane.ERROR_MESSAGE);

                }
            }
        });
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Registration();
            }
        });
    }


}