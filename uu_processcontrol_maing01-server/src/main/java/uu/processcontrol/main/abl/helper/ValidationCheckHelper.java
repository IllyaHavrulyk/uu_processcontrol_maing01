package uu.processcontrol.main.abl.helper;

import java.time.ZonedDateTime;
import org.springframework.stereotype.Component;
import uu.processcontrol.main.abl.entity.Phase;
import uu.processcontrol.main.abl.entity.PhaseStatus;
import uu.processcontrol.main.abl.entity.ProcessControl;

@Component
public class ValidationCheckHelper {
  private final ZonedDateTime now = ZonedDateTime.now();

  public Phase checkValidationPhase(ProcessControl processControl){
    Phase validationPhase = processControl.getPhases().get(1);
    if(validationPhase.getStatus().equals(PhaseStatus.INIT)
      && validationPhase.getStartTime().isBefore(now)
      && validationPhase.getEndTime().isAfter(now)){
      validationPhase.setStatus(PhaseStatus.RUNNING);
    }
    if(validationPhase.getStatus().equals(PhaseStatus.RUNNING)){
      //Here will be datamanagement/validate
      validationPhase.setStatus(PhaseStatus.OK);
    }
    return validationPhase;
  }

  public Phase checkModerationPhase(ProcessControl processControl){
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
