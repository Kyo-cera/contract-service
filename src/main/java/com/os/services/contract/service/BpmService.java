package com.os.services.contract.service;

import com.os.services.contract.dao.BpmDao;
import com.os.services.contract.model.*;
import com.os.services.contract.model.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BpmService {

    @Autowired
    BpmDao bpmDao;

    public Process startProcessWithModelId(Data data, String contentId, String contentType, String modelId)
            throws Exception {

        return bpmDao.startProcessWithModelId(data, contentId, contentType, modelId);
    }

    public List<ProcessModel> getModelIdList() throws Exception {
        return bpmDao.getModelIdList();
    }
}
