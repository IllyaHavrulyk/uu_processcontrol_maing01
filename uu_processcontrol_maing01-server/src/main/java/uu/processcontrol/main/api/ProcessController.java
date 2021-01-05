package uu.processcontrol.main.api;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.inject.Inject;
import org.springframework.web.bind.annotation.RequestMethod;
import uu.app.server.CommandContext;
import uu.app.server.annotation.Command;
import uu.app.server.annotation.CommandController;
import uu.processcontrol.main.abl.ProcessControlAbl;
import uu.processcontrol.main.api.dto.ProcessControlCreateDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlDtoOut;
import uu.processcontrol.main.api.dto.ProcessControlGetDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlListDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlListDtoOut;

@CommandController
public class ProcessController {

  @Inject
  ProcessControlAbl processControlAbl;

  @Command(path = "process/start", method = POST)
  public ProcessControlDtoOut create(CommandContext<ProcessControlCreateDtoIn> context) {
    return processControlAbl.create(context.getUri().getAwid(), context.getDtoIn());
  }

  @Command(path = "process/get", method = GET)
  public ProcessControlDtoOut get(CommandContext<ProcessControlGetDtoIn> context) {
    return processControlAbl.get(context.getUri().getAwid(), context.getDtoIn());
  }


  @Command(path = "process/list", method = GET)
  public ProcessControlListDtoOut list(CommandContext<ProcessControlListDtoIn> context) {
    return processControlAbl.list(context.getUri().getAwid(), context.getDtoIn());
  }

}
