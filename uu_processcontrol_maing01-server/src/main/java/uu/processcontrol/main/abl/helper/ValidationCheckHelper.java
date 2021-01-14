package uu.processcontrol.main.abl.helper;

import java.time.ZonedDateTime;
import org.springframework.stereotype.Component;
import uu.processcontrol.main.abl.entity.Phase;
import uu.processcontrol.main.abl.entity.PhaseStatus;
import uu.processcontrol.main.abl.entity.ProcessControl;

@Component
public class ValidationCheckHelper {
  public Phase checkValidationPhase(ProcessControl processControl){
    ZonedDateTime now = ZonedDateTime.now();
    Phase validationPhase = processControl.getPhases().get(1);
    if(validationPhase.getStatus().equals(PhaseStatus.INIT) && now.isAfter(validationPhase.getStartTime()) && now.isBefore(validationPhase.getEndTime())){
      validationPhase.setStatus(PhaseStatus.RUNNING);
    }
    //here will be validation result and if validation result have some messages
    //Also here will be updating of process with data
    validationPhase.setStatus(PhaseStatus.OK);
    return validationPhase;
  }

  public Phase checkModerationPhase(ProcessControl processControl){
    ZonedDateTime now = ZonedDateTime.now();
    Phase moderationPhase = processControl.getPhases().get(2);
    Phase validationPhase = processControl.getPhases().get(1);
    if(validationPhase.getStatus().equals(PhaseStatus.OK)
      && now.isBefore(moderationPhase.getEndTime())
      && now.isAfter(moderationPhase.getStartTime())
      && moderationPhase.getStatus().equals(PhaseStatus.INIT)
    ){
      moderationPhase.setStatus(PhaseStatus.RUNNING);
    }
    return moderationPhase;
  }
}
