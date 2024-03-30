package com.itwh.serve.mapper;

import com.itwh.common.annotation.AutoFillUser;
import com.itwh.common.enumeration.OperationType;
import com.itwh.pojo.entity.CustomerDetails;
import com.itwh.pojo.vo.CustomerInformVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerDetailsMapper {

    /**
     * 查询客户基本信息
     * @return
     */
    CustomerDetails listBasicInform(Long id);

    /**
     * 修改客户详细信息
     * @param customerDetails
     */
    @AutoFillUser(value = OperationType.UPDATE)
    void update(CustomerDetails customerDetails);

    /**
     * 获取客户信息
     * @param customerDetails
     * @return
     */
    CustomerDetails list(CustomerDetails customerDetails);
}
