package com.itwh.serve.service.Impl;

import com.itwh.common.context.BaseContext;
import com.itwh.pojo.dto.UpdateMedicineDetailDTO;
import com.itwh.pojo.entity.MedicineDetail;
import com.itwh.pojo.entity.TempMedicine;
import com.itwh.serve.mapper.MedicineDetailMapper;
import com.itwh.serve.service.MedicineDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineDetailServiceImpl implements MedicineDetailService {

    @Autowired
    private MedicineDetailMapper medicineDetailMapper;

    /**
     * 用户新增常用药品
     * @param tempMedicine
     * @return
     */
    public void saveMedicineDetail(TempMedicine tempMedicine) {
        MedicineDetail medicineDetail = new MedicineDetail();
        BeanUtils.copyProperties(tempMedicine, medicineDetail);
        medicineDetail.setUserId(BaseContext.getCurrentId());

        medicineDetailMapper.save(medicineDetail);
    }

    /**
     *修改用户常用药品
     * @param updateMedicineDetailDTO
     * @return
     */
    public void updateMedicineDetail(UpdateMedicineDetailDTO updateMedicineDetailDTO) {
        MedicineDetail medicineDetail = new MedicineDetail();
        BeanUtils.copyProperties(updateMedicineDetailDTO, medicineDetail);
        medicineDetail.setUserId(BaseContext.getCurrentId());

        medicineDetailMapper.update(medicineDetail);
    }

    /**
     * 根据用户id获取用户常用药品信息
     *
     * @param userId
     * @return
     */
    public List<MedicineDetail> listMedicineDetailByUserId(Long userId) {
        return medicineDetailMapper.listByUserId(userId);
    }

    /**
     * 根据常用药品id获取用户常用药品信息
     * @param id
     * @return
     */
    public MedicineDetail listMedicineDetailById(Long id) {
        return medicineDetailMapper.listById(id);
    }

    /**
     * 删除用户常用药品信息
     * @param id
     * @return
     */
    public void deleteMedicineDetail(Long id) {
        MedicineDetail medicineDetail = new MedicineDetail();
        medicineDetail.setId(id);
        medicineDetail.setUserId(BaseContext.getCurrentId());
        medicineDetailMapper.delete(medicineDetail);
    }

}
