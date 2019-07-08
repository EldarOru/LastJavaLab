

/*
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;


public class Server {

    private static IslandManager islandManager;
    static File ServerOut = new File("OutputServer.json");
    static String objects = "";
    static  int k = 0;
    public static void main(String args[]) throws SocketException {
        {


            System.out.println("Сервер запущен!");
            islandManager = new IslandManager();
            islandManager.createDirection();
            objects= islandManager.readFile(ServerOut);
            System.out.println(objects);
            islandManager.createVector(objects);
            System.out.println(islandManager.vectorJSON.get(0).getName());
            islandManager.creation();
            try {
                DatagramChannel channel = DatagramChannel.open();//Открывает канал дейтаграммы.

                 boolean permission = false;
                channel.bind(new InetSocketAddress(9876));
                //Этот метод используется, чтобы установить ассоциацию между сокетом и локальным адресом.
                // Как только ассоциация устанавливается тогда, сокет остается связанным, пока канал не закрывается.
                // Если local у параметра есть значение null тогда сокет будет связан с адресом, который присваивается автоматически.

                while (true) {

                    Thread t = new Thread(() -> {//поток и лямбда выражение
                        SocketAddress address;
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        buf.clear();

                         //TODO SYKA
                        try {
                            address = channel.receive(buf);//принятия пакета данных
                        } catch (IOException ex) {
                            address = null;
                            ex.printStackTrace();
                        }
                        System.out.println("Команда успешно принята");
                        String command = null;
                        try {
                            command = (String) deserialize(buf.array());
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        System.out.println(command);
                        islandManager.setMessage("");

                        if (k==1 ) {
                            if (command.equals("removeLast")) {
                                islandManager.removeLast();
                            } else if (command.length() > 6 && command.substring(0, 6).equalsIgnoreCase("Import")) {
                            /*while (!islandManager.vectorJSON.isEmpty()){
                                for (int i = 0; i<islandManager.vectorJSON.size();i++) {
                                    islandManager.vectorJSON.remove(i);
                                }
                            }
                            String lol = command.substring(6);
                            System.out.println(lol);
                            islandManager.removeAll();
                            islandManager.createVector(lol);
                            islandManager.creation();
                            islandManager.addMessageWithN("Данные успешно импортированы");

                                islandManager.importFromClient(command);
                            }
                        else if (command.startsWith("[")){
                            islandManager.createDirection();
                            islandManager.createVector(command);
                            islandManager.creation();
                        }
                            else if (command.equalsIgnoreCase("save")) {
                                String fakgoback = islandManager.toJson();
                                try {
                                    FileWriter fileWriter = new FileWriter(ServerOut);
                                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                    bufferedWriter.write(fakgoback);
                                    bufferedWriter.flush();
                                    bufferedWriter.close();
                                    islandManager.addMessageWithN("Данные успешные сохранены на сервере");
                                } catch (IOException e) {
                                    e.printStackTrace();

                                }
                            } else if (command.equals("reorder")) {
                                islandManager.reorder();
                                System.out.println("Почему не работает?");
                            } else if (command.equals("info")) {
                                islandManager.info();

                            } else if (command.equalsIgnoreCase("showCollection") && command.length() > 8) {
                                islandManager.showCollection();
                            } else if (command.equals("show")) {//пофикси
                                islandManager.addMessageWithN("Северная часть остров");
                                for (int i = 0; i < islandManager.island_north.InnerThing.size(); i++) {
                                    islandManager.addMessageWithN(islandManager.island_north.InnerThing.get(i).getName());
                                }
                                islandManager.addMessageWithN("Южная часть остров");
                                for (int i = 0; i < islandManager.island_south.InnerThing.size(); i++) {
                                    islandManager.addMessageWithN(islandManager.island_south.InnerThing.get(i).getName());
                                }
                                islandManager.addMessageWithN("Западная часть остров");
                                for (int i = 0; i < islandManager.island_west.InnerThing.size(); i++) {
                                    islandManager.addMessageWithN(islandManager.island_west.InnerThing.get(i).getName());
                                }
                                islandManager.addMessageWithN("Восточная часть остров");
                                for (int i = 0; i < islandManager.island_east.InnerThing.size(); i++) {
                                    islandManager.addMessageWithN(islandManager.island_east.InnerThing.get(i).getName());
                                }
                                islandManager.Event();
                                // islandManager.addMessageWithN(islandManager.island_west.InnerThing.get(1).getName());

                            } else if (command.length() > 8 && command.substring(0, 9).equalsIgnoreCase("addNormal")) {
                                islandManager.addNormal(command);
                            } else if (command.equals("help")) {
                                islandManager.help();
                            } else if (command.length() > 6 && command.substring(0, 6).equals("remove")) {
                                islandManager.remove(command);
                            } else if (command.length() > 3 && command.substring(0, 3).equals("add")) {
                                String kek = (command.substring(3, command.length() - 1 + 1));
                                islandManager.addMessageWithN((kek));
                                islandManager.add(kek);
                            } else if (command.equalsIgnoreCase("load")) {
                                islandManager.addMessageWithN(islandManager.toJson());
                                islandManager.addMessageWithN("Данные успешно загружены в ваш клиент");
                            } else if (command.equalsIgnoreCase("exit")) {
                                islandManager.addMessageWithN(islandManager.toJson());
                                islandManager.addMessageWithN(("Отключение от сервера..."));


                            }
                            else {
                                islandManager.addMessageWithN(("Команда не найдена"));
                                islandManager.addMessageWithN(("Введите help для получения информации о командах"));
                            }
                        }if (k==0){
                            islandManager.addMessageWithN("aaa");
                            if ( command.length()>13&&command.substring(0,12).equalsIgnoreCase("Registration")){
                                islandManager.addMessageWithN("aaa");
                                islandManager.registration(command);
                            }else if (command.length()>13 && command.substring(0,5).equalsIgnoreCase("login")){


                                k=+1;
                            }
                            else {
                                islandManager.addMessageWithN("АВТОРИЗИРУЙСЯ");
                            }
                        }
                        /*byte[] sendData = new byte[8192];
                        try {
                            sendData = serialize(islandManager.getMessage());
                            //данные для отправки на клиент
                            //ByteBuffer buffer = ByteBuffer.wrap("lol".getBytes());
                        }catch (IOException K){

                        }
                        ByteBuffer buffer = ByteBuffer.wrap(islandManager.getMessage().getBytes());
                        try {
                            channel.send(buffer, address);//отправка сообщения
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    );
                    t.start();

                }
            }catch (IOException E){
            }
        }
    }
    private static byte[] serialize(String str) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeUTF(str);
        oos.flush();
        oos.close();
        return bos.toByteArray();
    }
    private static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bos = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bos);
        Object obj = ois.readUTF();
        ois.close();
        return obj;
    }
}*/