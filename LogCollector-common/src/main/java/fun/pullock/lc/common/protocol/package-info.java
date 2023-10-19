package fun.pullock.lc.common.protocol;
/*
* 协议设计
* 协议包含：消息头和消息体
* 消息头：
*           1byte标识是请求还是响应：0请求 1响应
*           1byte标识类型：1连接 2心跳 3日志 4JVM数据
*           4byte数据长度
* 消息体：
*           JSON数据
* */