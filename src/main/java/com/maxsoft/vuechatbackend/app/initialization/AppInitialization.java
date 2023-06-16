package com.maxsoft.vuechatbackend.app.initialization;

import com.maxsoft.vuechatbackend.app.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppInitialization {

    private final InitService initService;

    @EventListener(ContextRefreshedEvent.class)
    public void postContextInitialization(ContextRefreshedEvent event) throws NoSuchAlgorithmException {
        log.info("Initialization method has started");
        ApplicationContext applicationContext = event.getApplicationContext();
        AppProperties appProperties = applicationContext.getBean(AppProperties.class);

        if (appProperties.getIsRecreateServerUserNeeded()) {
            initService.recreateServerUser();
        }
    }
}
