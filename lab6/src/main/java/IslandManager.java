import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class IslandManager extends MessageServer {
    List<Thing> vectorJSON = Collections.synchronizedList(new Vector<Thing>());
    private String message = "";
    static String  thatUser;
    Island island = new Island("Мадагаскар");
    Island island_north = new Island("Северная часть острова");
    Island island_south = new Island("Южная часть острова");
    Island island_east = new Island("Восточная часть острова");
    Island island_west = new Island("Западная часть острова");
    Mother_Mumi mother_mumi = new Mother_Mumi("Муми Мама", new Boil("кофе"), new Search("банку с маслом"));
    Sniff sniff = new Sniff("Снифф", new Dig ("песок"));
    Snusmumrik snusmumrik = new Snusmumrik("Снусмурик", new Swim("море"), new See("золотое небо"));
    public void displayGreetings(){
        addMessageWithN("Приветствую");
    }
    public final Date createDate;
    static File file = new File("Input.json");
    public static File output = new File("Output.json");
    static File outputBackUp = new File("OutputBackup.json");
    String fileName = "Output";
    String format = ".json";
    static String infoFromClient = " ";
    String info = new String(" ");
    String outInfo = new String(" ");
    IslandManager(){
        createDate = new Date();
    }
    public void createDirection(){
        island.AddInnerLocation(island_north, island_south, island_east, island_west);
    }
    /**

     * Метод для чтения текста из файла fileName

     * @return String info - строка-содержимое файла

     */
    public String readFile(File file){
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.nextLine().trim();
                info += line;
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        addMessageWithN(info);
        return info;
    }
    public static String readFileFromClient(File file){

        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.nextLine().trim();
                infoFromClient += line;
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return infoFromClient;
    }
    /**
     * Метод, создающий коллекцию vectorJson по данным из файла info
     */
    public List createVector(String str) {//шаблон фабрик автосохранение бэкап
        JSONParser parser = new JSONParser();
        if (!str.isEmpty()){

        try {
            JSONArray a = (JSONArray) parser.parse(str);
            for (Object o : a) {
                JSONObject thing = (JSONObject) o;
//чекай
                String name = (String) thing.get("name");
                String d = (String) thing.get("direction");
                int w = (int) thing.get("weight");
                vectorJSON.add(new Thing(name,d,w,thatUser));
            }
        } catch (ParseException e) {
            System.out.println("Боюсь, данные в неправильном формате");
        }
        }return vectorJSON;
}
    public void importFromClient(String string){
        if (string.length()>7) {
            while (!vectorJSON.isEmpty()) {
                for (int i = 0; i < vectorJSON.size(); i++) {
                    vectorJSON.remove(i);
                }
            }
            String lol = string.substring(6);
            removeAll();
            createVector(lol);
            creation();
            addMessageWithN("Данные успешно импортированы");
        }else {
            addMessageWithN("Клиент изначально пуст");
            }
        }

    public void removeAll(){
        while (!island_east.InnerThing.isEmpty()) {
            for (int l = 0; l < island_east.InnerThing.size(); l++) {
                island_east.InnerThing.remove(l);
            }
        }
        while (!island_north.InnerThing.isEmpty()) {
            for (int l = 0; l < island_north.InnerThing.size(); l++) {
                island_north.InnerThing.remove(l);
            }
        }
        while (!island_south.InnerThing.isEmpty()) {
            for (int l = 0; l < island_south.InnerThing.size(); l++) {
                island_south.InnerThing.remove(l);
            }
        }
        while (!island_west.InnerThing.isEmpty()) {
            for (int l = 0; l < island_west.InnerThing.size(); l++) {
                island_west.InnerThing.remove(l);
            }
        }

    }
    /**
    * Метод, удаляющий последний объект в коллекции
     * Во многих методах могут быть методы removeAll и creation.
     * Они нужды для того, чтобы после изменения коллекции заново отсортировать объекты по сторонам света
     */
    public void removeLast(){
        if (vectorJSON.isEmpty()){
            addMessageWithN("Коллекция пуста.");
            return;
        }else{
            addMessageWithN("Элемент " + vectorJSON.get(vectorJSON.size() - 1).name + " удален.");
            vectorJSON.remove(vectorJSON.size() - 1);
            removeAll();
            creation();
            save(true);
        }
    }

    /**
     * Метод, сортирующий коллекцию в обратном порядке
     */
    public void reorder(){
        if (vectorJSON.isEmpty()) {
            addMessageWithN("Коллекция пуста.");
            return;
        }else{
        Collections.reverse(vectorJSON);
        addMessageWithN("Коллекция выстроена в обратном порядке");
        }
    }

    /**
     * Метод для получения помощи по командам
     */
    public void help(){
        addMessageWithN("revomeLast для удаления последнего объект");
        addMessageWithN("info для получения информации");
        addMessageWithN("reorder для обратного порядка");
        addMessageWithN("exit для выхода");
        addMessageWithN("show для показа");
        addMessageWithN("add [] для добавления нового объекта в коллекци.");
        addMessageWithN("SortByLength для сортировки по размеру");
        addMessageWithN("addNormal для добавления предмета НЕ в формате json. Пример: kamushek,s,123");
        addMessageWithN("save для сохранения данных на сервере");
        addMessageWithN("load для сохранения данных в клиенте");
    }

    /**
     * Метод для сортировки объектов вектора по сторонам света
     */
    public void creation( ) {
        for (Thing thing : vectorJSON) {
            if (thing.direction.equals("n")) {
                island_north.InnerThing.add(thing);

            } else if (thing.direction.equals("s")) {
                island_south.InnerThing.add(thing);

            } else if (thing.direction.equals("w")) {
                island_west.InnerThing.add(thing);

            } else if (thing.direction.equals("e")) {
                island_east.InnerThing.add(thing);

            } else {
                addMessageWithN("Неверная локация объекта " + thing.name);
            }
        }
    }
    /**

     * add {element} Метод для добавления элемента в коллекцию в интерактивном режиме

     * Формат задания элемента {element}- json

     * При вводе {element} другого формата или при вводе некорректного представления объекта - бросается исключение

     */
    public List add(String command)  {
        JSONParser parser = new JSONParser();
        try {
            JSONArray a = (JSONArray) parser.parse(command);
            for (Object ob1 : a) {
                JSONObject ob = (JSONObject) ob1;
                    String name = (String) ob.get("name");
                    String d = (String) ob.get("direction");
                    String w = (String) ob.get("weight");
                    int r = Integer.parseInt(w);
                    vectorJSON.add(new Thing(name, d,r,thatUser));
                break;
            }
            addMessageWithN("Объект " + vectorJSON.get(vectorJSON.size()-1).name + " добавлен");
            removeAll();
            creation();
            save(true);
        } catch (Exception e) {
            addMessageWithN("Неподобающий формат");
        }return vectorJSON;
    }

    /**
     * Метод для получения из вектора String в Json формате
     * @return String с информацией о векторе в Json формате
     */
    public String toJson(){
        outInfo = "";
        outInfo += "[";
        SortByWeight();
        for (Thing thing: vectorJSON){
           JSONObject jsonObject = new JSONObject();
           //if (outInfo.endsWith("]"))

               jsonObject.put("name",thing.name);
               jsonObject.put("direction",thing.direction);
               jsonObject.put("weight", thing.weight);
               String objectInfo = jsonObject.toJSONString();
               outInfo += objectInfo;
               outInfo += ",";

        }if (outInfo != null && outInfo.length() > 0 && outInfo.charAt(outInfo.length() - 1) == ',') {
            outInfo = outInfo.substring(0, outInfo.length() - 1);
        }
        outInfo += "]";
        return outInfo;
    }

    /**
     * Метод для сохранения
     * @param copy true - сохранение в BackUp, false - сохранение в главный файл
     */
    public void save(boolean copy) {
        toJson();
        String copyString = "";
        if (copy){
            try {
                FileWriter fileWriter = new FileWriter(outputBackUp);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(outInfo);
                bufferedWriter.flush();
                bufferedWriter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else {
            try {
                FileWriter fileWriter = new FileWriter(output);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(outInfo);
                bufferedWriter.flush();
                bufferedWriter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Удаление объекта из вектора по его имени и расположению
     * @param string На вход идет всё сообщение пользователя
     */
    public void remove(String string) {
        String nameObject = "";
        String location = "";
        String thatObject = "";
                String weightS = "";
                double weightD = 0;
        if (vectorJSON.isEmpty()) {
            addMessageWithN("Коллекция пуста.");
            return;
        }else {
            try {
                thatObject = string.substring(7,string.length()-1+1);
                addMessageWithN(thatObject);
                String[] removeList = thatObject.split(",");
                nameObject = removeList[0];
                location = removeList[1];
                weightS = removeList[2];
                weightD = Double.parseDouble(weightS);
                for (int i = 0; i < vectorJSON.size(); i++) {
                    if (vectorJSON.get(i).name.equalsIgnoreCase(nameObject)&& vectorJSON.get(i).direction.equalsIgnoreCase(location)&& vectorJSON.get(i).weight == weightD) {
                        addMessageWithN("Объект " + nameObject + " был удален");
                        vectorJSON.remove(i);
                    }
                }
                save(true);
                removeAll();
                creation();
            }catch (Exception ex){
                addMessageWithN("Такого объекта нет!");
            }
        }
    }

    /**
     * Добавление в вектор нового объекта НЕ в формате Json
     * @param string Сообщение пользователя
     */
  /*  public void addNormal(String string){
        String nameObject1 = "";
        String location1 = "";
        String addObject1 = "";
        String weightS = "";
        int weightD = 0;
        try {
            addObject1 = string.substring(4,string.length()-1+1);
            String[] addList = addObject1.split(";");
            nameObject1 = addList[0];
            location1 = addList[1];
            weightS = addList[2];
            weightD = Integer.parseInt(weightS);
            vectorJSON.add(new Thing(nameObject1,location1,weightD));
            removeAll();
            creation();
            save(true);
            addMessageWithN("Объект " + nameObject1 + " был успешно добавлен");
        }catch (Exception ex){
            addMessageWithN("Извините, но даже тут вы умудрились что-то неправильно ввести.");
            addMessageWithN("Мне вас жаль.");
        }
    }*/
    /**

     * Выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов)

     */
    public void info(){
        addMessageWithN("Тип коллекции: " + vectorJSON.getClass());
        addMessageWithN("Количество элементов в коллекци: " + vectorJSON.size());
        addMessageWithN("Дата создания: " + createDate);
    }
    public void TellStory(){//это и есть show
        //island_east.DescribeTrolls();//повествует о нелегком быте муми троллей
        // Проходит по ArrayList InnerLocation и рассказывает о каждой локации
        addMessageWithN(island.DescribeLocations());
        //mother_mumi.take(new Axe("топор","s"));//убери
    }
    public void showCollection(){
        for(int i = 0; i<vectorJSON.size(); i++){
            addMessageWithN(vectorJSON.get(i).name);
            addMessageWithN(vectorJSON.get(i).direction);
            String kek = String.valueOf(vectorJSON.get(i).weight);
            addMessageWithN(kek);
            addMessageWithN(vectorJSON.get(i).thingDate.toString());
            addMessageWithN("---------");
        }
    }
   /* public void Event(){
        Thing ship = new Thing("Корабль", "n", 444);
        island_north.AddInnerThing(ship);
        addMessageWithN(ship.name + " потерпел крушение в " + island_north.getName());
        island_east.GoTo(island_north,mother_mumi, snusmumrik);
        island_south.GoTo(island_north,sniff);
        int luck = (int)(Math.random()*10);
        if (luck>5){
            Treasure treasure = new Treasure("сокровища","n",50);
            island_north.AddInnerThing(treasure);
            for (Trolls trr : island_north.InnerTrolls){
                addMessage(trr.TrollName + ", ");
            }
            addMessageWithN("нашли " + treasure.name + "!");
            addMessage("Теперь там было ");
            for (Thing thing : island_north.InnerThing){
                addMessage(thing.getName() + ",");
            }
            addMessageWithN("");
            mother_mumi.take(treasure);
            try{
                mother_mumi.openChest(treasure);
            }

            catch (CheckException e){
                addMessageWithN(e.getMessage());
            }
            island_north.InnerThing.remove(treasure);
        }
        if (luck<=5){
            addMessageWithN(ship.name + " оказался пуст");
        }
island_north.InnerThing.remove(ship);
        island_north.InnerTrolls.remove(sniff);
        island_north.InnerTrolls.remove(snusmumrik);
        island_north.InnerTrolls.remove(mother_mumi);
    }
*/
    public List SortByWeight() {
        vectorJSON = vectorJSON.stream().sorted((e1, e2) -> {
            if (e1.weight> e2.weight)
                return 1;
            if (e1.weight == e2.weight)
                return 0;
            return -1;
        }).collect(Collectors.toList());
        return vectorJSON;
    }
    //----------------------------------------------------------------------------------------------------------------------------------

    public void registration(String command) {
        String arbidol = command.substring(13);

        String[] nado1 = arbidol.split(";");
        if (nado1.length == 2) {
            try {
                DataBaseHandler dataBaseHandler = new DataBaseHandler();
                String logAndMail = command.substring(13);

                String[] nado = logAndMail.split(";");
                String Email = nado[1];
                final Properties properties = new Properties();
                String login = nado[0];

                String password = randomPassword();
                String secret = hashPassword(password);

                User user = new User(login, secret, Email);
                ResultSet resultSet = dataBaseHandler.checkUser(user);
                int count = 0;
                try {
                    while (resultSet.next()) {
                        count++;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (count == 0) {
                    if (validEmail(Email)) {
                        properties.load(new FileInputStream("C:\\Users\\macdo\\IdeaProjects\\lab6\\src\\main\\java\\mail.properties"));
                        Session mailSession = Session.getDefaultInstance(properties);
                        MimeMessage message = new MimeMessage(mailSession);
                        message.setFrom(new InternetAddress("jerarthebest@gmail.com"));
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(Email));
                        message.setSubject("Ваш пароль для авторизации в самом лучшем приложении");
                        message.setText("Дорогой " + login + " Держите этот пароль в секрете: " + password);

                        Transport transport = mailSession.getTransport();
                        transport.connect("jerarthebest@gmail.com", "donthackme123");
                        transport.sendMessage(message, message.getAllRecipients());
                        transport.close();

                        dataBaseHandler.signUpUser(user);
                        addMessageWithN("Вы успешно зарегистрировались!");
                        addMessageWithN("Зайдите на почту и проверьте пароль");
                        addMessageWithN("А потом вы сможете зайти");
                    } else {
                        addMessageWithN("Вам Email не валидный");
                    }
                } else {
                    addMessageWithN("Пользователь с таким логином и/или Email`ом уже существует!");
                }
            } catch (AddressException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            addMessageWithN("Не пытайся сломать программу");
        }
    }


    public String loginUser(String command) {
        String koks = null;
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        String data = command.substring(6);

        String[] information = data.split(";");

        if (information.length == 2) {
            User user = new User();
            thatUser = information[0];
            user.setLogin(information[0]);
            String rightPassword = hashPassword(information[1]);
            user.setPassword(rightPassword);
            ResultSet resultSet = dataBaseHandler.getUser(user);
            int c = 0;
            try {
                while (resultSet.next()) {
                    c++;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (c >= 1) {
                return koks = "Вы успешно вошли!";
            } else {
                return koks = "Что-то не так \t( ` ω ´ )";
            }
        }else return "Не пытайся сломать программу";
    }
    public void allUsers(List<String> list){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        ResultSet resultSet = dataBaseHandler.getAllUsers();
        try {
            while (resultSet.next()){
                list.add(resultSet.getString("login"));
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public String showTable(){
        String infoTable = "";
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        ResultSet resultSet = dataBaseHandler.getObject();
        addMessageWithN("Объекты во всей базе данных");
        try {
            while (resultSet.next()){
                String name = resultSet.getString("nameofobject");
                String direction = resultSet.getString("direction");
                int weight = resultSet.getInt("weight");
                String creator = resultSet.getString("creator");
                infoTable += "\nИмя:" + name + " Местоположение:" + direction + " Масса:" + weight + " Создатель:" + creator;
            }return infoTable;
        }catch (Exception ex){
            ex.printStackTrace();
        }return infoTable;
    }
    public void addTable(String command){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        String kek[] = command.substring(4).split(";");
        int weight = Integer.parseInt(kek[2]);
        if (kek[1].equals("s")||kek[1].equals("n")||kek[1].equals("w")||kek[1].equals("e")) {
            Thing thing = new Thing(kek[0], kek[1], weight,thatUser);
            dataBaseHandler.signUpObject(thing, thatUser);
        }else addMessageWithN("Установите правильно сторону света: n || s || w || e");
    }
    public void createVectorFromTable() {
        int i =0;
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        ResultSet resultSet = dataBaseHandler.getAllObjects();
        if (!vectorJSON.isEmpty()) {
            for (int j = 0; j < vectorJSON.size(); j++) {
                vectorJSON.remove(j);
            }
        }
            try {
                while (resultSet.next()) {
                    i++;
                    String name = resultSet.getString("nameofobject");
                    String direction = resultSet.getString("direction");
                    int weight = resultSet.getInt("weight");
                    String maker = resultSet.getString("creator");
                    if (vectorJSON.size() < i) {
                        vectorJSON.add(new Thing(name, direction, weight,maker));
                    }
                }
                addMessageWithN("Ваши объекты импортированы из базы данных в коллекцию для более удобной работы");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    public void createVectorFromTable1(String name) {
        int i =0;
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        ResultSet resultSet = dataBaseHandler.getAllUserObjects(name);
        if (!vectorJSON.isEmpty()) {
            for (int j = 0; j < vectorJSON.size(); j++) {
                vectorJSON.remove(j);
            }
        }
        try {
            while (resultSet.next()) {
                i++;
                String name1 = resultSet.getString("nameofobject");
                String direction = resultSet.getString("direction");
                int weight = resultSet.getInt("weight");
                String maker = resultSet.getString("creator");
                if (vectorJSON.size() < i) {
                    vectorJSON.add(new Thing(name1, direction, weight,maker));
                }
            }
            addMessageWithN("Ваши объекты импортированы из базы данных в коллекцию для более удобной работы");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Генерация пароля
     * @return пароль
     */
    public String randomPassword(){
        String password = new Random().ints(48,123)
                .filter(i -> (i <= 57 || (i >= 65 && i<=90) || i>=97))
                .limit(5)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint,StringBuilder::append)
                .toString();
        return password;
    }
    public String hashPassword(String password) {
        try {
            MessageDigest sha224 = MessageDigest.getInstance("SHA-224");
            byte[] bytes = sha224.digest(password.getBytes());
            StringBuilder builder = new StringBuilder();
            for (byte b : bytes){
                builder.append(String.format("%02X",b));
            }return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }return "lol";
    }
    public boolean validEmail(String email){
        int counter = 0;
        Pattern pattern = Pattern.compile("^((\\w|[-+])+(\\.[\\w-]+)*@[\\w-]+((\\.[\\d\\p{Alpha}]+)*(\\.\\p{Alpha}{2,})*)*)$");
        Matcher matcher = pattern.matcher(email);
        while (matcher.find()){
            counter++;
        }
        if (counter == 0){
            return false;
        }else return true;
    }
    /*
    public void newObject(Thing thing,String name, Date date){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.newSighUpObject(thing,name,date);
    }*/
    public void fromListToTable(String name){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.removeAllUserObjects(name);
        for(Thing thing : vectorJSON) {
            if (!thing.getCreator().equals(name)) {

            } else {
                dataBaseHandler.signUpObject(thing, name);
            }
        }
        addMessageWithN("Ваша коллекция загружена в базу данных");
    }
    @Override
    public void setMessage(String message) {
        super.setMessage(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void addMessage(String message) {
        super.addMessage(message);
    }

    @Override
    public void addMessageWithN(String message) {
        super.addMessageWithN(message);
    }
}
