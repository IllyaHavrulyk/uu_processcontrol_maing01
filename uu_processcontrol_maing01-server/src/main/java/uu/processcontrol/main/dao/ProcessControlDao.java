package uu.processcontrol.main.dao;

import uu.app.datastore.domain.PageInfo;
import uu.app.datastore.domain.PagedResult;
import uu.app.datastore.exceptions.DatastoreConcurrencyRuntimeException;
import uu.app.datastore.exceptions.DatastoreLimitsRuntimeException;
import uu.app.datastore.exceptions.DatastoreRuntimeException;
import uu.app.datastore.exceptions.DatastoreUnexpectedRuntimeException;
import uu.app.objectstore.dao.UuObjectDao;
import uu.processcontrol.main.abl.entity.ProcessControl;

public interface ProcessControlDao extends UuObjectDao<ProcessControl> {

  @Override
  ProcessControl create(ProcessControl processControl) throws DatastoreRuntimeException, DatastoreLimitsRuntimeException;

  @Override
  ProcessControl get(String awid, String id) throws DatastoreRuntimeException, DatastoreUnexpectedRuntimeException;

  @Override
  PagedResult<ProcessControl> list(String awid, PageInfo pageInfo) throws DatastoreRuntimeException;

  @Override
  void delete(String awid, String id) throws DatastoreRuntimeException, DatastoreUnexpectedRuntimeException, DatastoreConcurrencyRuntimeException;

  @Override
  ProcessControl update(ProcessControl entity) throws DatastoreRuntimeException, DatastoreUnexpectedRuntimeException, DatastoreConcurrencyRuntimeException;
}
