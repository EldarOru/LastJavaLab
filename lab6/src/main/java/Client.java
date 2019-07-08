import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;



public class Client {
    static String info1 = "";

    /*public static String readFile1(File file) {//обработка json на сервере
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.nextLine().trim();
                info1 += line;
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return info1;
    }*/
    public static void main(String args[]) throws Exception {
        System.out.println(
                "Для входа введите команду login;ваш_логин;ваш_пароль" + "\n"
                + "Для регистрации (пароль будет отправлен вам на почту) введите команду Registration;желаемый_логин;ваш_Email"+ "\n" );
        new Auto();
        while(true){
            try {//import
//System.out.println("Напишите import, если хотите импортировать свои данные на сервер"); 
/* DatagramSocket clientSocket1 = new DatagramSocket(); 
InetAddress IPAddress1 = InetAddress.getByName("localhost"); 
byte[] firstData = new byte[8192]; 
String info =readFile1(IslandManager.output); 
firstData = serialize(info); 
DatagramPacket firstPacket = new DatagramPacket(firstData,firstData.length,IPAddress1,9876); 
clientSocket1.send(firstPacket); 
clientSocket1.close();*/

//while (true) {//клиент не выключался при превышении времени 
//несколько клиентов обслуживания 
                BufferedReader inFromUser =
                        new BufferedReader(new InputStreamReader(System.in));
                DatagramSocket clientSocket = new DatagramSocket();//отправляет пакеты
                clientSocket.setSoTimeout(6000);
                InetAddress IPAddress = InetAddress.getByName("localhost"); //локальный хост
                byte[] sendData = new byte[8192];
                byte[] receiveData = new byte[1024];



                String sentence = inFromUser.readLine();
                if (sentence.equalsIgnoreCase("Import")){
                    String info = IslandManager.readFileFromClient(IslandManager.outputBackUp);
                    sentence += info;
                    System.out.println(sentence);
                }


                sendData = serialize(sentence);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
                clientSocket.send(sendPacket); //отправка пакета
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);//прием пакета данных
                clientSocket.receive(receivePacket);
                receiveData = receivePacket.getData();
                String modifiedSentence = (String)deserialize(receiveData);//расшифровка пакета
                System.out.println(modifiedSentence);
                clientSocket.close();


/*if (modifiedSentence.startsWith("[")&& !modifiedSentence.endsWith(".")){ 
String collection = modifiedSentence.substring(0,modifiedSentence.indexOf("]")+1); 
try { 
FileWriter fileWriter = new FileWriter(IslandManager.output); 
BufferedWriter bufferedWriter = new BufferedWriter(fileWriter); 
bufferedWriter.write(collection); 
bufferedWriter.flush(); 
bufferedWriter.close(); 
}catch (IOException e){ 
e.printStackTrace(); 
} 
}*/
                if ((modifiedSentence.endsWith("т") || modifiedSentence.endsWith(".")) && modifiedSentence.startsWith("[")) {
                    String collection = modifiedSentence.substring(0,modifiedSentence.indexOf("]")+1);
                    try {

                        FileWriter fileWriter = new FileWriter(IslandManager.outputBackUp);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(collection);
                        bufferedWriter.flush();
                        bufferedWriter.close();


                    }catch (IOException e){
                        e.printStackTrace();

                    }
                    break;
                }
                if (sentence.equals("exit")){
                    System.out.println("Отключение от сервера...");
                    break;
                }


            } catch (SocketTimeoutException e){
                System.out.println("Время ожидания превысило допустимое...");
                System.out.println("Проверьте правильность подключения или повторите попытку позже.");
                System.out.println("Для отключения введите команду exit");
                Scanner input = new Scanner(System.in);
                String command = input.nextLine();
                if (command.equalsIgnoreCase("exit")){
                    System.out.println("Отключение клиента");
                    break;
                }
            }//отлавливает время ожидания
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