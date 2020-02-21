package com.junshijia.Alg;

import com.junshijia.Alg.dao.AlgDataDao;
import com.junshijia.Alg.domain.AlgData;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FetchAlgData {
    private InputStream is;
    private SqlSessionFactory factory;
    private SqlSession session;
    private AlgDataDao dataDao;
    private List<AlgData> datas;
    public FetchAlgData() {
        boolean flag = true;
        while(flag) {
            try {
                is = Resources.getResourceAsStream("SqlConfig.xml");
                factory = new SqlSessionFactoryBuilder().build(is);
                session = factory.openSession();
                dataDao = session.getMapper(AlgDataDao.class);
                datas = dataDao.findAll();
                for (AlgData d : datas) {
                    System.out.println(d);
                }
                flag = false;
            } catch (IOException e) {
                System.out.println("db error, w8 500s and reconnect...");
                try {
                    Thread.sleep(500000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            } finally {
                session.close();
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<AlgData> getDatas() {
        return datas;
    }
}
