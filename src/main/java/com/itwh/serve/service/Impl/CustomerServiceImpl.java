package com.itwh.serve.service.Impl;

import cn.hutool.core.text.StrBuilder;
import com.itwh.common.constant.MessageConstant;
import com.itwh.common.context.BaseContext;
import com.itwh.common.exception.AccountNotFoundException;
import com.itwh.common.exception.BaseException;
import com.itwh.pojo.dto.UpdateCustomerBasicInformDTO;
import com.itwh.pojo.dto.UpdateCustomerassociatedIdDTO;
import com.itwh.pojo.entity.*;
import com.itwh.pojo.vo.*;
import com.itwh.serve.mapper.AssociateAccountMapper;
import com.itwh.serve.mapper.CustomerDetailsMapper;
import com.itwh.serve.mapper.UserMapper;
import com.itwh.serve.service.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDetailsMapper customerDetailsMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AssociateAccountMapper associateAccountMapper;

    @Autowired
    private BloodGlucoseRecordService bloodGlucoseRecordService;

    @Autowired
    private DietRecordService dietRecordService;

    @Autowired
    private MedicineRecordService medicineRecordService;

    @Autowired
    private SportRecordService sportRecordService;

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

    /**
     * 获取用户健康记录Excel表
     * @return
     */
    public void getReport(HttpServletResponse response, LocalDate beginDate, LocalDate endDate) {
        //存放从begin到end的所有日期
        List<LocalDate> localDateList = new ArrayList<>();
        localDateList.add(beginDate);
        while (!beginDate.equals(endDate)){
            beginDate = beginDate.plusDays(1);
            localDateList.add(beginDate);
        }

        //通过POI将数据写入到Excel文件中
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("template/用户健康记录表");

        try {
            //基于模板文件创建一个新的Excel文件
            XSSFWorkbook excel = new XSSFWorkbook(in);

            //拿到标签页
            XSSFSheet sheet = excel.getSheet("用户健康记录");

            //填充数据
            //1.填入开始时间到截至时间
            sheet.getRow(1).getCell(0).setCellValue(beginDate + "至" + endDate);

            //2.插入详细数据
            int count = 3;
            for (LocalDate date : localDateList){
                sheet.getRow(count).getCell(0).setCellValue(date.toString());
                XSSFRow row = sheet.getRow(count);

                //查询血糖记录
                BloodGlucoseRecordVO bloodGlucoseRecordVO = bloodGlucoseRecordService.listBloodGlucoseRecordByDate(date, date);
                //只查了一天，所以只可能有一天的数据
                List<BloodGlucoseRecord> bloodGlucoseRecords = bloodGlucoseRecordVO.getBloodGlucoseRecordsList().get(0);
                for (BloodGlucoseRecord bloodGlucoseRecord : bloodGlucoseRecords){
                    switch (bloodGlucoseRecord.getPeriodLabel()){
                        case "空腹":
                            row.getCell(1).setCellValue(bloodGlucoseRecord.getGlucoseLevel());
                            break;
                        case "早餐后":
                            row.getCell(2).setCellValue(bloodGlucoseRecord.getGlucoseLevel());
                            break;
                        case "午餐前":
                            row.getCell(3).setCellValue(bloodGlucoseRecord.getGlucoseLevel());
                            break;
                        case "午餐后":
                            row.getCell(4).setCellValue(bloodGlucoseRecord.getGlucoseLevel());
                            break;
                        case "晚餐前":
                            row.getCell(5).setCellValue(bloodGlucoseRecord.getGlucoseLevel());
                            break;
                        case "晚餐后":
                            row.getCell(6).setCellValue(bloodGlucoseRecord.getGlucoseLevel());
                            break;
                        case "睡前":
                            row.getCell(7).setCellValue(bloodGlucoseRecord.getGlucoseLevel());
                            break;
                        case "夜间":
                            row.getCell(8).setCellValue(bloodGlucoseRecord.getGlucoseLevel());
                            break;
                        default:
                            break;
                    }
                }

                //查询饮食记录
                ListDietRecordVO listDietRecordVO = dietRecordService.listDietRecordByDate(date, date);
                //只查了一天，所以只可能有一天的数据
                List<DietRecordVO> dietRecordVOS = listDietRecordVO.getDietRecordVOList().get(0);
                for (DietRecordVO dietRecordVO : dietRecordVOS){
                    StringJoiner food = new StringJoiner(",");
                    switch (dietRecordVO.getPeriodLabel()){
                        case "早餐":
                            for (FoodDetailVO foodDetailVO : dietRecordVO.getFoodDetailVOS()){
                                food.add(foodDetailVO.getName() + " " + foodDetailVO.getFoodNum() + "g");
                            }
                            row.getCell(9).setCellValue(food.toString());
                            break;
                        case "午餐":
                            for (FoodDetailVO foodDetailVO : dietRecordVO.getFoodDetailVOS()){
                                food.add(foodDetailVO.getName() + " " + foodDetailVO.getFoodNum() + "g");
                            }
                            row.getCell(10).setCellValue(food.toString());
                            break;
                        case "晚餐":
                            for (FoodDetailVO foodDetailVO : dietRecordVO.getFoodDetailVOS()){
                                food.add(foodDetailVO.getName() + " " + foodDetailVO.getFoodNum() + "g");
                            }
                            row.getCell(11).setCellValue(food.toString());
                            break;
                        case "加餐":
                            for (FoodDetailVO foodDetailVO : dietRecordVO.getFoodDetailVOS()){
                                food.add(foodDetailVO.getName() + " " + foodDetailVO.getFoodNum() + "g");
                            }
                            row.getCell(12).setCellValue(food.toString());
                            break;
                        default:
                            break;
                    }
                }

                //查询用药记录
                ListMedicineRecordVO listMedicineRecordVO = medicineRecordService.listMedicineRecord(date, date);
                //只查了一天，所以只可能有一天的数据
                List<MedicineRecordVO> medicineRecordVOS = listMedicineRecordVO.getMedicineRecordVOList().get(0);
                for (MedicineRecordVO medicineRecordVO : medicineRecordVOS){
                    StringJoiner medicine = new StringJoiner(",");
                    switch (medicineRecordVO.getPeriodLabel()){
                        case "空腹":
                            for (TempMedicine tempMedicine : medicineRecordVO.getTempMedicines()){
                                medicine.add(tempMedicine.getName()+tempMedicine.getDosage());
                            }
                            row.getCell(13).setCellValue(medicine.toString());
                            break;
                        case "早餐后":
                            for (TempMedicine tempMedicine : medicineRecordVO.getTempMedicines()){
                                medicine.add(tempMedicine.getName()+tempMedicine.getDosage());
                            }
                            row.getCell(14).setCellValue(medicine.toString());
                            break;
                        case "午餐前":
                            for (TempMedicine tempMedicine : medicineRecordVO.getTempMedicines()){
                                medicine.add(tempMedicine.getName()+tempMedicine.getDosage());
                            }
                            row.getCell(15).setCellValue(medicine.toString());
                            break;
                        case "午餐后":
                            for (TempMedicine tempMedicine : medicineRecordVO.getTempMedicines()){
                                medicine.add(tempMedicine.getName()+tempMedicine.getDosage());
                            }
                            row.getCell(16).setCellValue(medicine.toString());
                            break;
                        case "晚餐前":
                            for (TempMedicine tempMedicine : medicineRecordVO.getTempMedicines()){
                                medicine.add(tempMedicine.getName()+tempMedicine.getDosage());
                            }
                            row.getCell(17).setCellValue(medicine.toString());
                            break;
                        case "晚餐后":
                            for (TempMedicine tempMedicine : medicineRecordVO.getTempMedicines()){
                                medicine.add(tempMedicine.getName()+tempMedicine.getDosage());
                            }
                            row.getCell(18).setCellValue(medicine.toString());
                            break;
                        case "睡前":
                            for (TempMedicine tempMedicine : medicineRecordVO.getTempMedicines()){
                                medicine.add(tempMedicine.getName()+tempMedicine.getDosage());
                            }
                            row.getCell(19).setCellValue(medicine.toString());
                            break;
                        case "夜间":
                            for (TempMedicine tempMedicine : medicineRecordVO.getTempMedicines()){
                                medicine.add(tempMedicine.getName()+tempMedicine.getDosage());
                            }
                            row.getCell(20).setCellValue(medicine.toString());
                            break;
                        default:
                            break;
                    }
                }

                //查询运动记录
                ListSportRecordVO listSportRecordVO = sportRecordService.listSportRecord(date, date);
                //只查了一天，所以只可能有一天的数据
                List<SportRecordVO> sportRecordVOS = listSportRecordVO.getSportRecordVOList().get(0);
                for (SportRecordVO sportRecordVO : sportRecordVOS){
                    StringJoiner sport = new StringJoiner(",");
                    switch (sportRecordVO.getPeriodLabel()){
                        case "早晨":
                            for (SportDetailVO sportDetailVO : sportRecordVO.getSportDetailVOS()){
                                sport.add(sportDetailVO.getName()+sportDetailVO.getTime()+"分钟:"+sportDetailVO.getConsumption()+"大卡");
                            }
                            row.getCell(21).setCellValue(sport.toString());
                            break;
                        case "中午":
                            for (SportDetailVO sportDetailVO : sportRecordVO.getSportDetailVOS()){
                                sport.add(sportDetailVO.getName()+sportDetailVO.getTime()+"分钟:"+sportDetailVO.getConsumption()+"大卡");
                            }
                            row.getCell(22).setCellValue(sport.toString());
                            break;
                        case "晚上":
                            for (SportDetailVO sportDetailVO : sportRecordVO.getSportDetailVOS()){
                                sport.add(sportDetailVO.getName()+sportDetailVO.getTime()+"分钟:"+sportDetailVO.getConsumption()+"大卡");
                            }
                            row.getCell(23).setCellValue(sport.toString());
                            break;
                        default:
                            break;
                    }
                }
            }

            //通过输出流将Excel文件下载到客户端浏览器
            ServletOutputStream out = response.getOutputStream();
            excel.write(out);

            //释放资源
            out.close();
            excel.close();
        } catch (IOException e) {
            throw new BaseException("生成用户健康记录表出错！");
        }
    }

}
