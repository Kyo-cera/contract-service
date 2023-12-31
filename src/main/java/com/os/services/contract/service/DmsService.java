
package com.os.services.contract.service;

import com.os.services.contract.dao.ApiClient;
import com.os.services.contract.dao.DmsDao;
import com.os.services.contract.model.QDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class DmsService {
    @Autowired
    DmsDao dmsDao;
    @Autowired
    ApiClient apiClient;

    public Map<String, Object> getItem(String id) {
        return this.dmsDao.getItem(id);
    }

    public Map<String, Object> createItem(QDoc qdoc, String parentId) {
        return dmsDao.createItem(qdoc, parentId);
    }

    public Map<String, Object> createItemContent(String id, MultipartFile file) throws IOException {
        return dmsDao.createItemContent(id, file);
    }

    public Map<String, Object> updateItem(QDoc qdoc, String id) {
        return dmsDao.updateItem(qdoc, id);
    }

    public Map<String, Object> deleteItem(String id) {
        return dmsDao.deleteItem(id);
    }

    public void updateItem(String itemId, String contractStatus, String processingStatus) throws Exception {
        apiClient.updateItem(itemId, contractStatus, processingStatus);
    }
}
