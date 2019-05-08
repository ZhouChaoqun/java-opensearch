package com.quancheng.manto.message;

import com.quancheng.manto.common.Const;
import com.quancheng.manto.message.handler.OnlineResMessageHandler;
import com.quancheng.manto.message.handler.SpecialResMessageHandler;
import com.quancheng.manto.message.handler.UnsignedResMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zz 2018/12/27
 */
@Component
public class MessageHandlerFactory {

    @Autowired
    private UnsignedResMessageHandler unsignedResMessageHandler;

    @Autowired
    private OnlineResMessageHandler onlineResMessageHandler;

    @Autowired
    private SpecialResMessageHandler specialResMessageHandler;

    public MessageHandler getHandler(String tableName) {

        MessageHandler messageHandler;

        switch (tableName) {

            case Const.UNSIGNED_RESTAURANT_TABLE_NAME:
            case Const.UNSIGNED_BLACKLIST_TABLE_NAME:
                messageHandler = unsignedResMessageHandler;
                break;
            case Const.RESTAURANT_INFO_TABLE_NAME:
                messageHandler = specialResMessageHandler;
                break;
            default:
                messageHandler = onlineResMessageHandler;
        }

        return messageHandler;
    }
}
