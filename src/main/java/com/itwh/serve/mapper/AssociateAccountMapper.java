package com.itwh.serve.mapper;

import com.itwh.pojo.entity.AssociateAccount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssociateAccountMapper {

    /**
     * 添加关联账号
     * @param addIds
     */
    void saveBatch(List<AssociateAccount> addIds);

    /**
     * 删除关联账号
     * @param associateAccount
     */
    void delete(AssociateAccount associateAccount);

    /**
     * 查询关联账号
     *
     * @param associateAccount
     * @return
     */
    List<AssociateAccount> list(AssociateAccount associateAccount);
}
