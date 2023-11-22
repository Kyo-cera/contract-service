package com.os.services.contract.dao;

import com.os.services.contract.model.*;
import com.os.services.contract.model.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Import(FeignClientsConfiguration.class)
public class BpmDao {

    @Autowired
    ApiClient apiClient;

    public Process startProcessWithModelId(Data data, String contentId, String contentType, String modelId) throws Exception {
        return apiClient.startProcessWithModelId(data, contentId, contentType, modelId);
    }

    public List<ProcessModel> getModelIdList() throws Exception {
        return apiClient.getModelIdList();
    }
}
