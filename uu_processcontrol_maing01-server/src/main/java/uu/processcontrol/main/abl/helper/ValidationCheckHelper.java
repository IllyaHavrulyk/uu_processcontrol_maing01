package uu.processcontrol.main.abl.helper;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.apache.tomcat.jni.Proc;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uu.app.client.AppClient;
import uu.app.client.AppClientFactory;
import uu.app.datastore.domain.PageInfo;
import uu.app.uri.Uri;
import uu.processcontrol.main.abl.MetadataListDtoIn;
import uu.processcontrol.main.abl.MetadataListDtoOut;
import uu.processcontrol.main.abl.ValidationResult;
import uu.processcontrol.main.abl.entity.Metadata;
import uu.processcontrol.main.abl.entity.Phase;
import uu.processcontrol.main.abl.entity.PhaseStatus;
import uu.processcontrol.main.abl.entity.ProcessControl;
import uu.processcontrol.main.dao.mongo.ProcessControlMongoDao;

@Component
public class ValidationCheckHelper {

  @Inject
  private AppClientFactory appClientFactory;

  @Inject
  private ProcessControlMongoDao processControlMongoDao;

  public ProcessControl checkValidationPhase(ProcessControl processControl) {
    ZonedDateTime now = ZonedDateTime.now();
    Phase validationPhase = processControl.getPhases().get(1);
    Phase receivingPhase = processControl.getPhases().get(0);
    if (validationPhase.getStatus().equals(PhaseStatus.INIT) && now.isAfter(validationPhase.getStartTime()) && now.isBefore(validationPhase.getEndTime())) {
      receivingPhase.setStatus(PhaseStatus.OK);
      validationPhase.setStatus(PhaseStatus.RUNNING);
      processControl.getPhases().set(0, receivingPhase);
      processControl.getPhases().set(1, validationPhase);
      RestTemplate restTemplate = new RestTemplate();
      String validateUri = "http://localhost:8083/uu-datamanagement-maing01/11111111111111111111111111111110/validate";
      AppClient appClient = appClientFactory.newAppClient();
      ValidationResult validationResultDtoOut = appClient.get(Uri.parse(validateUri), null, ValidationResult.class);
    }
    return processControl;
  }

  public ProcessControl checkModerationPhase(ProcessControl processControl) {
    ZonedDateTime now = ZonedDateTime.now();
    Phase moderationPhase = processControl.getPhases().get(2);
    Phase validationPhase = processControl.getPhases().get(1);
    if (moderationPhase.getStatus().equals(PhaseStatus.INIT)
      && now.isAfter(moderationPhase.getStartTime())
      && now.isBefore(moderationPhase.getEndTime())) {
      AppClient appClient = appClientFactory.newAppClient();
      String listUri = "http://localhost:8083/uu-datamanagement-maing01/11111111111111111111111111111110/metadata/list";
      MetadataListDtoIn metadataListDtoIn = new MetadataListDtoIn();
      metadataListDtoIn.setPageInfo(new PageInfo(0, 10));
      MetadataListDtoOut metadataListDtoOut = appClient.get(Uri.parse(listUri), metadataListDtoIn, MetadataListDtoOut.class);
      for (Metadata metadataItem : metadataListDtoOut.getItemList()) {
        if (metadataItem.getValidationResult().getValidationMessages().size() > 0) {
          processControl.getPhases().get(1).setStatus(PhaseStatus.NOTOK);
          processControl.getPhases().get(2).setStatus(PhaseStatus.NOTOK);
          processControl.setEndTime(ZonedDateTime.now());
          processControlMongoDao.update(processControl);
          return processControl;
        }
      }
      validationPhase.setStatus(PhaseStatus.OK);
      moderationPhase.setStatus(PhaseStatus.RUNNING);
      processControl.getPhases().set(1, validationPhase);
      processControl.getPhases().set(2, moderationPhase);
      processControlMongoDao.update(processControl);
      return processControl;
    }
    if (moderationPhase.getStatus().equals(PhaseStatus.RUNNING) && now.isAfter(moderationPhase.getEndTime())) {
      moderationPhase.setStatus(PhaseStatus.OK);
      processControl.setEndTime(ZonedDateTime.now());
      processControlMongoDao.update(processControl);
      return processControl;
    }
    return processControl;
  }
}
