import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Methods extends JFrame {
    Locale locale = new Locale("ru","RU");
    ResourceBundle resourceBundle = ResourceBundle.getBundle("language",locale);
    static String name = "f";
    static DatagramSocket datagramSocket;
    static {

        try {
            datagramSocket = new DatagramSocket();
        }catch (SocketException ex){
            ex.getStackTrace();
        }
    }
    static void send(String sentence) {
        try {

            byte[] sendData = new byte[8192];
            sendData = serialize(sentence);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("localhost"), 9876);
            datagramSocket.send(sendPacket); //отправка пакета
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    static String receive() {


        String modifiedSentence = "";
        try {

            datagramSocket.setSoTimeout(2000);
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);//прием пакета данных

                datagramSocket.receive(receivePacket);

                receiveData = receivePacket.getData();
                modifiedSentence = (String) deserialize(receiveData);//расшифровка пакета
                System.out.println(modifiedSentence);
            }catch (IOException ex){
            System.out.println("=c");
        }catch (ClassNotFoundException ex){
            System.out.println("=c");
        }
        return modifiedSentence;

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
