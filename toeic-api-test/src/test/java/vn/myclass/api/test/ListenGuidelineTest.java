package vn.myclass.api.test;

import org.testng.annotations.Test;
import vn.myclass.core.dao.ListenGuidelineDao;
import vn.myclass.core.daoimpl.ListenGuidelineDaoImpl;

public class ListenGuidelineTest {
    @Test
    public void checkFindByProperties()
    {
        ListenGuidelineDao listenGuidelineDao = new ListenGuidelineDaoImpl();
        Object[] result = listenGuidelineDao.findByProperty(null,null,null,null,2,2);

    }
}
