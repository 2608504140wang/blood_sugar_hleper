package com.itwh.pojo.dto;

import com.itwh.pojo.entity.AssociateAccount;
import lombok.Data;

import java.util.List;

@Data
public class UpdateCustomerassociatedIdDTO {

    private Long id;

    private List<AssociateAccount> addIds;

    private List<AssociateAccount> removeIds;
}
