package com.student.zhaokangwei.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
//@Setter
//@Getter
public class Asset implements Serializable {
    @NotNull(message = "请传递资产编号")
    private Integer id;//资产编号
    @NotBlank(message = "请传递资产名称")
    private String name;//资产名称
    @NotBlank(message = "请传递资产类型")
    private String type;//类型
    @NotBlank(message = "请传递资产状态")
    private String state;//状态
    @NotNull(message = "请传递资产数量")
    private Integer mount;//数量
    @NotNull(message = "请传递资产单位")
    private String unity;//单位
    @NotNull(message = "请传递资产价格")
    private Double price;//价格
    @NotNull(message = "请传递资产折损率")
    private String discountRate;//折损率

    public Asset() {
    }

    public Asset(String name,
                 String type, String state, Integer mount, String unity,
                 Double price, String discountRate) {
        this.name = name;
        this.type = type;
        this.state = state;
        this.mount = mount;
        this.unity = unity;
        this.price = price;
        this.discountRate = discountRate;
    }

    public Asset(Integer id, String name,
                 String type, String state, Integer mount, String unity,
                 Double price, String discountRate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.state = state;
        this.mount = mount;
        this.unity = unity;
        this.price = price;
        this.discountRate = discountRate;
    }
}
