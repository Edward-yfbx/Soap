package com.yfbx.soaputil.bean;

import java.util.List;

/**
 * Author: Edward
 * Date: 2017/10/4 22:47
 * Description:
 */

public class Info {

    private List<Object> User;
    private List<Object> Company;
    private List<Object> ItemType;
    private List<Object> Area;
    private List<Object> Industry;
    private List<Object> Type;
    private List<Object> Brand;
    private List<Object> CreditTerms;
    private String EnablePDARevisit;
    private String AllowEmptyInventory;


    public Info() {
    }

    public List<Object> getUser() {
        return User;
    }

    public void setUser(List<Object> user) {
        User = user;
    }

    public List<Object> getCompany() {
        return Company;
    }

    public void setCompany(List<Object> company) {
        Company = company;
    }

    public List<Object> getItemType() {
        return ItemType;
    }

    public void setItemType(List<Object> itemType) {
        ItemType = itemType;
    }

    public List<Object> getArea() {
        return Area;
    }

    public void setArea(List<Object> area) {
        Area = area;
    }

    public List<Object> getIndustry() {
        return Industry;
    }

    public void setIndustry(List<Object> industry) {
        Industry = industry;
    }

    public List<Object> getType() {
        return Type;
    }

    public void setType(List<Object> type) {
        Type = type;
    }

    public List<Object> getBrand() {
        return Brand;
    }

    public void setBrand(List<Object> brand) {
        Brand = brand;
    }

    public List<Object> getCreditTerms() {
        return CreditTerms;
    }

    public void setCreditTerms(List<Object> creditTerms) {
        CreditTerms = creditTerms;
    }

    public String getEnablePDARevisit() {
        return EnablePDARevisit;
    }

    public void setEnablePDARevisit(String enablePDARevisit) {
        EnablePDARevisit = enablePDARevisit;
    }

    public String getAllowEmptyInventory() {
        return AllowEmptyInventory;
    }

    public void setAllowEmptyInventory(String allowEmptyInventory) {
        AllowEmptyInventory = allowEmptyInventory;
    }
}
