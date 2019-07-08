import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class UserPage1 {

    static ArrayList<Color> colors = new ArrayList<>();
    static ArrayList<String> users = new ArrayList();
    TableModel tableModel;
    ArrayList<CircleComponent> circles = new ArrayList<>();
    JLabel m2;
    JPanel center = new JPanel();
    JTable table;
    JButton remove,addj,poradok;
    JTextField jname,jposition,jweight,filter;
    String[] columnNames = {
            "Name",
            "Direction",
            "Weight",
            "Date",
            "Creator"
    };

    Object [][] moreinfo;
    Locale localeRu = new Locale("ru","RU");

    Object[][] createTable(List<Thing> vector){

        for (int i = 0; i<vector.size();i++){

            moreinfo[i][0] = vector.get(i).getName();
            moreinfo[i][1] = vector.get(i).getDirection();
            moreinfo[i][2] = vector.get(i).getWeight();
            String k = DateFormat.getDateInstance(DateFormat.FULL,localeRu).format(vector.get(i).thingDate);

            moreinfo[i][3] = k;
            moreinfo[i][4] = vector.get(i).getCreator();

        }return moreinfo;
    }//доделать
    public void userList() {

    }
    public void toVector(List<Thing> vector) {
        while (!vector.isEmpty()) {
            for (int j = 0; j < vector.size(); j++) {
                vector.remove(j);
            }
        }
        for (int j = 0; j < tableModel.getRowCount(); j++) {
            String surname = tableModel.getValueAt(j, 0).toString();
            String surposition = tableModel.getValueAt(j, 1).toString();
            String surweight = tableModel.getValueAt(j, 2).toString();
            int weight = Integer.parseInt(surweight);
            String maker = tableModel.getValueAt(j, 4).toString();
            vector.add(new Thing(surname, surposition, weight, maker));


        }
    }

    IslandManager islandManager = new IslandManager();
    //все будем делать через коллекцию
    UserPage1() {
        //TODO ТАБЛИЦУ

        islandManager.createDirection();
        JFrame jFrame = new JFrame("Домашняя страница пользователя " + Methods.name);
        addj = new JButton("Добавить");
        remove = new JButton("Удалить");
        poradok = new JButton("Фильтр");
        islandManager.createVectorFromTable();
        tableModel = new MyTable();
        filter = new JTextField("Фильтр");
        table = new JTable(tableModel);
        ((MyTable) tableModel).setColumnIdentifiers(columnNames);
        moreinfo = new Object[islandManager.vectorJSON.size()][5];
        moreinfo = createTable(islandManager.vectorJSON);
        for (int i = 0; i < moreinfo.length; i++) {
            ((MyTable) tableModel).addRow(moreinfo[i]);
        }


        center.setLayout(null);

        //  CircleComponent component = new CircleComponent(10);
        //component.setLocation(5,5);
        //component.setSize(component.getPreferredSize());
        //center.add(component);
        circles();

        center.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                for (CircleComponent circleComponent : circles) {

                    if ((x < circleComponent.getX() + 20 && x > circleComponent.getX() - 20) && (y < circleComponent.getY() + 20 && y > circleComponent.getY() - 20)) {
                        if (circleComponent.isMoving){
                            circleComponent.isMoving = false;
                        }else {
                            circleComponent.shakeCircle(center);
                            JOptionPane.showMessageDialog(null, " Место " + circleComponent.getDirection1() + " Размер " + circleComponent.getWeight1() + " Создатель " + circleComponent.getCreator(), circleComponent.getName1(), JOptionPane.INFORMATION_MESSAGE);
                            circleComponent.moveCircle(center);
                        }
                      /*  int dx =0;
                        while (dx < 100) {
                            try {
                                wait();
                                circleComponent.setLocation(circleComponent.getX() + 1, circleComponent.getY() + 1);
                                dx += 1;
                                center.repaint();
                            }catch (InterruptedException ex){

                            }
                        }*/
                    }
                }
            }
        });
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        table.setPreferredScrollableViewportSize(new Dimension(100, 100));
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        jFrame.add(buttons);
        buttons.add(remove);
        buttons.add(addj);
        buttons.add(poradok);
        // buttons.add(new JScrollPane(table));
        jname = new JTextField("Имя объекта");
        jposition = new JTextField("Позиция");
        jweight = new JTextField("Вес");
        buttons.add(filter);
        buttons.add(jposition);
        buttons.add(jweight);
        buttons.add(jname);

        JPanel mem = new JPanel();
        mem.setLayout(new BorderLayout());

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        main.add(buttons, BorderLayout.NORTH);
        mem.add(new JScrollPane(table), BorderLayout.SOUTH);
        mem.add(center, BorderLayout.CENTER);

        main.add(mem);

        jFrame.add(main);
        jFrame.setSize(700, 600);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        RowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);


        table.setFillsViewportHeight(true);

        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                toVector(islandManager.vectorJSON);
                islandManager.fromListToTable(Methods.name);
                center.repaint();
                circles();
            }
        });



        //Box contents = new Box(BoxLayout.Y_AXIS);
        //contents.add(new JOptionPane(new JScrollPane(table)));
        jFrame.setBackground(Color.RED);
        islandManager.allUsers(users);

        Random random = new Random();
        for (int a =0;a<users.size();a++){
            float r = random.nextFloat();
            float g = random.nextFloat();
            float b = random.nextFloat();
            colors.add(new Color(r,g,b));
        }
       // jFrame.add(new JScrollPane(table));
        //jFrame.add(remove);
        //jFrame.add(addj);
        //jFrame.add(vod);
        //jFrame.add(jname);
        //jFrame.add(jposition);
        //jFrame.add(jweight);
        jFrame.setVisible(true);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int inx = -1;
                inx = table.getSelectedRow();
                System.out.println(tableModel.getRowCount());
                if (inx == -1) {
                    JOptionPane.showMessageDialog(null, "Вы ничего не выбрали", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } else{
                    String user = table.getValueAt(inx, 4).toString();
                if (user.equals(Methods.name)) {
                    for (int x = 0; x<tableModel.getRowCount();x++){//тупа чекаю да
                        System.out.println(tableModel.getValueAt(x,0));
                    }
                    ((MyTable) tableModel).removeRow(inx);

                    toVector(islandManager.vectorJSON);

                    //TODO ЗАКОМЕНТЬ
                   // updateTable(islandManager.vectorJSON);
                    islandManager.fromListToTable(Methods.name);
                    center.removeAll();
                    center.repaint();
                    circles();
                } else {
                    JOptionPane.showMessageDialog(null, "Это не ваш предмет", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        });

        //добавление в таблицу сделано
        addj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] objects = new Object[5];
                int trueWeight;
                String name = jname.getText();
                String position = jposition.getText();
                String weight = jweight.getText();
                try {
                    trueWeight = Integer.parseInt(weight);

                    if (position.equals("s") || position.equals("n") || position.equals("e") || position.equals("w") && !name.isEmpty()) {
                        objects[0] = name;
                        objects[1] = position;
                        objects[2] = trueWeight;
                        String k = DateFormat.getDateInstance(DateFormat.FULL,localeRu).format(new Date());
                        String j = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.ENGLISH).format(new Date());
                        objects[3] = k;
                        objects[4] = Methods.name;
                        ((MyTable) tableModel).addRow(objects);


                    }

                    toVector(islandManager.vectorJSON);
                    islandManager.fromListToTable(Methods.name);
                    circles();


                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Вес - это число", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }

            }


        });
        //ненужная кнопка зачем я её ваще сделал
        /*
        poradok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                islandManager.SortByWeight();
                int rows = tableModel.getRowCount();
                for(int i = rows - 1; i >=0; i--)
                {
                    ((MyTable) tableModel).removeRow(i);
                }
                updateTable(islandManager.vectorJSON);

            }
        });*/

        poradok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String eboy = filter.getText();
                int chosen = -1;
                 chosen = table.getSelectedColumn();
                if (eboy.isEmpty() || eboy.equalsIgnoreCase("фильтр")|| chosen == -1) {

                    ((TableRowSorter<TableModel>) sorter).setRowFilter(null);
                }else if (eboy.equalsIgnoreCase("back")){

                }
                else {

                    try {

                        ((TableRowSorter<TableModel>) sorter).setRowFilter(

                                RowFilter.regexFilter(eboy,chosen));

                    } catch (PatternSyntaxException pse) {

                        System.err.println("Bad regex pattern");
                    }
                }
            }
        });
        }

    public void updateTable(List<Thing> vector){
        while (tableModel.getRowCount()!= 0 ) {

            for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
                ((MyTable) tableModel).removeRow(i);
            }
        }
        for (int j = 0; j<vector.size();j++){
            Object object [] = new Object[5];
            object[0] = vector.get(j).getName();
            object[1] = vector.get(j).getDirection();
            object[2] = vector.get(j).getWeight();
            String k = DateFormat.getDateInstance(DateFormat.FULL,localeRu).format(vector.get(j).thingDate);
            object[3] = k;
            object[4] = vector.get(j).getCreator();
            ((MyTable) tableModel).addRow(object);
        }
    }

    public void circles(){
        center.removeAll();
        center.repaint();

        int x = 0;
        int v = 0;
        int z = 0;
        int e = 0;
        int n = 0;
        while (!circles.isEmpty()){
            for (int k = 0; k<circles.size();k++){
                circles.remove(k);
            }
        }
        for (int j = 0; j<islandManager.vectorJSON.size();j++) {
            if (islandManager.vectorJSON.get(j).getDirection().equals("s")) {
                circles.add(new CircleComponent(20, islandManager.vectorJSON.get(j).getName(), islandManager.vectorJSON.get(j).getDirection(), islandManager.vectorJSON.get(j).getWeight(), islandManager.vectorJSON.get(j).getCreator()));
                circles.get(z).setLocation(150 + x, 300);
                circles.get(z).setSize(circles.get(z).getPreferredSize());

                center.add(circles.get(z));
                x += 30;
                z += 1;
            }
            if (islandManager.vectorJSON.get(j).getDirection().equals("w")) {
                circles.add(new CircleComponent(20, islandManager.vectorJSON.get(j).getName(), islandManager.vectorJSON.get(j).getDirection(), islandManager.vectorJSON.get(j).getWeight(), islandManager.vectorJSON.get(j).getCreator()));
                circles.get(z).setLocation(5, 100 + v);
                circles.get(z).setSize(circles.get(z).getPreferredSize());

                center.add(circles.get(z));
                v += 30;
                z += 1;
            }//работает)
            if (islandManager.vectorJSON.get(j).getDirection().equals("e")) {
                circles.add(new CircleComponent(20, islandManager.vectorJSON.get(j).getName(), islandManager.vectorJSON.get(j).getDirection(), islandManager.vectorJSON.get(j).getWeight(), islandManager.vectorJSON.get(j).getCreator()));
                circles.get(z).setLocation(650, 100 + e);
                circles.get(z).setSize(circles.get(z).getPreferredSize());

                center.add(circles.get(z));
                e += 30;
                z += 1;
            }
            if (islandManager.vectorJSON.get(j).getDirection().equals("n")) {
                circles.add(new CircleComponent(20, islandManager.vectorJSON.get(j).getName(), islandManager.vectorJSON.get(j).getDirection(), islandManager.vectorJSON.get(j).getWeight(), islandManager.vectorJSON.get(j).getCreator()));
                circles.get(z).setLocation(150 +n, 50 );
                circles.get(z).setSize(circles.get(z).getPreferredSize());

                center.add(circles.get(z));
                n += 30;
                z += 1;
            }
        }

    }

}
