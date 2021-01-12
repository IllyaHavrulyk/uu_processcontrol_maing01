package uu.processcontrol.main.api;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.inject.Inject;
import uu.app.server.CommandContext;
import uu.app.server.annotation.Command;
import uu.app.server.annotation.CommandController;
import uu.processcontrol.main.abl.ProcessControlAbl;
import uu.processcontrol.main.abl.entity.ProcessControl;
import uu.processcontrol.main.api.dto.ProcessControlCreateDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlCreateSingleDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlDtoOut;
import uu.processcontrol.main.api.dto.ProcessControlGetDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlListDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlListDtoOut;
import uu.processcontrol.main.api.dto.ProcessControlUpdateDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlValidateDtoIn;

@CommandController
public class ProcessController {

  @Inject
  ProcessControlAbl processControlAbl;

  @Command(path = "process/start", method = POST)
  public ProcessControlDtoOut create(CommandContext<ProcessControlCreateDtoIn> context) {
    return processControlAbl.start(context.getUri().getAwid(), context.getDtoIn());
  }

  @Command(path = "process/get", method = GET)
  public ProcessControlDtoOut get(CommandContext<ProcessControlGetDtoIn> context) {
    return processControlAbl.get(context.getUri().getAwid(), context.getDtoIn());
  }


  @Command(path = "process/list", method = GET)
  public ProcessControlListDtoOut list(CommandContext<ProcessControlListDtoIn> context) {
    return processControlAbl.list(context.getUri().getAwid(), context.getDtoIn());
  }

  @Command(path = "process/create", method = POST)
  public ProcessControlDtoOut createSingle(CommandContext<ProcessControlCreateSingleDtoIn> context){

    return processControlAbl.createSingle(context.getUri().getAwid(), context.getDtoIn());
  }

  @Command(path = "process/update", method = POST)
  public ProcessControlDtoOut update(CommandContext<ProcessControlUpdateDtoIn> context){
    return processControlAbl.update(context.getUri().getAwid(), context.getDtoIn());
  }

  @Command(path = "process/validate", method = POST)
  public ProcessControlDtoOut validate(CommandContext<ProcessControlValidateDtoIn> context){
    return processControlAbl.validate(context.getUri().getAwid(), context.getDtoIn());
  }
}
