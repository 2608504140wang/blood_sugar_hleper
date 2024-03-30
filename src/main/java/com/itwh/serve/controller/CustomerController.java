package com.itwh.serve.controller;

import com.itwh.common.context.BaseContext;
import com.itwh.common.result.Result;
import com.itwh.pojo.dto.UpdateCustomerBasicInformDTO;
import com.itwh.pojo.dto.UpdateCustomerassociatedIdDTO;
import com.itwh.pojo.entity.AssociateAccount;
import com.itwh.pojo.vo.CustomerInformVO;
import com.itwh.serve.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户相关接口
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    /**
     * 查询客户基本信息
     * @return
     */
    @GetMapping("/list")
    public Result listCustomer(){
        CustomerInformVO customerInformVO = customerService.listCustomer(BaseContext.getCurrentId());
        return Result.success("信息查询成功", customerInformVO);
    }

    /**
     * 修改客户基本信息
     * @param updateCustomerBasicInformDTO
     * @return
     */
    @PutMapping("/update")
    public Result updateCustomerBasicInform(@RequestBody UpdateCustomerBasicInformDTO updateCustomerBasicInformDTO){
        customerService.updateCustomerBasicInform(updateCustomerBasicInformDTO);
        return Result.success("客户信息修改成功");
    }

    /**
     * 修改客户关联账号
     * @param updateCustomerassociatedIdDTO
     * @return
     */
    @PutMapping("/associated_id/update")
    public Result updateAssociatedId(@RequestBody UpdateCustomerassociatedIdDTO updateCustomerassociatedIdDTO){
        if (updateCustomerassociatedIdDTO.getAddIds() == null && updateCustomerassociatedIdDTO.getRemoveIds() == null){
            return Result.error(500,"添加和删除关联账号集不能同时为0");
        }
        customerService.updateAssociatedId(updateCustomerassociatedIdDTO);

        return Result.success("客户关联账号修改成功");
    }



}
