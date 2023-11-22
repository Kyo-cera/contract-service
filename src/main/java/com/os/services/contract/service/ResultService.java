package com.os.services.contract.service;

import com.os.services.contract.dao.ResultDao;
import com.os.services.contract.model.DmsObject;
import com.os.services.contract.model.FilterByIndexData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {
    @Autowired
    ResultDao resultDao;

    public List<DmsObject> getJsonQueryResult(FilterByIndexData filter, String type) throws Exception {
        return resultDao.getJsonQueryResult(filter, type);
    }

}
