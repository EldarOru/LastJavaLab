import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Registration extends Methods {
    JLabel l1, l2, l3;
    JTextField tf1, tf2;
    JButton btn1, btn2;

//все сделано
    Registration() {
        JFrame frame = new JFrame("Login Form");
        frame.setVisible(true);
        frame.setSize(700, 700);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Registration Form in Java");
        l1 = new JLabel("Registration Form in Windows Form:");
        l1.setForeground(Color.blue);
        l1.setFont(new Font("Serif", Font.BOLD, 20));
        l2 = new JLabel("Name:");
        l3 = new JLabel("Email-ID:");
        tf1 = new JTextField();
        tf2 = new JTextField();
        btn1 = new JButton("Submit");
        btn2 = new JButton("Back");


        l1.setBounds(100, 30, 400, 30);
        l2.setBounds(80, 70, 200, 30);
        l3.setBounds(80, 110, 200, 30);
        tf1.setBounds(300, 70, 200, 30);
        tf2.setBounds(300, 110, 200, 30);
        btn1.setBounds(50, 350, 100, 30);
        btn2.setBounds(170, 350, 100, 30);
        frame.add(btn1);
        frame.add(btn2);
        frame.add(l1);
        frame.add(l2);
        frame.add(tf1);
        frame.add(l3);
        frame.add(tf2);

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    String name = tf1.getText();
                    String email = tf2.getText();
                    if (!name.equals("") && !email.equals("")) {
                        send("registration;" + name + ";" + email);
                        String back = receive();
                        System.out.println(back);
                        if (back.startsWith("Вам Email не валидный")) {
                            JOptionPane.showMessageDialog(null, "Вам Email не валидный", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        } else if (back.startsWith("Пользователь с таким логином и/или Email`ом уже существует!")) {
                            JOptionPane.showMessageDialog(null, "Пользователь с таким логином и/или Email`ом уже существует!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        } else if (back.startsWith("Вы успешно зарегистрировались!")) {
                            JOptionPane.showMessageDialog(null, "Пароль выслан на почту");
                        } else {
                            JOptionPane.showMessageDialog(null, "Что-то неправильно", "Упс!", JOptionPane.ERROR_MESSAGE);
                        }
                    }

            }
        });
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }
}
