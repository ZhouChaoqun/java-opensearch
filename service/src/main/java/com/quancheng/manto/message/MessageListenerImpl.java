package com.quancheng.manto.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.quancheng.ons.boot.OnsListener;
import com.quancheng.starter.log.FlushLogId;
import com.quancheng.starter.log.LogUtil;
import com.quancheng.starter.log.QcLog;
import com.quancheng.starter.log.QcLoggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author zz 2018/12/11
 */

@OnsListener(consumerId = "#{quancheng.ons.consumerId}", topic = "#{quancheng.ons.topic}")
public class MessageListenerImpl implements MessageListener {

    private static final QcLog LOGGER = LogUtil.getLogger(MessageListenerImpl.class);

    @Value("${mq.filter.tableNames}")
    private String tableNames;

    @Autowired
    private MessageHandlerFactory handlerFactory;

    @Override
    @QcLoggable(QcLoggable.Type.NONE)
    @FlushLogId
    public Action consume(Message message, ConsumeContext consumeContext) {

        if (message == null) {
            return Action.CommitMessage;
        }

        byte[] messageBody = message.getBody();
        JSONObject data;
        String opt;

        try {

            JSONObject messageInfo = JSON.parseObject(new String(messageBody));

            String tableName = messageInfo.getString("tableName");
            if (!tableNames.contains(tableName)) {
                return Action.CommitMessage;
            }

            data = messageInfo.getJSONObject("data");
            opt = messageInfo.getString("opt");

            LOGGER.warn("搜索服务监听消息 =====>>>{}", messageInfo.toJSONString());

            MessageHandler handler = handlerFactory.getHandler(tableName);
            return handler.process(opt, data, tableName);

        } catch (Exception e) {
            LOGGER.error("搜索服务处理消息异常 ", e);
            return Action.CommitMessage;
        }

    }

}
