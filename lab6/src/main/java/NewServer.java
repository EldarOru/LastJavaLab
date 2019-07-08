

import java.io.*;
import java.net.*;


public class NewServer implements Runnable {
    SocketAddress address;
    private DatagramSocket serverSocket;

    static File ServerOut = new File("OutputServer.json");
    static String objects = "";
    private byte[] in;
    private byte[] out;
    public NewServer() throws SocketException {
        serverSocket = new DatagramSocket(9876);
    }
    @Override
    public void run() {
        IslandManager islandManager = new IslandManager();
        islandManager.createDirection();
        boolean permission = false;
        while (true) {
            try {
                in = new byte[1024];
                out = new byte[1024];

                DatagramPacket receivedPacket = new DatagramPacket(in, in.length);
                serverSocket.receive(receivedPacket);
                in = receivedPacket.getData();
                String command = null;
                try {
                     command = (String) deserialize(in);
                }catch (ClassNotFoundException ex){
                    ex.getException();
                }
                System.out.println(command);
                islandManager.setMessage("");

                    if (command.length()> 7 && command.equalsIgnoreCase("showtable")){
                        islandManager.addMessageWithN(islandManager.showTable());
                    }
                   // else if (command.length()>3 && command.substring(0,3).equalsIgnoreCase("add")){
                     //   islandManager.addNormal(command);
                    else if (command.length()> 2 && command.equalsIgnoreCase("exit")){
                        islandManager.fromListToTable(islandManager.thatUser);
                        permission = false;
                    }else if (command.length()> 5 && command.equalsIgnoreCase("removelast")){
                        islandManager.removeLast();
                    }else if (command.length() > 2 && command.equalsIgnoreCase("show")){
                        islandManager.showCollection();
                    }




                    if (command.length() > 13 && command.substring(0, 12).equalsIgnoreCase("Registration")) {
                        //  islandManager.addMessageWithN("aaa");
                        islandManager.registration(command);

                    }else if (command.length() > 6 && command.substring(0, 5).equalsIgnoreCase("login")) {
                        String trueOrFalse = islandManager.loginUser(command);
                        System.out.println(trueOrFalse);
                        if (trueOrFalse == "Вы успешно вошли!") {
                            islandManager.addMessageWithN("Вы успешно вошли!");
                            islandManager.addMessageWithN("(*≧ω≦*)");
                         //   islandManager.createVectorFromTable1(islandManager.thatUser);
                            islandManager.info();
                        }else {
                            islandManager.addMessageWithN(trueOrFalse);
                        }
                    }else if (command.equalsIgnoreCase("exit")){
                        islandManager.addMessageWithN("Увидимся (づ.◕‿‿◕.)づ・。*。✧・゜゜・。✧ 。*・゜゜・✧。・ ゜゜・");
                    }
                    else {
                        islandManager.addMessageWithN("АВТОРИЗИРУЙСЯ (╬ Ò﹏Ó)");
                    }


             /*
                islandManager.setMessage("");
                if (permission == false) {
                    islandManager.addMessageWithN("aaa");
                    if (command.length() > 13 && command.substring(0, 12).equalsIgnoreCase("Registration")) {
                        islandManager.addMessageWithN("aaa");
                        islandManager.registration(command);
                    } else if (command.length() > 13 && command.substring(0, 5).equalsIgnoreCase("login")) {

                        permission = true;

                    } else {
                        islandManager.addMessageWithN("АВТОРИЗИРУЙСЯ");
                    }
                }
                if (permission) {
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
                        } УДАААЛИИИ
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


                    } else {
                        islandManager.addMessageWithN(("Команда не найдена"));
                        islandManager.addMessageWithN(("Введите help для получения информации о командах"));
                    }туут
                }*/
                        /*byte[] sendData = new byte[8192];
                        try {
                            sendData = serialize(islandManager.getMessage());
                            //данные для отправки на клиент
                            //ByteBuffer buffer = ByteBuffer.wrap("lol".getBytes());
                        }catch (IOException K){

                        }*/
                InetAddress IPAddress = receivedPacket.getAddress();
                int port = receivedPacket.getPort();
                in = serialize(islandManager.getMessage());
                DatagramPacket sendPacket = new DatagramPacket(in, in.length, IPAddress, port);
                serverSocket.send(sendPacket);

            } catch (IOException EX) {
                EX.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        try {
            Thread thread = new Thread( new NewServer(),"Server");
            thread.start();

        }catch (SocketException ex){
            ex.getStackTrace();
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
}
