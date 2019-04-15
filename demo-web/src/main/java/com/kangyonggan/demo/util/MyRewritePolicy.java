package com.kangyonggan.demo.util;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.util.Booleans;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.SimpleMessage;

/**
 * @author kangyonggan
 * @since 2019/4/14 0014
 */
@Plugin(name = "MyRewritePolicy", category = "Core", elementType = "rewritePolicy", printObject = true)
public class MyRewritePolicy implements RewritePolicy {

    private boolean debug;

    public MyRewritePolicy(boolean debug) {
        this.debug = debug;
    }

    @Override
    public LogEvent rewrite(LogEvent event) {
        if (debug) {
            return event;
        }

        Message msg = event.getMessage();
        if (msg == null) {
            return event;
        }
        String message = msg.getFormattedMessage();

        // 处理日志的逻辑
        if (null != message) {
            message = processData(message);
        }
        SimpleMessage simpleMessage = new SimpleMessage(message);
        return new Log4jLogEvent.Builder(event).setMessage(simpleMessage).build();
    }

    /**
     * @param str
     * @return
     */
    private String processData(String str) {

        return str;
    }

    @PluginFactory
    public static MyRewritePolicy factory(@PluginAttribute("debug") String debug) {
        return new MyRewritePolicy(Booleans.parseBoolean(debug, true));
    }

}
