import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.DatagramPacket;

public class ChatApp {
    private String userName;
    private String multicastAddress;
    private int port;
    private MulticastSocket socket;
    private InetAddress group;

    public ChatApp(String userName, String multicastAddress, int port) {
        this.userName = userName;
        this.multicastAddress = multicastAddress;
        this.port = port;
    }

    public boolean enterRoom() {
        try {
            group = InetAddress.getByName(multicastAddress);
            socket = new MulticastSocket(port);
            socket.joinGroup(group);
            sendMessage("Usuário " + userName + " entrou na sala");
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao entrar na sala: " + e.getMessage());
            return false;
        }
    }

    public boolean leaveRoom() {
        try {
            sendMessage("Usuário " + userName + " saiu da sala");
            socket.leaveGroup(group);
            socket.close();
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao sair da sala: " + e.getMessage());
            return false;
        }
    }

    public void sendMessage(String message) {
        try {
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
            socket.send(packet);
        } catch (IOException e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
        }
    }

    public void receiveMessages() {
        Thread receiveThread = new Thread(() -> {
            byte[] buffer = new byte[1024];
            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    System.out.println(message);
                } catch (IOException e) {
                    System.err.println("Erro ao receber mensagem: " + e.getMessage());
                    break;
                }
            }
        });
        receiveThread.start();
    }