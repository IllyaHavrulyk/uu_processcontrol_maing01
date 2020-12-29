package uu.processcontrol.main.dao.mongo;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.index.Index;
import uu.app.objectstore.annotations.ObjectStoreDao;
import uu.app.objectstore.mongodb.dao.UuObjectMongoDao;
import uu.processcontrol.main.dao.ProcesscontrolMainDao;
import uu.processcontrol.main.abl.entity.ProcesscontrolMain;

@ObjectStoreDao(entity = ProcesscontrolMain.class, store = "primary")
public class ProcesscontrolMainMongoDao extends UuObjectMongoDao<ProcesscontrolMain> implements ProcesscontrolMainDao {

 public void createSchema() {
   super.createSchema();
   createIndex(new Index().on(ATTR_AWID, Direction.ASC).unique());
 }

}
