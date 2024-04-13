package com.itwh.serve.service;

import com.itwh.pojo.dto.UpdateCustomerBasicInformDTO;
import com.itwh.pojo.dto.UpdateCustomerassociatedIdDTO;
import com.itwh.pojo.entity.AssociateAccount;
import com.itwh.pojo.vo.CustomerInformVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

@Service
public interface CustomerService {

    /**
     * 查询客户基本信息
     * @return
     */
    CustomerInformVO listCustomer(Long id);

    /**
     * 修改客户基本信息
     * @param updateCustomerBasicInformDTO
     */
    void updateCustomerBasicInform(UpdateCustomerBasicInformDTO updateCustomerBasicInformDTO);

    /**
     * 获取客户关联账号
     *
     * @return
     */
    List<AssociateAccount> listAssociatedId();

    /**
     * 修改客户关联账号
     * @param updateCustomerassociatedIdDTO
     * @return
     */
    void updateAssociatedId(UpdateCustomerassociatedIdDTO updateCustomerassociatedIdDTO);

    /**
     * 获取用户健康记录Excel表
     * @return
     */
    void getReport(HttpServletResponse response, LocalDate begin, LocalDate end);
}
