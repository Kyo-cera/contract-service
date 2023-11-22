package com.os.services.contract.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.os.services.contract.config.ContractServiceConfig;
import com.os.services.contract.model.*;
import com.os.services.contract.model.Process;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ApiClient {

    @Autowired
    private ContractServiceConfig config;

    private static final Logger log = LoggerFactory.getLogger(ApiClient.class);

    OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    // Get object on yuuvis RAD by filtering
    public List<DmsObject> getJsonQueryResult(FilterByIndexData filter, String type) throws Exception {

        String jsonString = new Gson().toJson(filter);
        List<DmsObject> dmsObjectList;

        // For bug investigation
        log.info("getJsonQueryResult: request params: request jsonString = {}, type = {}", jsonString, type);
        log.info("getJsonQueryResult: request params: server ip = {}, root credential = {}", config.getServerIP(),
                this.config.getRootCredential());

        try {
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, jsonString);
            Request request = new Request.Builder()
                    .url("http://" + config.getServerIP() + "/rest-ws/service/result/query?type=" + type)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization",
                            "Basic " + Base64.getEncoder().encodeToString(this.config.getRootCredential().getBytes()))
                    .build();
            Response response = client.newCall(request).execute();
            log.info("prova: ---> ", jsonString, request, response, " <---");

            if (response.code() != HttpStatus.OK.value()) {
                String errorMessage = String.format(
                        "getJsonQueryResult: yuuvis RAD returned response: code = %d, message = %s. Please contact administrator",
                        response.code(), response.message());
                throw new Exception(errorMessage);

            } else {
                log.info("getJsonQueryResult: yuuvis RAD returned response: code = {}, message = {}", response.code(),
                        response.message());
                Type dmsObject = new TypeToken<List<DmsObject>>() {
                }.getType();
                dmsObjectList = (List<DmsObject>) new Gson().fromJson(response.body().string(), dmsObject);
            }

        } catch (Exception e) {
            log.error("getJsonQueryResult: Error message = {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
        return dmsObjectList;
    }

    // Start process with model id and index data
    public Process startProcessWithModelId(Data data, String contentId, String contentType, String modelId)
            throws Exception {

        Process processResult;

        try {
            Content content = new Content();
            content.setId(contentId);
            content.setType(contentType);
            List<Content> contents = new ArrayList<>();
            contents.add(0, content);
            RequestBodyStartProcess requestBodyStartProcess = new RequestBodyStartProcess();
            requestBodyStartProcess.setData(data);
            requestBodyStartProcess.setContents(contents);

            Gson gson = new Gson();
            String jsonBody = new Gson().toJson(requestBodyStartProcess);

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, jsonBody);
            Request request = new Request.Builder()
                    .url("http://" + config.getServerIP() + "/rest-ws/service/bpm/process/?modelid=" + modelId)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization",
                            "Basic " + Base64.getEncoder().encodeToString(this.config.getRootCredential().getBytes()))
                    .build();
            Response response = client.newCall(request).execute();

            if (response.code() != HttpStatus.CREATED.value()) {
                String errorMessage = String.format(
                        "startProcessWithModelId: yuuvis RAD returned response: code = %d, message = %s. Please contact administrator",
                        response.code(), response.message());
                throw new Exception(errorMessage);

            } else {
                log.info("startProcessWithModelId: yuuvis RAD returned response: code = {}, message = {}",
                        response.code(), response.message());
                processResult = gson.fromJson(response.body().string(), Process.class);
            }
        } catch (Exception e) {
            log.error("startProcessWithModelId: Error message = {}", e.getMessage());
            throw new Exception(e.getMessage());
        }

        return processResult;
    }

    //
    public List<ProcessModel> getModelIdList() throws Exception {

        List<ProcessModel> modelIdList;

        try {
            Request request = new Request.Builder()
                    .url("http://" + config.getServerIP() + "/rest-ws/service/bpm/management/model")
                    .method("GET", null)
                    .addHeader("Authorization",
                            "Basic " + Base64.getEncoder().encodeToString(this.config.getRootCredential().getBytes()))
                    .build();
            Response response = client.newCall(request).execute();

            if (response.code() != HttpStatus.OK.value()) {
                String errorMessage = String.format(
                        "getModelIdList: yuuvis RAD returned response: code = %d, message = %s. Please contact administrator",
                        response.code(), response.message());
                throw new Exception(errorMessage);

            } else {
                log.info("getModelIdList: yuuvis RAD returned response: code = {}, message = {}", response.code(),
                        response.message());
                Type modelIds = new TypeToken<List<ProcessModel>>() {
                }.getType();
                modelIdList = (List<ProcessModel>) new Gson().fromJson(response.body().string(), modelIds);
            }
        } catch (Exception e) {
            log.info("getModelIdList: Error message = {}", e.getMessage());
            throw new Exception(e.getMessage());
        }

        return modelIdList;
    }

    //
    public void updateItem(String itemId, String contractStatus, String processingStatus) throws Exception {

        try {
            MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");
            String jsonBody = "{\r\n    \"contractStatus\": \"" + contractStatus + "\",\r\n    \"processingStatus\": \""
                    + processingStatus + "\"\r\n}";
            RequestBody body = RequestBody.create(mediaType, jsonBody);
            Request request = new Request.Builder()
                    .url("http://" + config.getServerIP() + "/rest-ws/service/dms/" + itemId)
                    .method("PUT", body)
                    .addHeader("Content-Type", "application/json; charset=UTF-8")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization",
                            "Basic " + Base64.getEncoder().encodeToString(this.config.getRootCredential().getBytes()))
                    .build();
            Response response = client.newCall(request).execute();

            if (response.code() != HttpStatus.NO_CONTENT.value()) {
                String errorMessage = String.format(
                        "updateItem: yuuvis RAD returned response: code = %d, message = %s. Please contact administrator",
                        response.code(), response.message());
                throw new Exception(errorMessage);

            } else {
                log.info("updateItem: yuuvis RAD returned response: code = {}, message = {}", response.code(),
                        response.message());
            }

        } catch (Exception e) {
            log.error("updateItem: Error message = {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
