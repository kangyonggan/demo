package com.kangyonggan.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author kangyonggan
 * @since 8/8/18
 */
@Table(name = "tb_user_profile")
@Data
@ApiModel(description = "用户信息")
public class UserProfile implements Serializable {
    /**
     * 用户ID
     */
    @Column(name = "user_id")
    @ApiModelProperty(hidden = true)
    private Long userId;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", example = "张三")
    private String name;

    /**
     * 证件类型
     */
    @Column(name = "id_type")
    @ApiModelProperty(value = "证件类型", required = false, example = "0")
    private String idType;

    /**
     * 证件号码
     */
    @Column(name = "id_no")
    @ApiModelProperty(value = "证件号码", required = false, example = "35020319690428071X")
    private String idNo;

    /**
     * IP地址
     */
    @Column(name = "ip_address")
    @ApiModelProperty(hidden = true)
    private String ipAddress;

    /**
     * 逻辑删除
     */
    @Column(name = "is_deleted")
    @ApiModelProperty(hidden = true)
    private Byte isDeleted;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    @ApiModelProperty(hidden = true)
    private Date createdTime;

    /**
     * 更新时间
     */
    @Column(name = "updated_time")
    @ApiModelProperty(hidden = true)
    private Date updatedTime;

    private static final long serialVersionUID = 1L;
}