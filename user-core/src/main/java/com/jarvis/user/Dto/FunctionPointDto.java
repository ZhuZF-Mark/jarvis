package com.jarvis.user.Dto;

import com.jarvis.user.entity.FunctionPoint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * Created by ZZF on 2018/3/20.
 */
@ApiModel
public class FunctionPointDto extends FunctionPoint {
    /**
     * 是否已选择
     */
    @ApiModelProperty("是否已选择")
    private Boolean checked;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
