package com.os.services.contract.controller;

import com.os.services.contract.model.*;
import com.os.services.contract.service.BpmService;
import com.os.services.contract.service.DmsService;
import com.os.services.contract.service.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class SchedulerController {

    @Autowired
    BpmService bpmService;
    @Autowired
    ResultService resultService;
    @Autowired
    DmsService dmsService;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String OBJECT_TYPE = "absContractDoc";
    private static final String CONTRACT_STATUS_ACT = "Active";
    private static final String CONTRACT_STATUS_INA = "Inactive";
    private static final String PROCESSING_STATUS_FIN = "finalized";
    private static final String PROCESSING_STATUS_TERM = "finalizedTermination";
    private static final String CONTRACT_TYPE_ONETIME = "ONE_TIME";
    private static final String PROCESS_MODEL_NAME = "Renewal_Process";
    private static final String CONTRACT_STATUS_FIN = "Finished";
    private static final String PROCESSING_STATUS_PROS = "processing";
    private static final Logger log = LoggerFactory.getLogger(SchedulerController.class);

    // This task works every configured time and after service restarts
    // @Scheduled(initialDelay = 1000, fixedRate = 5000) for demo
    @Scheduled(cron = "${schedulerCron}")
    @EventListener(ApplicationReadyEvent.class)
    public void schedulerTask() {

        try {

            reminderTask();
            updateTask();
            updateTaskForOneTime();
            updateStatus();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    // Start yuuvis RAD process when today is in diff (contract end data -
    // notification period)
    public void reminderTask() throws Exception {

        try {
            // Create filter
            FilterByIndexData filter = new FilterByIndexData();
            filter.setContractStatus(CONTRACT_STATUS_ACT);
            // filter.setContractStatus(CONTRACT_STATUS_INA);
            filter.setProcessingStatus(PROCESSING_STATUS_FIN);

            // Get target object with filter
            List<DmsObject> dmsObject = resultService.getJsonQueryResult(filter, OBJECT_TYPE);
            log.info("reminderTask: Result search was completed. The number of hit list is {}", dmsObject.size(),
                    "status: ", CONTRACT_STATUS_ACT);

            // Calculate difference days in each object
            dmsObject.forEach((item) -> {

                String contractEndDate = item.getData().getContractEnddate();
                int notificationPeriod = item.getData().getNotificationPeriod();

                if (!item.getData().getContractType().equals(CONTRACT_TYPE_ONETIME)) {
                    log.info("reminderTask: Contract type is not One time. object id = {}", item.getId(), " title",
                            item.getTitle());

                    int dayDiff;
                    try {
                        dayDiff = calculateDayDiff(contractEndDate);
                        if (dayDiff <= notificationPeriod) {
                            log.info("reminderTask: This contract should be notified to user because {} <= {}", dayDiff,
                                    notificationPeriod);

                            // Get process model id of contract management
                            String modelId = getModelIdForContractMng();
                            log.info("reminderTask: Renewal Process model id = {}", modelId);

                            // Start process
                            this.bpmService.startProcessWithModelId(item.getData(), item.getId(), item.getType(),
                                    modelId);
                            log.info("reminderTask: Process was started. id = {}, type = {}, data = {}", item.getId(),
                                    item.getType(), item.getData().toString());

                        } else {
                            // no action
                            log.info("reminderTask: Reminder is not necessary. object id = {}", item.getId());
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }

                } else {
                    log.info("reminderTask: Contract type is One time. object id = {}", item.getId());

                }
            });
        } catch (Exception e) {
            log.error("reminderTask: Error message =  {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    /*
     * Update index data (contractstatus -> Finished, processingstatus -> finalized
     * )
     * when contract end date is today or passed.
     */
    public void updateTask() throws Exception {

        try {
            // Create filter
            FilterByIndexData filter = new FilterByIndexData();
            filter.setProcessingStatus(PROCESSING_STATUS_TERM);
            // Get target object with filter
            List<DmsObject> dmsObject = resultService.getJsonQueryResult(filter, OBJECT_TYPE);
            log.info("updateTask: Result search was completed. The number of hit list is {}", dmsObject.size(), "-- ",
                    OBJECT_TYPE);
            // Check if today is contract end date or not
            dmsObject.forEach((item) -> {
                String contractEndDate = item.getData().getContractEnddate();
                int dayDiff;
                try {
                    dayDiff = calculateDayDiff(contractEndDate);

                    if (dayDiff < 0) {
                        dmsService.updateItem(item.getId(), CONTRACT_STATUS_FIN, PROCESSING_STATUS_FIN);
                        log.info("updateTask: Target object was updated. object id = {}", item.getId(), " title",
                                item.getTitle());

                    } else {
                        // No action
                        log.info("updateTask: This is not the object to be updated. object id = {}", item.getId(),
                                " title", item.getTitle());
                    }

                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            });

        } catch (Exception e) {
            log.error("updateTask: Error message =  {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    /*
     * Update index data (contractstatus -> Finished, processingstatus -> finalized
     * )
     * when contract end date is today or passed.
     */
    public void updateTaskForOneTime() throws Exception {

        try {
            // Create filter
            FilterByIndexData filter = new FilterByIndexData();
            filter.setContractType(CONTRACT_TYPE_ONETIME);
            filter.setContractStatus(CONTRACT_STATUS_ACT);
            filter.setProcessingStatus(PROCESSING_STATUS_FIN);
            // Get target object with filter
            List<DmsObject> dmsObject = resultService.getJsonQueryResult(filter, OBJECT_TYPE);
            log.info("updateTaskForOneTime: Result search was completed. The number of hit list is {}",
                    dmsObject.size());
            // Check if today is contract end date or not
            dmsObject.forEach((item) -> {
                String contractEndDate = item.getData().getContractEnddate();
                int dayDiff;
                try {
                    dayDiff = calculateDayDiff(contractEndDate);

                    if (dayDiff < 0) {
                        dmsService.updateItem(item.getId(), CONTRACT_STATUS_FIN, PROCESSING_STATUS_FIN);
                        log.info("updateTaskForOneTime: Target object was updated. id = {}", item.getId());

                    } else {
                        // No action
                        log.info("updateTaskForOneTime: This is not the object to be updated. object id = {}",
                                item.getId());
                    }

                } catch (Exception e) {
                    log.error("updateTaskForOneTime: Error message =  {}", e.getMessage());
                }
            });

        } catch (Exception e) {
            log.error("updateTaskForOneTime: Error message =  {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    // Status Update Task INI
    public void updateStatus() throws Exception {

        try {
            // Create filter
            FilterByIndexData filter = new FilterByIndexData();
            filter.setContractStatus(CONTRACT_STATUS_INA);
            filter.setProcessingStatus(PROCESSING_STATUS_PROS);

            log.info("typo contract status -->", CONTRACT_STATUS_ACT, " Process status--->", PROCESSING_STATUS_PROS);
            // Get target object with filter
            List<DmsObject> dmsObject = resultService.getJsonQueryResult(filter, OBJECT_TYPE);
            log.info("updateStatus: Result search was completed. The number of hit list is {}", dmsObject.size(),
                    "status: ", CONTRACT_STATUS_INA);

            // Calculate difference days in each object
            dmsObject.forEach((item) -> {

                String contractEndDate = item.getData().getContractEnddate();
                int notificationPeriod = item.getData().getNotificationPeriod();

                int dayDiff;
                try {
                    dayDiff = calculateDayDiff(contractEndDate);
                    if (dayDiff > notificationPeriod) {
                        // WR
                        dmsService.updateItem(item.getId(), CONTRACT_STATUS_ACT,
                                PROCESSING_STATUS_FIN);
                        log.info("updateStatus: This contract should be notified to user because {} < {}", dayDiff,
                                notificationPeriod);

                        // Get process model id of contract management
                        String modelId = getModelIdForContractMng();
                        log.info("updateStatus:  Process model id = {}", modelId);
                    } else if (dayDiff > 0) {
                        dmsService.updateItem(item.getId(), CONTRACT_STATUS_ACT,
                                PROCESSING_STATUS_FIN);
                        // WT
                        log.info("updateStatus: This contract not active {} < {}", dayDiff,
                                notificationPeriod);
                    } else {
                        // No action
                        log.info("updateStatus: This contract not active {} < {}", dayDiff,
                                notificationPeriod);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }

            });
        } catch (Exception e) {
            log.error("updateStatus: Error message =  {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    // Status Update Task INI
    //
    private String getModelIdForContractMng() throws Exception {

        List<ProcessModel> modelIdList = bpmService.getModelIdList();
        log.info("getModelIdForContractMng: The number of model id list is {}", modelIdList.size());

        final String[] modelId = new String[1];
        modelIdList.forEach((item) -> {
            if (item.getName().contains(PROCESS_MODEL_NAME)) {
                modelId[0] = item.getId();

            } else {
                // error
                log.error("This process model does not include " + PROCESS_MODEL_NAME + " " + item.getName());
            }
        });

        return modelId[0];
    }

    private int calculateDayDiff(String contractEndDate) throws Exception {

        Date todayDate = new Date();
        String todayDateStr = DATE_FORMAT.format(todayDate);
        int intDayDiff;

        try {
            Date startDate = ConvertStr2Date(todayDateStr);
            Date endDate = ConvertStr2Date(contractEndDate);
            log.info("calculateDayDiff: Today's date: {}, Contract end date: {}", startDate.toString(),
                    endDate.toString());

            long dateTimeTo = endDate.getTime();
            long dateTimeFrom = startDate.getTime();
            log.info("calculateDayDiff: Today's datetime: {}, Contract end datetime: {}", dateTimeFrom, dateTimeTo);

            long longDayDiff = (dateTimeTo - dateTimeFrom) / (1000 * 60 * 60 * 24);
            intDayDiff = (int) longDayDiff;
            log.info("calculateDayDiff: Contract end datetime - Today's datetime = Difference days: {}", intDayDiff);

        } catch (Exception e) {
            log.error("calculateDayDiff: Error message =  {}", e.getMessage());
            throw new Exception(e.getMessage());
        }

        return intDayDiff;
    }

    private Date ConvertStr2Date(String str) throws Exception {
        return DATE_FORMAT.parse(str);
    }
}
