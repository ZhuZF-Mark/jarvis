package com.jarvis.user.dto;

/**
 * Created by Dong yangjian on 2018/3/14.
 */
public class SupplierDto {

    private Long id;

    private String supliersName;

    private String supliersShortName;

    private Long parentsupliersId;

    private String supliersCode;

    private Boolean useFlg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupliersName() {
        return supliersName;
    }

    public void setSupliersName(String supliersName) {
        this.supliersName = supliersName;
    }

    public String getSupliersShortName() {
        return supliersShortName;
    }

    public void setSupliersShortName(String supliersShortName) {
        this.supliersShortName = supliersShortName;
    }

    public Long getParentsupliersId() {
        return parentsupliersId;
    }

    public void setParentsupliersId(Long parentsupliersId) {
        this.parentsupliersId = parentsupliersId;
    }

    public String getSupliersCode() {
        return supliersCode;
    }

    public void setSupliersCode(String supliersCode) {
        this.supliersCode = supliersCode;
    }

    public Boolean getUseFlg() {
        return useFlg;
    }

    public void setUseFlg(Boolean useFlg) {
        this.useFlg = useFlg;
    }
}
