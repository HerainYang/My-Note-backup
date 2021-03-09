import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class server {
    private JFrame mainWindow;
    private JPanel chatBox;
    private JPanel sendBox;
    private JSplitPane sendChatBox;
    private JButton sendButton;
    private JPanel buttonPanel;
    private FlowLayout buttonLayout;
    private JScrollPane sendTextArea;
    private JTextArea sendText;
    private JScrollPane chatTextArea;
    private JTextArea chatText;
    private OutputStream os;
    private PrintWriter pw;
    ArrayList<Socket> clients = new ArrayList<>();
    private void serverConnect(){
        try {
            ServerSocket newServer = new ServerSocket(6066);
            createWindow("localhost:6066");
            chatText.append("Server created at "+getTime()+"[localhost:6066]\n");
            while(true){
                Socket client = newServer.accept();
                chatText.append("客户端[" + client.getRemoteSocketAddress() + "]连接成功，当前在线用户" + clients.size() + "个\n");
                clients.add(client);
                new MessageListener(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void sendAllMsg(String Message) throws IOException {
        chatText.append(getTime()+"[To All]:"+Message+"\n");
        String Msg = getTime()+"[To All]:"+Message+"\n";
        Msg += "[this is the end of buffer]";
        for(Socket socket : clients){
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.println(Msg);
            pw.flush();
        }
    }

    class MessageListener extends Thread{
        private Socket listenClient;
        private OutputStream os;
        private PrintWriter pw;
        private InputStream is;
        private InputStreamReader isr;
        private BufferedReader br;
        public MessageListener(Socket listenClient){
            this.listenClient = listenClient;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    sendMsg(receiveMsg());
                    System.out.println("send successfully");
                } catch (IOException e) {
                    System.out.println("Client logout");
                    clients.remove(listenClient);
                    return;
                }
            }
        }

        private void sendMsg(String Message) throws IOException {
            chatText.append(Message+"\n");
            Message += "[this is the end of buffer]";
            for(Socket socket : clients){
                os = socket.getOutputStream();
                pw = new PrintWriter(os);
                pw.println(Message);
                pw.flush();
            }
        }
        private String receiveMsg() throws IOException{
            System.out.println("receive something");
            is = listenClient.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            chatText.append("");
            String message = "";
            String newLine;
            System.out.println(br.read());
            if(br.read() == -1){
                throw new IOException();
            }
            while((newLine = br.readLine()) != null){
                if(newLine.equals("[this is the end of buffer]"))
                    break;
                System.out.println(newLine);
                message += newLine;
                message += '\n';
            }
            System.out.println(message);
            System.out.println("exit");
            return message;
        }
    }

    public static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
        Date date = new Date();
        return sdf.format(date);
    }

    private void createWindow(String serverAddressPort){
        mainWindow = new JFrame("chatting server"+"["+serverAddressPort+"]");
        mainWindow.setSize(1000, 1000);
        //mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        mainWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        sendChatBox = new JSplitPane();
        sendChatBox.setDividerLocation(300);
        sendChatBox.setOrientation(JSplitPane.VERTICAL_SPLIT);
        mainWindow.add(sendChatBox, BorderLayout.CENTER);

        chatBox = new JPanel();
        chatBox.setBackground(Color.CYAN);
        chatBox.setLayout(new BorderLayout());
        sendChatBox.setLeftComponent(chatBox);

        sendBox = new JPanel();
        sendBox.setBackground(Color.BLUE);
        sendBox.setLayout(new BorderLayout());
        sendChatBox.setRightComponent(sendBox);

        sendTextArea = new JScrollPane();
        sendBox.add(sendTextArea, BorderLayout.CENTER);

        sendText = new JTextArea();
        sendTextArea.setViewportView(sendText);

        buttonLayout = new FlowLayout();
        buttonLayout.setAlignment(FlowLayout.RIGHT);

        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.gray);
        buttonPanel.setLayout(buttonLayout);
        sendBox.add(buttonPanel, BorderLayout.SOUTH);

        sendButton = new JButton();
        sendButton.setText("Send");
        buttonPanel.add(sendButton);

        chatTextArea = new JScrollPane();
        chatBox.add(chatTextArea, BorderLayout.CENTER);

        chatText = new JTextArea();
        chatText.setEditable(false);
        chatTextArea.setViewportView(chatText);
        functionBounding();
    }

    private void functionBounding(){
        sendButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = sendText.getText();
                sendText.setText("");
                try {
                    sendAllMsg(input);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        server chatServer = new server();
        chatServer.serverConnect();
    }
}
