package com.fcbai.books.orm.utils;

import java.util.ArrayList;
import java.util.List;

public class FieldEntity {

    // field name
    private String fieldname;
    
    // field value
    private Object value;
    
    // field value's class type
    private Class<?> clazz;
    
    private List<String> errorMsg = new ArrayList<String> ();

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
    
    public List<String> getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(List<String> errorMsg) {
        this.errorMsg = errorMsg;
    }

    public FieldEntity() {
        super();
    }

    public FieldEntity(String fieldname, Object value, Class<?> clazz) {
        super();
        this.fieldname = fieldname;
        this.value = value;
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "FieldEntity [fieldname=" + fieldname + ", value=" + value
                + ", clazz=" + clazz + ", errorMsg=" + errorMsg + "]";
    }
    
}