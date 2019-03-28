package csye6225.cloud.noteapp.controller;

import com.amazonaws.Response;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.Topic;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import csye6225.cloud.noteapp.exception.AppException;
import csye6225.cloud.noteapp.model.User;
import csye6225.cloud.noteapp.repository.UserRepository;
import csye6225.cloud.noteapp.service.AmazonClient;
import csye6225.cloud.noteapp.service.MetricsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;

import javax.validation.Valid;
import java.nio.ByteBuffer;
import java.util.List;

@RestController
public class ResetpwController {

    private static final Logger logger = LoggerFactory.getLogger(ResetpwController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    public MetricsConfig metricsConfig;

    @PostMapping(value = "/reset")
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody String email) throws AppException {

        metricsConfig.statsDClient().incrementCounter("ResetPassword_API");
        logger.info("ResetPassword called");
        JsonObject jsonObject = new JsonObject();
        User up = userRepository.findUserByEmail(email);
        if(up != null)
        {
            AmazonSNS snsClient = AmazonSNSAsyncClientBuilder.standard()
                    .withCredentials(new InstanceProfileCredentialsProvider(false))
                    .build();
            List<Topic> topics = snsClient.listTopics().getTopics();

            for(Topic topic: topics)
            {
                if(topic.getTopicArn().endsWith("password_reset")){
                    logger.info(email);
                    PublishRequest req = new PublishRequest(topic.getTopicArn(),email);
                    snsClient.publish(req);
                    break;
                }
            }
            jsonObject.addProperty("message","Successful");
        }
        else{
            jsonObject.addProperty("message","User not found");
        }

        jsonObject.addProperty("Success","Password reset instruction sent.");
        return ResponseEntity.status(201).body(jsonObject.toString());
    }
}
