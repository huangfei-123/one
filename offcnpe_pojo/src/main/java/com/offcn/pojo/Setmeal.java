package com.offcn.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author mwx
 * @since 2021-07-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_setmeal")
public class Setmeal extends Model {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String code;

    @TableField("helpCode")
    private String helpcode;

    private String sex;

    private String age;

    private Float price;

    private String remark;

    private String attention;

    private String img;
    /**
     * 检查组信息
     */
    @TableField(exist = false)
    private List<Checkgroup> checkgroups;


}
