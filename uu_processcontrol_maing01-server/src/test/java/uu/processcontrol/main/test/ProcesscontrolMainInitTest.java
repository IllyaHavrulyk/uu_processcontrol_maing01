package uu.processcontrol.main.test;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uu.app.workspace.dto.common.OrganizationDto;
import uu.app.workspace.dto.common.UserDto;
import uu.app.workspace.dto.workspace.LicenseOwnerDto;
import uu.app.workspace.dto.workspace.SysDeleteAppWorkspaceDtoIn;
import uu.app.workspace.dto.workspace.SysInitAppWorkspaceDtoIn;
import uu.app.workspace.store.SysAppWorkspaceDao;
import uu.app.workspace.store.domain.SysAppWorkspace;
import uu.app.workspace.store.domain.WorkspaceState;
import uu.processcontrol.main.test.ProcesscontrolMainAbstractTest;
import uu.processcontrol.main.api.dto.ProcesscontrolMainInitDtoIn;
import uu.processcontrol.main.api.dto.ProcesscontrolMainInitDtoOut;

import java.util.Arrays;

public class ProcesscontrolMainInitTest extends ProcesscontrolMainAbstractTest {

  private String awid = "11111111111111111111111111111112";

  @Inject
  private SysAppWorkspaceDao sysAppWorkspaceDao;

  @Before
  public void setUp() {
    SysInitAppWorkspaceDtoIn dtoIn = new SysInitAppWorkspaceDtoIn();
    dtoIn.setAwid(awid);
    dtoIn.setAwidOwner("0-0");
    UserDto user = new UserDto();
    user.setUuIdentity("0-0");
    user.setName("Foo User");
    OrganizationDto organization = new OrganizationDto();
    organization.setoId("123");
    organization.setName("Foo Organization");
    LicenseOwnerDto licenseOwner = new LicenseOwnerDto();
    licenseOwner.setUserList(Arrays.asList(user));
    licenseOwner.setOrganization(organization);
    dtoIn.setLicenseOwner(licenseOwner);
    workspaceModel.initAppWorkspace(dtoIn);
  }

  @After
  public void deleteWorkspace() {

    SysDeleteAppWorkspaceDtoIn deleteAppWorkspaceDtoIn = new SysDeleteAppWorkspaceDtoIn();
    deleteAppWorkspaceDtoIn.setAwid(awid);

    SysAppWorkspace appWorkspace = sysAppWorkspaceDao.getByAwid(awid);
    appWorkspace.setState(WorkspaceState.CLOSED);
    sysAppWorkspaceDao.update(appWorkspace);

    workspaceModel.deleteAppWorkspace(deleteAppWorkspaceDtoIn);
  }

  @Test
  public void hdsTest() throws Exception {
    ProcesscontrolMainInitDtoIn dtoIn = new ProcesscontrolMainInitDtoIn();
    dtoIn.setAuthoritiesUri("urn:uu:GGALL");
    ProcesscontrolMainInitDtoOut result = processcontrolMainAbl.init(awid, dtoIn);
    assertThat(result.getUuAppErrorMap()).isNull();
  }

}
