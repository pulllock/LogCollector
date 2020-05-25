package me.cxis.lc.agent;

import com.alibaba.fastjson.JSON;
import me.cxis.lc.common.enums.MessageType;
import me.cxis.lc.common.protocol.Message;
import me.cxis.lc.common.protocol.model.JVMRuntimeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static me.cxis.lc.common.constants.Constants.LOCAL_AGENT_PORT;

public class MessageProcessor {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageProcessor.class);

    private final static MessageProcessor messageProcessor;

    static {
        messageProcessor = new MessageProcessor();
    }

    private MessageProcessor() {}

    public static MessageProcessor newMessageProcessor() {
        return messageProcessor;
    }

    public void start() {
        MessageProcessThread thread = new MessageProcessThread();
        thread.start();
    }

    private class MessageProcessThread extends Thread {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(LOCAL_AGENT_PORT);
                serverSocket.setSoTimeout(0);
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("socket: " + socket);
                    handleSocket(socket);
                }
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error("Message processor error, cause: ", e);
            }
        }
    }

    private void handleSocket(Socket socket) {

        try {
            final DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        int reqType = dataInputStream.readByte();
                        int msgType = dataInputStream.readByte();
                        int length = dataInputStream.readInt();
                        byte[] data = new byte[length];
                        dataInputStream.readFully(data);
                        LOGGER.info("reqType: {}, msgType: {}, length: {}, data: {}", reqType, msgType, length, new String(data));
                        processMessage(new Message(msgType, reqType, data));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("handler socket error, cause: ", e);
        }
    }

    private void processMessage(Message message) {
        MessageType messageType = MessageType.of(message.getType());
        if (messageType == null) {
            LOGGER.warn("Unknown message type: {}", message.getType());
            return;
        }

        switch (messageType) {
            case CONNECT: {
                ;
                break;
            }
            case JVM: {
                String data = new String(message.getData());
                JVMRuntimeInfo runtimeInfo = JSON.parseObject(data, JVMRuntimeInfo.class);
                System.out.println("receive jvm runtime data from client: " + runtimeInfo);
                break;
            }
            case LOG: {
                ;
                break;
            }
            case HEART_BEAT: {
                ;
                break;
            }
            default: {
                LOGGER.warn("Unknown message type: {}", message.getType());
                break;
            }
        }
    }
}
