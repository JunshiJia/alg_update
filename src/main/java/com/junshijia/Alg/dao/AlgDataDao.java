package com.junshijia.Alg.dao;

import com.junshijia.Alg.domain.AlgData;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@CacheNamespace(blocking = true)
public interface AlgDataDao {
    @Select("select * from AlgoUpdate")
    List<AlgData> findAll();
}
