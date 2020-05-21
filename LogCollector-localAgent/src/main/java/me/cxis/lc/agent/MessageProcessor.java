package me.cxis.lc.agent;

import me.cxis.lc.agent.enums.MessageType;
import me.cxis.lc.agent.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static me.cxis.lc.agent.constants.Constants.LOCAL_AGENT_PORT;
import static me.cxis.lc.agent.enums.MessageType.CONNECT;

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
                while (true) {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> handleSocket(socket)).start();
                }
            } catch (IOException e) {
                LOGGER.error("Message processor error, cause: ", e);
            } finally {
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        LOGGER.error("Message processor error, cause: ", e);
                    }
                }
            }
        }
    }

    private void handleSocket(Socket socket) {
        DataInputStream dataInputStream = null;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            int reqType = dataInputStream.readByte();
            int msgType = dataInputStream.readByte();
            int length = dataInputStream.readInt();
            byte[] data = new byte[length];
            dataInputStream.readFully(data);
            LOGGER.info("reqType: {}, msgType: {}, length: {}, data: {}", reqType, msgType, length, new String(data));
            processMessage(new Message(msgType, reqType, data));
        } catch (Exception e) {
            LOGGER.error("handler socket error, cause: ", e);
        } finally {
            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    LOGGER.error("handler socket error, cause: ", e);
                }
            }
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
                ;
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
