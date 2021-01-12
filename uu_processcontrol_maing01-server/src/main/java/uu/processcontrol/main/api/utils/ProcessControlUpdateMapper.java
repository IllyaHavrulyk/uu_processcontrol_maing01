package uu.processcontrol.main.api.utils;

import org.springframework.stereotype.Component;
import uu.processcontrol.main.abl.entity.ProcessControl;
import uu.processcontrol.main.api.dto.ProcessControlUpdateDtoIn;

@Component
public class ProcessControlUpdateMapper {
  public ProcessControl map(ProcessControl source, ProcessControlUpdateDtoIn target) {

    if(target.getEndTime() != null){
      source.setEndTime(target.getEndTime());
    }

    if(target.getPhases() != null){
      source.setPhases(target.getPhases());
    }

    if(target.getStartTime() != null){
      source.setStartTime(target.getStartTime());
    }
    return source;
  }

}
