
package com.zhongtai.tools;

import com.typesafe.config.ConfigRenderOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static ch.qos.logback.classic.util.ContextInitializer.CONFIG_FILE_PROPERTY;

public interface Logs {
    boolean logInit = init();

    static boolean init() {
        if (logInit) return true;
        System.setProperty("log.home", Conf.mongodb.log_dir);
        System.setProperty("log.root.level", Conf.mongodb.log_level);
        System.setProperty(CONFIG_FILE_PROPERTY, Conf.mongodb.log_conf_path);
        LoggerFactory
                .getLogger("console")
                .info(Conf.mongodb.cfg.root().render(ConfigRenderOptions.concise().setFormatted(true)));
        return true;
    }

    Logger Console = LoggerFactory.getLogger("console");

}
