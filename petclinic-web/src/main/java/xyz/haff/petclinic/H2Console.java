package xyz.haff.petclinic;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
@Slf4j
@Profile("h2")
public class H2Console {

    private Server webServer;

    @Value("${xyz.haff.petclinic.h2-console-port}")
    Integer h2ConsolePort;

    @EventListener(ContextRefreshedEvent.class)
    public void start() throws SQLException {
        log.info("starting h2 console at port " + h2ConsolePort);
        this.webServer = Server.createWebServer("-webPort", h2ConsolePort.toString(), "-tcpAllowOthers").start();
    }

    @EventListener(ContextClosedEvent.class)
    public void stop() {
        log.info("stopping h2 console at port " + h2ConsolePort);
        this.webServer.stop();
    }

}
