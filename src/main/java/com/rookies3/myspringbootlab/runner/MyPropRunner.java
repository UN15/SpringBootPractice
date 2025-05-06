package com.rookies3.myspringbootlab.runner;

import com.rookies3.myspringbootlab.property.MyPropProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyPropRunner implements ApplicationRunner {
    @Value("${myprop.username}")
    private String userName;

    @Value("${myprop.port}")
    private int port;

    @Autowired
    private MyPropProperties myPropProperties;

    private Logger logger = LoggerFactory.getLogger(MyPropRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Logger 구현체 => " + logger.getClass().getName());

        System.out.println(userName);
        System.out.println(port);
        logger.debug("${myprop.username} = {}", userName);
        logger.debug("${myprop.port} = {}", port);

        logger.info("MyPropProperties getUserName() = {}", myPropProperties.getUserName());
        logger.info("MyPropProperties getPort() = {}", myPropProperties.getPort());



    }
}
