package csye6225.cloud.noteapp.controller;

import com.amazonaws.Response;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import csye6225.cloud.noteapp.exception.AppException;
import csye6225.cloud.noteapp.model.User;
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

@RestController
public class ResetpwController {


    @PostMapping(value = "/reset")
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody Object jsonobj) throws AppException {

        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName("GetWeatherDataFunction")
                .withPayload(jsonobj.toString());

        BasicAWSCredentials awsCreds = new BasicAWSCredentials("APIADGALD", "PQFNsMOxyrb");

        AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();

        InvokeResult invokeResult = null;

        try {
            invokeResult = awsLambda.invoke(invokeRequest);
        }
        catch (Exception e) {

        }

        System.out.println(invokeResult.getStatusCode());

        ByteBuffer byteBuffer = invokeResult.getPayload();

        String rawJson = null;

        try {
            rawJson = new String(byteBuffer.array(), "UTF-8");
        }catch (Exception e) {

        }

        System.out.println(rawJson);

        ObjectMapper mapper = new ObjectMapper();

        try {
            Response response = mapper.readValue(rawJson, Response.class);
            //System.out.println(response.getWeather().get(0).getMain() + "\t" + response.getWeather().get(0).getDescription());

        } catch(Exception e) {

        }

        JsonObject entity = new JsonObject();
        entity.addProperty("Success","Password reset instruction sent.");
        return ResponseEntity.status(201).body(entity.toString());
    }
}
