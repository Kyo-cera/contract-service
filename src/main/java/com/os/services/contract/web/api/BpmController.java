package com.os.services.contract.web.api;

import com.os.services.contract.model.*;
import com.os.services.contract.model.Process;
import com.os.services.contract.service.BpmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/bpm")
public class BpmController {

    @Autowired
    BpmService bpmService;

    @RequestMapping(value = "/process", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Process startProcessWithModelId(@RequestBody Data data, @RequestParam String contentId, @RequestParam String contentType, @RequestParam String modelId) throws Exception {
        return bpmService.startProcessWithModelId(data, contentId, contentType, modelId);
    }

    @RequestMapping(value = "/management/model", method = RequestMethod.GET, produces = "application/json")
    public List<ProcessModel> getModelIdList() throws Exception {
        return bpmService.getModelIdList();
    }
}
