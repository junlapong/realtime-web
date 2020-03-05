package sse.controller;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.validation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import sse.model.NotificationMessage;

@RestController
public class NotificationController {

    final static Logger log = LoggerFactory.getLogger(NotificationController.class);

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();
 
    @RequestMapping(path = "/message-stream", method = RequestMethod.GET)
    public SseEmitter stream() throws IOException {
        final SseEmitter emitter = new SseEmitter(120000L);
        try {
            emitters.add(emitter);
            emitter.onCompletion(() -> this.emitters.remove(emitter));
            emitter.onTimeout(() -> this.emitters.remove(emitter));
        } catch (final Exception e) {
            //e.printStackTrace();
            log.error("ERROR: {}", e.getMessage());
        }

        log.info("/stream registered.");

        return emitter;
    }
 
    @RequestMapping(path = "/message-update", method = RequestMethod.POST)
    public NotificationMessage sendMessage(@Valid @RequestBody final NotificationMessage message) {

        log.info("{}", message);

        emitters.forEach((SseEmitter emitter) -> {
            try {
                emitter.send(message, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(emitter);
                //e.printStackTrace();
                log.error("ERROR: {}", e.getMessage());
            }
        });
        return message;
    }
}