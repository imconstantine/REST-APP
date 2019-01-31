package com.imconstantine.netexam;

import com.imconstantine.netexam.database.utils.MyBatisUtils;
import com.imconstantine.netexam.utils.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NetExamServer {

    /**
     * todo
     * CONFIG FILE PATH FOR DEVELOPMENT STAGE
     * MUST BE REMOVE BEFORE PRODUCTION
     **/
    private static final String CONFIG_FILE = "myconfig.ini";

    private static final Logger LOGGER = LoggerFactory.getLogger(NetExamServer.class);

    public static void main(String[] args) {
        /**todo
         * MUST BE REMOVE BEFORE PRODUCTION
         **/
        args = new String[]{CONFIG_FILE};

        if (args[0] == null) {
            LOGGER.info("Can't start server. Please specify the path for config file");
            System.exit(0);
        }
        if (Setting.loadConfigFile(args[0])) {
            if (MyBatisUtils.initSqlSessionFactory()) {
                SpringApplication.run(NetExamServer.class, args);
            }
        }

    }

}
