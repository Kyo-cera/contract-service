
package com.os.services.contract;

import feign.jackson.JacksonEncoder;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJms
@EnableFeignClients(defaultConfiguration = JacksonEncoder.class)
@EnableScheduling
public class ContractServiceApplication implements ApplicationContextAware {
    public static void main(String[] args) throws IOException, ParseException {
        SpringApplication.run(ContractServiceApplication.class, args);
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        // https://github.com/springfox/springfox/issues/1074
        // // force the bean to get loaded as soon as possible
        ac.getBean("requestMappingHandlerAdapter");
    }

}
