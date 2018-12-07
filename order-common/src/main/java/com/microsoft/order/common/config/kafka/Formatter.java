package com.microsoft.order.common.config.kafka;

import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * @author: 陈偲
 * @date: 2018/5/10 17:57
 * @version: 1.0
 */
public interface Formatter {
    String format(ILoggingEvent event);
}
