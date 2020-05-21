package me.cxis.lc.agent;

public class LocalAgent {

    public static void main(String[] args) {
        // 启动接收client消息的线程
        MessageProcessor.newMessageProcessor().start();

        // 启动收集日志线程

    }
}
