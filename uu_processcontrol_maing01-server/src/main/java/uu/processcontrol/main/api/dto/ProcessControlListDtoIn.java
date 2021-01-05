package uu.processcontrol.main.api.dto;

import uu.app.datastore.domain.PageInfo;
import uu.app.validation.ValidationType;

@ValidationType("ProcessControlListDtoInType")
public class ProcessControlListDtoIn {
  private PageInfo pageInfo;

  public PageInfo getPageInfo() {
    return pageInfo;
  }

  public void setPageInfo(PageInfo pageInfo) {
    this.pageInfo = pageInfo;
  }
}
