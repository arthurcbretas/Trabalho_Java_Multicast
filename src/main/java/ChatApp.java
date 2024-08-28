import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.Scanner;

public class ChatApp {
    //Olá! Para usar o programa você terá que ir no terminal e colocar o caminho até a pasta java
    //Depois digite javac ChatApp.java e você verá um "clone" do ChatApp
    //logo em seguida digite java ChatApp "Digite um usuário" e o endereço multicast, sugiro o 230.0.0.1
    private String username;
    private InetAddress group;
    private SocketAddress groupSocketAddress;
    private MulticastSocket socket;
    private int port = 6789;
    private boolean running;
    private NetworkInterface networkInterface;

    public ChatApp(String username, String groupAddress) {
        this.username = username;
        try {
            this.group = InetAddress.getByName(groupAddress);
            this.groupSocketAddress = new InetSocketAddress(group, port);
            this.networkInterface = findFirstNonLoopbackInterface();
        } catch (IOException e) {
            System.err.println("Erro ao resolver o endereço multicast ou ao identificar a interface de rede: " + e.getMessage());
        }
    }

    private NetworkInterface findFirstNonLoopbackInterface() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface iface = interfaces.nextElement();
            if (!iface.isLoopback() && iface.isUp()) {
                return iface;
            }
        }
        return null;
    }

    public boolean enterRoom() {
        try {
            socket = new MulticastSocket(port);
            socket.joinGroup(groupSocketAddress, networkInterface); // Usando a versão mais recente do método com SocketAddress
            running = true;
            sendMessage("Usuario " + username + " entrou na sala");
            new Thread(this::receiveMessages).start();
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao entrar na sala: " + e.getMessage());
            return false;
        }
    }

    public boolean leaveRoom() {
        try {
            sendMessage("Usuario " + username + " saiu da sala");
            running = false;
            socket.leaveGroup(groupSocketAddress, networkInterface); // Usando a versão mais recente do método com SocketAddress
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
        try {
            while (running) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println(message);
            }
        } catch (IOException e) {
            if (running) {
                System.err.println("Erro ao receber mensagem: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) { // Aqui está o método main
        if (args.length != 2) {
            System.out.println("Uso: java ChatApp <Nome de Usuario> <Endereço Multicast>");
            return;
        }

        String username = args[0];
        String groupAddress = args[1];

        ChatApp chatApp = new ChatApp(username, groupAddress);

        if (chatApp.enterRoom()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Voce entrou na sala " + groupAddress + ". Digite suas mensagens abaixo:" + " para sair digite /sair");

            while (true) {
                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("/sair")) {
                    chatApp.leaveRoom();
                    break;
                }
                chatApp.sendMessage(username + ": " + message);
            }

            scanner.close();
        }
    }
}
