package xyz.cloudease.todo.scheduling;

import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.codec.binary.Base64;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Log4j2
@Component
public class PhoneCallJob implements Job {
    private static final String TWILLIO_CALL_URL = "https://api.twilio.com/2010-04-01/Accounts/%s/Calls.json";

    @Value("${twillio.account.sid}")
    private String twillioAccountSid;

    @Value("${twillio.account.authtoken}")
    private String twillioAccountAuthToken;

    @Value("${twillio.phone.to}")
    private String twillioPhoneTo;

    @Value("${twillio.phone.from}")
    private String twillioPhoneFrom;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Phone call job triggered");
        String message = (String)jobExecutionContext.getMergedJobDataMap().get("message");
        log.info("message: " + message);
        triggerPhoneCall(message);
    }

    private void triggerPhoneCall(String message) {
        String twiMlCommand = "<Response>" +
                              "<Say voice=\"alice\">This is a todo reminder to " + message + "</Say>" +
                              "</Response>";
        String twiMlUrl = "http://twimlets.com/echo?Twiml=" +
                URLEncoder.encode(twiMlCommand, StandardCharsets.UTF_8);

        RestTemplate restTemplate = new RestTemplate();

        // set url-encoded request params
        restTemplate.getMessageConverters().add( new FormHttpMessageConverter() );
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add( "Url", twiMlUrl);
        requestBody.add( "To", twillioPhoneTo);
        requestBody.add( "From", twillioPhoneFrom);

        // create request with auth header
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody,
                createAuthHeader(twillioAccountSid, twillioAccountAuthToken));

        ResponseEntity<Object> response = null;
        try {
            response = restTemplate.postForEntity(
                    String.format(TWILLIO_CALL_URL, twillioAccountSid),
                    request, Object.class );
        } catch (RestClientException e) {
            log.error("Failed to trigger twillio calls api: ", e);
            return;
        }
        if(!response.getStatusCode().is2xxSuccessful()) {
            log.error("Twillio api return unsuccessful status code: ",
                    response.getStatusCode());
        }
    }

    private HttpHeaders createAuthHeader(String username, String password){
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")) );
        String authHeader = "Basic " + new String(encodedAuth);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authHeader);
        return headers;
    }
}
