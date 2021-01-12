package uu.processcontrol.main.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uu.app.authorization.Authorization;
import uu.app.authorization.DefaultAuthorizationResult;
import uu.app.client.AppClient;
import uu.app.client.AppClientFactory;
import uu.app.client.ClientContextConfiguration;
import uu.app.datastore.domain.PageInfo;
import uu.app.uri.Uri;
import uu.processcontrol.main.SubAppConfiguration;
import uu.processcontrol.main.SubAppRunner;
import uu.processcontrol.main.api.dto.ProcessControlCreateDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlCreateSingleDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlDtoOut;
import uu.processcontrol.main.api.dto.ProcessControlGetDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlListDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlListDtoOut;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SubAppRunner.class,  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {ClientContextConfiguration.class, SubAppConfiguration.class})

public class ProcessControlAblTest {

  private static final String CONTEXT_PATH = "/uu-processcontrol-maing01/00000000000000000000000000000000-11111111111111111111111111111112/";

  private static final String HOST = "http://127.0.0.1:";
  private static final String LIST_PROCESS_COMMAND = "process/list";
  private static final String START_PROCESS_COMMAND = "process/start";
  private static final String CREATE_PROCESS_COMMAND = "process/create";
  private static final String GET_PROCESS_COMMAND = "process/get";

  @LocalServerPort
  private int randomServerPort;

  @Inject
  private AppClientFactory factory;

  @MockBean
  protected Authorization authorization;

  @Before
  public void setup() {
    Set<String> authorizedProfiles = new HashSet<>();
    authorizedProfiles.add("Authorities");
    when(authorization.authorize(any(), any())).thenReturn(new DefaultAuthorizationResult(true, Collections.emptySet()));

    AppClient appClient = factory.newAppClient();
    String uri = HOST + randomServerPort + CONTEXT_PATH;
    ProcessControlCreateSingleDtoIn singleDtoIn = new ProcessControlCreateSingleDtoIn();
    appClient.post(Uri.parse(uri + CREATE_PROCESS_COMMAND), singleDtoIn , ProcessControlCreateSingleDtoIn.class);
  }

  @Test
  public void createProcessTest(){
    //Setting client for proper work
    AppClient appClient = factory.newAppClient();
    String uri = HOST + randomServerPort + CONTEXT_PATH;
    ProcessControlCreateSingleDtoIn createSingleDtoIn = new ProcessControlCreateSingleDtoIn();
    ProcessControlDtoOut createSingleDtoOut = appClient.post(Uri.parse(uri + CREATE_PROCESS_COMMAND), createSingleDtoIn , ProcessControlDtoOut.class);
    String checkableId = createSingleDtoOut.getId();
    ProcessControlGetDtoIn getSingleDtoIn = new ProcessControlGetDtoIn();
    getSingleDtoIn.setId(checkableId);
    ProcessControlDtoOut getDtoOut = appClient.get(Uri.parse(uri + GET_PROCESS_COMMAND), getSingleDtoIn , ProcessControlDtoOut.class);
    assertNotNull(getDtoOut);
    assertNotNull(createSingleDtoOut);
    assertTrue(getDtoOut.getId().equals(checkableId));
    assertNotNull(createSingleDtoOut.getUuAppErrorMap());
    assertTrue(createSingleDtoOut.getUuAppErrorMap().getUuAppErrorMap().isEmpty());
  }

  @Test
  public void startProcessControlTest(){

    //Setting client for proper work
    AppClient appClient = factory.newAppClient();
    String uri = HOST + randomServerPort + CONTEXT_PATH;
    //Getting list of one element and getting it's first item
    ProcessControlListDtoOut controlListDtoOut = createListDtoOut(appClient, uri);
    String checkableId = controlListDtoOut.getItemList().get(0).getId();
    //Setting needed dtoIn and  testing command
    ProcessControlCreateDtoIn processControlCreateDtoIn = new ProcessControlCreateDtoIn();
    processControlCreateDtoIn.setId(checkableId);
    ProcessControlDtoOut startProcessDtoOut = appClient.post(Uri.parse(uri + START_PROCESS_COMMAND),  processControlCreateDtoIn, ProcessControlDtoOut.class);
    assertTrue(startProcessDtoOut.getId() != checkableId);
    assertNotNull(startProcessDtoOut.getUuAppErrorMap());
    assertTrue(startProcessDtoOut.getUuAppErrorMap().getUuAppErrorMap().isEmpty());
  }

  @Test
  public void listProcessControlTest(){
    //Setting client for proper work
    AppClient appClient = factory.newAppClient();
    String uri = HOST + randomServerPort + CONTEXT_PATH;
    //Testing process/list command
    ProcessControlListDtoOut controlListDtoOut = createListDtoOut(appClient, uri);
    assertTrue(controlListDtoOut.getItemList().size() != 0);
    assertNotNull(controlListDtoOut.getItemList().get(0));
    assertNotNull(controlListDtoOut.getUuAppErrorMap());
    assertTrue(controlListDtoOut.getUuAppErrorMap().getUuAppErrorMap().isEmpty());
  }

  @Test
  public void getProcessControlTest(){
    //Setting client for proper work
    AppClient appClient = factory.newAppClient();
    String uri = HOST + randomServerPort + CONTEXT_PATH;
    ProcessControlListDtoOut controlListDtoOut = createListDtoOut(appClient, uri);
    ProcessControlGetDtoIn getDtoIn = new ProcessControlGetDtoIn();
    getDtoIn.setId(controlListDtoOut.getItemList().get(0).getId());
    ProcessControlDtoOut getDtoOut = appClient.get(Uri.parse(uri + GET_PROCESS_COMMAND), getDtoIn , ProcessControlDtoOut.class);
    assertNotNull(controlListDtoOut.getUuAppErrorMap());
    assertTrue(controlListDtoOut.getUuAppErrorMap().getUuAppErrorMap().isEmpty());
    assertNotNull(getDtoOut);
  }

  private ProcessControlListDtoOut createListDtoOut(AppClient appClient, String uri) {
    ProcessControlListDtoIn controlListDtoIn = new ProcessControlListDtoIn();
    controlListDtoIn.setPageInfo(new PageInfo(0,1));
    return appClient.get(Uri.parse(uri + LIST_PROCESS_COMMAND), controlListDtoIn , ProcessControlListDtoOut.class);
   }
}
