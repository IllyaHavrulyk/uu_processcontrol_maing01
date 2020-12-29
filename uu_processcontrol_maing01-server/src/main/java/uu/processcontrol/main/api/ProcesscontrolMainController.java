package uu.processcontrol.main.api;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.inject.Inject;
import uu.app.server.CommandContext;
import uu.app.server.annotation.Command;
import uu.app.server.annotation.CommandController;
import uu.processcontrol.main.api.dto.ProcesscontrolMainInitDtoIn;
import uu.processcontrol.main.api.dto.ProcesscontrolMainInitDtoOut;
import uu.processcontrol.main.abl.ProcesscontrolMainAbl;

@CommandController
public final class ProcesscontrolMainController {

  @Inject
  private ProcesscontrolMainAbl processcontrolMainAbl;

  @Command(path = "init", method = POST)
  public ProcesscontrolMainInitDtoOut create(CommandContext<ProcesscontrolMainInitDtoIn> ctx) {
    ProcesscontrolMainInitDtoOut dtoOut = processcontrolMainAbl.init(ctx.getUri().getAwid(), ctx.getDtoIn());
    return dtoOut;
  }

}
