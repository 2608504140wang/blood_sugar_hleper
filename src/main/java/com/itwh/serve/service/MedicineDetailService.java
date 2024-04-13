package com.itwh.serve.service;

import com.itwh.pojo.dto.UpdateMedicineDetailDTO;
import com.itwh.pojo.entity.MedicineDetail;
import com.itwh.pojo.entity.TempMedicine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicineDetailService {

    /**
     * 用户新增常用药品
     * @param tempMedicine
     * @return
     */
    void saveMedicineDetail(TempMedicine tempMedicine);

    /**
     *修改用户常用药品
     * @param updateMedicineDetailDTO
     * @return
     */
    void updateMedicineDetail(UpdateMedicineDetailDTO updateMedicineDetailDTO);

    /**
     * 根据用户id获取用户常用药品信息
     *
     * @param userId
     * @return
     */
    List<MedicineDetail> listMedicineDetailByUserId(Long userId);

    /**
     * 根据常用药品id获取用户常用药品信息
     * @param id
     * @return
     */
    MedicineDetail listMedicineDetailById(Long id);

    /**
     * 删除用户常用药品信息
     * @param id
     * @return
     */
    void deleteMedicineDetail(Long id);
}
