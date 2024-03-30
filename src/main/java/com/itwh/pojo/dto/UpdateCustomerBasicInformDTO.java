package com.itwh.pojo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateCustomerBasicInformDTO {

    private String nickName;

    private String name;

    private String gender;

    private String avatar;

    private Integer height;

    private Integer weight;

    private Integer age;

    private String address;

    private LocalDate birthday;

    private String occupation;

    private String introduction;

}
