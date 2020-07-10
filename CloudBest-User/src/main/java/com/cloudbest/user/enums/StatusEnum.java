package com.cloudbest.user.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/*自己定义状态码
        定义枚举类*/
public enum StatusEnum implements IEnum {
    SHELVES(1,"上架"),
    DISMOUNT(0,"下架");
    ;
    private  int value;
    private String desc;

    StatusEnum(int value, String desc){
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

