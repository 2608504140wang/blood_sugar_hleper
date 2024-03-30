package com.itwh.serve.service.Impl;

import com.itwh.common.constant.MessageConstant;
import com.itwh.common.context.BaseContext;
import com.itwh.common.exception.AccountNotFoundException;
import com.itwh.pojo.dto.UpdateCustomerBasicInformDTO;
import com.itwh.pojo.dto.UpdateCustomerassociatedIdDTO;
import com.itwh.pojo.entity.AssociateAccount;
import com.itwh.pojo.entity.CustomerDetails;
import com.itwh.pojo.entity.SysUser;
import com.itwh.pojo.vo.CustomerInformVO;
import com.itwh.serve.mapper.AssociateAccountMapper;
import com.itwh.serve.mapper.CustomerDetailsMapper;
import com.itwh.serve.mapper.UserMapper;
import com.itwh.serve.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDetailsMapper customerDetailsMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AssociateAccountMapper associateAccountMapper;

    /**
     * 查询客户基本信息
     * @return
     */
    public CustomerInformVO listCustomer(Long id) {
        CustomerInformVO customerInformVO = new CustomerInformVO();
        customerInformVO.setUserId(id);
        //查询客户基本信息
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        List<SysUser> list1 = userMapper.list(sysUser);
        if (list1 == null || list1.size() == 0){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        BeanUtils.copyProperties(list1.get(0), customerInformVO);

        //查询客户详细信息
        CustomerDetails customerDetails = customerDetailsMapper.listBasicInform(id);
        if (customerDetails != null){
            BeanUtils.copyProperties(customerDetails, customerInformVO);
        }

        //查询用户关联账号
        AssociateAccount associateAccount = new AssociateAccount();
        associateAccount.setUser1Id(id);
        List<AssociateAccount> list = associateAccountMapper.list(associateAccount);
        if (list != null && list.size() > 0) {
            List<Long> associatedIds = new ArrayList<>();
            for (AssociateAccount associateAccount1 : list){
                associatedIds.add(associateAccount1.getUser2Id());
            }
            customerInformVO.setAssociatedIds(associatedIds);
        }
        return customerInformVO;
    }

    /**
     * 修改客户基本信息
     * @param updateCustomerBasicInformDTO
     */
    @Transactional
    public void updateCustomerBasicInform(UpdateCustomerBasicInformDTO updateCustomerBasicInformDTO) {
        Long userId = BaseContext.getCurrentId();
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setUserId(userId);
        BeanUtils.copyProperties(updateCustomerBasicInformDTO, customerDetails);
        customerDetailsMapper.update(customerDetails);

        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        BeanUtils.copyProperties(updateCustomerBasicInformDTO, sysUser);
        userMapper.update(sysUser);
    }

    /**
     * 获取客户关联账号
     *
     * @return
     */
    public List<AssociateAccount> listAssociatedId() {
        AssociateAccount associateAccount = new AssociateAccount();
        associateAccount.setUser1Id(BaseContext.getCurrentId());
        List<AssociateAccount> associateAccounts = associateAccountMapper.list(associateAccount);
        return associateAccounts;
    }

    /**
     * 修改客户关联账号
     * @param updateCustomerassociatedIdDTO
     * @return
     */
    public void updateAssociatedId(UpdateCustomerassociatedIdDTO updateCustomerassociatedIdDTO) {
        SysUser user = new SysUser();
        user.setId(BaseContext.getCurrentId());
        List<SysUser> list = userMapper.list(user);
        //只可能查找1个或0个客户
        if (list == null || list.size() == 0){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        user = list.get(0);

        List<AssociateAccount> addIds = updateCustomerassociatedIdDTO.getAddIds();
        if (addIds != null && addIds.size() > 0){
            addIds.forEach(associateAccount -> associateAccount.setUser1Id(BaseContext.getCurrentId()));
            associateAccountMapper.saveBatch(addIds);
        }

        List<AssociateAccount> removeIds = updateCustomerassociatedIdDTO.getRemoveIds();
        if (removeIds != null && removeIds.size() > 0){
            removeIds.forEach(associateAccount -> associateAccount.setUser1Id(BaseContext.getCurrentId()));
            for (AssociateAccount associateAccount : removeIds){
                associateAccountMapper.delete(associateAccount);
            }

            removeIds.forEach(associateAccount -> {associateAccount.setUser1Id(associateAccount.getUser2Id());
                                                    associateAccount.setUser2Id(BaseContext.getCurrentId());});
            for (AssociateAccount associateAccount : removeIds){
                associateAccountMapper.delete(associateAccount);
            }
        }

        userMapper.update(user);
    }

}
