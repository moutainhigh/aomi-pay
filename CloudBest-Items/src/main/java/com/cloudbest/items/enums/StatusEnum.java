package com.cloudbest.items.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum StatusEnum implements IEnum {
    SHELVES(1,"开启"),
    DISMOUNT(0,"关闭");
    ;
    private  int value;
    private String desc;

    StatusEnum(int value,String desc){
        this.value=value;
        this.desc=desc;
    }
    @Override
    public Integer getValue(){
        return this.value=value;
    }
    @Override
    public String toString(){
        return this.desc=desc;
    }

}
