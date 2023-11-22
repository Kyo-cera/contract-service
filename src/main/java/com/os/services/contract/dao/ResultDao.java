package com.os.services.contract.dao;

import com.os.services.contract.model.DmsObject;
import com.os.services.contract.model.FilterByIndexData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
@Import(FeignClientsConfiguration.class)
public class ResultDao {

    @Autowired
    ApiClient apiClient;

    public List<DmsObject> getJsonQueryResult(FilterByIndexData filter, String type) throws Exception {
        return apiClient.getJsonQueryResult(filter, type);
    }
}
