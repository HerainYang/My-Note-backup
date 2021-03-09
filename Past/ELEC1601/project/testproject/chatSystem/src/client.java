import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class client {
    private Socket server;

    private JPanel settingBox;
    private FlowLayout settingBoxLayout;
    private JTextArea serverInput;
    private JLabel serverHint;
    private JButton serverConnectButton;
    private JLabel changeNicknameHint;
    private JTextArea nickname;
    private JButton changeNicknameButton;
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

    private String currentNickname;

    private OutputStream os;
    private PrintWriter pw;
    private InputStream is;
    private InputStreamReader isr;
    private BufferedReader br;

    public void clientConnect() {
        try {
            createWindow("chatSystem client");
            server = new Socket("localhost", 6066);
            currentNickname = server.getLocalSocketAddress().toString();
            chatText.append("Connect successful, your current nickname is " + currentNickname + "\n");
            new ReceiveMessageListener().start();
        } catch (IOException e) {
            chatText.append("Server is maintaining, please connect later\n");
        }

    }

    class ReceiveMessageListener extends Thread{
        @Override
        public void run() {
            while(true){
                try {
                    chatText.append(receiveMsg());
                } catch (IOException e) {
                    System.out.println("Server disconnected");
                    return;
                }
            }
        }
    }

    private String receiveMsg() throws IOException{
        System.out.println("receiving message");
        is = server.getInputStream();
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);
        String message = "";
        String newLine;
        System.out.println(br.read());
        if(br.read() == -1){
            throw new IOException();
        }
        while((newLine = br.readLine()) != null){
            if(newLine.equals("[this is the end of buffer]"))
                break;
            message += newLine;
            message += '\n';
        }
        System.out.println("exit");
        return message;
    }

    private void sendMsg(String msg) throws IOException {
        os = server.getOutputStream();
        pw = new PrintWriter(os);
        msg += "[this is the end of buffer]";
        pw.println(msg);
        pw.flush();
        System.out.println("send one time");
    }

    public String getCurrentNickname(){
        return currentNickname;
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

        settingBoxLayout = new FlowLayout();
        settingBoxLayout.setAlignment(FlowLayout.LEFT);

        settingBox = new JPanel();
        settingBox.setBackground(Color.orange);
        settingBox.setLayout(settingBoxLayout);
        settingBox.setPreferredSize(new Dimension(mainWindow.getWidth(), 50));
        mainWindow.add(settingBox, BorderLayout.NORTH);

        serverHint = new JLabel();
        serverHint.setText("Server Address");
        settingBox.add(serverHint);

        serverInput = new JTextArea();
        serverInput.setPreferredSize(new Dimension(150, 20));
        settingBox.add(serverInput);

        serverConnectButton = new JButton();
        serverConnectButton.setText("Connect");
        settingBox.add(serverConnectButton);

        changeNicknameHint = new JLabel();
        changeNicknameHint.setText("Set Nickname");
        settingBox.add(changeNicknameHint);

        nickname = new JTextArea();
        nickname.setPreferredSize(new Dimension(150, 20));
        settingBox.add(nickname);

        changeNicknameButton = new JButton();
        changeNicknameButton.setText("Change");
        settingBox.add(changeNicknameButton);

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
                if(currentNickname == null){
                    chatText.append("Server is unavailable\n");
                    return;
                }
                String message = getTime()+" [From "+currentNickname+"]:\n" + input + "\n";
                //chatText.append(message);
                try {
                    sendMsg(message);
                } catch (IOException ioException) {
                    System.out.println("error");
                }
                System.out.println(input);
            }
        });

        changeNicknameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pastName = currentNickname;
                currentNickname = nickname.getText();
                mainWindow.setTitle("chatting server"+"["+currentNickname+"]");
                try {
                    sendMsg("["+pastName + " change name to " + currentNickname + "]\n");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        client thisClient = new client();
        thisClient.clientConnect();
    }
}
