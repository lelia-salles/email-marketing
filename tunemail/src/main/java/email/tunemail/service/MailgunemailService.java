package email.tunemail.service;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(name = "mailgun-client", url = "${mailgun.api-url}")
public interface MailgunemailService {

    @PostMapping
    @Headers("Authorization: Basic {apiKey}")
    void sendEmail(@RequestHeader("Authorization") String apiKey, @RequestParam("from") String from,
                   @RequestParam("to") String to, @RequestParam("subject") String subject,
                   @RequestParam("text") String text);
}

