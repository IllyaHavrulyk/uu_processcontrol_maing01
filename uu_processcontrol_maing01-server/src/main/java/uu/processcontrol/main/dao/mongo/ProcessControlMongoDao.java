package uu.processcontrol.main.dao.mongo;

import uu.app.datastore.concurrency.ConcurrencyStrategy;
import uu.app.datastore.domain.PageInfo;
import uu.app.datastore.domain.PagedResult;
import uu.app.datastore.exceptions.DatastoreLimitsRuntimeException;
import uu.app.datastore.exceptions.DatastoreRuntimeException;
import uu.app.objectstore.annotations.ObjectStoreDao;
import uu.app.objectstore.mongodb.dao.UuObjectMongoDao;
import uu.processcontrol.main.abl.entity.ProcessControl;
import uu.processcontrol.main.dao.ProcessControlDao;

@ObjectStoreDao(entity = ProcessControl.class, store = "primary", concurrency = ConcurrencyStrategy.NONE)
public class ProcessControlMongoDao extends UuObjectMongoDao<ProcessControl> implements ProcessControlDao {

  @Override
  public ProcessControl create(ProcessControl processControl) throws DatastoreRuntimeException, DatastoreLimitsRuntimeException {
    return super.create(processControl);
  }

  @Override
  public ProcessControl get(String awid, String id) {
    return super.get(awid, id);
  }

  @Override
  public PagedResult<ProcessControl> list(String awid, PageInfo pageInfo) throws DatastoreRuntimeException {
    return super.list(awid, pageInfo);
  }

  @Override
  public void delete(String awid, String id) {
    super.delete(awid, id);
  }
}
