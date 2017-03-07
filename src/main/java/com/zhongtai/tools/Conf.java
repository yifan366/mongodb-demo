package com.zhongtai.tools;

import static java.util.stream.Collectors.toCollection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.impl.ConfigBeanImpl;
import com.zhongtai.commontypes.DBConnection;
import com.zhongtai.redis.RedisNode;

/**
 * Created by rain on 2016/12/13.
 */
public interface Conf {
    Config cfg = load();

    static Config load(){
        Config config = ConfigFactory.load();//扫描加载所有可用的配置文件
        String custom_conf = "reference.conf";
        if (config.hasPath(custom_conf)) {
            File file = new File(config.getString(custom_conf));
            if (file.exists()) {
                Config custom = ConfigFactory.parseFile(file);
                config = custom.withFallback(config);
            }
        }
        return config;
    }
    interface mongodb {
        //日志配置
        Config cfg = Conf.cfg.getObject("mongodb").toConfig();
        String log_dir = cfg.getString("log-dir");
        String log_level = cfg.getString("log-level");
        String log_conf_path = cfg.getString("log-conf-path");
        //nosql配置
        interface options {
            Config cfg = mongodb.cfg.getObject("options").toConfig();
            boolean autoConnectRetry = cfg.getBoolean("autoConnectRetry");
            boolean socketKeepAlive = cfg.getBoolean("socketKeepAlive");
            int socketTimeout = (int) cfg.getMemorySize("socketTimeout").toBytes();
            int connectionsPerHost = (int) cfg.getMemorySize("connectionsPerHost").toBytes();
            int maxWaitTime = (int) cfg.getMemorySize("maxWaitTime").toBytes();
            int connectTimeout = (int) cfg.getMemorySize("connectTimeout").toBytes();
            int ThreadsPerConnection = (int) cfg.getMemorySize("ThreadsPerConnection").toBytes();
            List<DBConnection> nodes = cfg.getList("nodes").stream().map(v -> DBConnection.from(v.unwrapped().toString())).collect(toCollection(ArrayList::new));
        }
        interface collectioninfo{
            Config cfg = mongodb.cfg.getObject("collectioninfo").toConfig();
            String dbName = cfg.getString("dbName");
            //String collectionName = cfg.getString("collectionName");

        }
        interface redis {
            Config cfg = mongodb.cfg.getObject("redis").toConfig();

            String password = cfg.getString("password");
            String clusterModel = cfg.getString("cluster-model");

            List<RedisNode> nodes = cfg.getList("nodes")
                    .stream()//第一纬度数组
                    .map(v -> RedisNode.from(v.unwrapped().toString()))
                    .collect(toCollection(ArrayList::new));
            static boolean isCluster() {
                return "cluster".equals(clusterModel);
            }

            static <T> T getPoolConfig(Class<T> clazz) {
                return ConfigBeanImpl.createInternal(cfg.getObject("config").toConfig(), clazz);
            }
        }
        interface httpserver {
        	Config cfg = mongodb.cfg.getObject("httpserver").toConfig();
        	String url = cfg.getString("url");
        	
        }
        
    }

}
