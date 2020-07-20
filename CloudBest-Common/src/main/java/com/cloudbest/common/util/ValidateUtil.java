package com.cloudbest.common.util;


import com.cloudbest.common.annotations.Validator;
import com.cloudbest.common.constants.CommonConstants;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.enums.RegexEnum;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.ValidationException;
import java.lang.reflect.Field;

/**
 * @author hdq
 * @date 2020/7/15 13:45
 * @desc
 */
@Slf4j
public class ValidateUtil {
    public String validate(){
        try {
            valid(this);
        } catch (ValidationException e) {
            return e.getMessage();
        }
        return null;
    }


    public static void valid(Object object) throws ValidationException {
        // 获取object的类型
        Class<? extends Object> clazz = object.getClass();
        // 获取该类型声明的成员
        Field[] fields = clazz.getDeclaredFields();
        // 遍历属性
        for (Field field : fields) {
            // 对于private私有化的成员变量，通过setAccessible来修改器访问权限
            field.setAccessible(true);
            validate(field, object);
            // 重新设置会私有权限
            field.setAccessible(false);
        }
    }

    public static void validate(Field field, Object object) throws ValidationException {
        String description;
        Object value=null;
        // 获取对象的成员的注解信息
        Validator dv = field.getAnnotation(Validator.class);
        try {
            value = field.get(object);
        } catch (Exception e) {
            CommonExceptionUtils.throwParamException(CommonErrorCode.E_ANALYSIS);
        }
        if (dv == null) {
            return;
        }
        description = "".equals(dv.description()) ? field.getName() : dv
                .description();
        /************* 注解解析工作开始 ******************/
        if (dv.isNotNull()) {
            if (value == null || "".equals(value.toString())) {
                CommonExceptionUtils.throwParamException(description+ CommonConstants.ERR_DESC_PARAM.NONE_ERROR);
            }
        }
        if (value != null) {
            if (value.toString().length() > dv.maxLength() && dv.maxLength() != 0) {
                CommonExceptionUtils.throwParamException(description + "长度不能超过" + dv.maxLength());
            }
            if (value.toString().length() < dv.minLength() && dv.minLength() != 0) {
                CommonExceptionUtils.throwParamException(description + "长度不能小于" + dv.minLength());
            }
            if(!StringUtil.isBlank(value.toString())) {
                if (dv.regexType() != RegexEnum.NONE) {
                    switch (dv.regexType()) {
                        case SPECIALCHAR:
                            if (RegexUtils.hasSpecialChar(value.toString())) {
                                CommonExceptionUtils.throwParamException(description + CommonConstants.ERR_DESC_PARAM.HAS_SPECIALCHAR_ERROR);
                            }
                            break;
                        case CHINESE:
                            if (RegexUtils.isChinese2(value.toString())) {
                                CommonExceptionUtils.throwParamException(description + CommonConstants.ERR_DESC_PARAM.HAS_CHINESE_ERROR);
                            }
                            break;
                        case EMAIL:
                            if (!RegexUtils.isEmail(value.toString())) {
                                CommonExceptionUtils.throwParamException(description + CommonConstants.ERR_DESC_PARAM.FORMAT_ERROR);
                            }
                            break;
                        case NUMBER:
                            if (!RegexUtils.isNumber(value.toString())) {
                                CommonExceptionUtils.throwParamException(description + CommonConstants.ERR_DESC_PARAM.FORMAT_ERROR);
                            }
                            break;
                        case PHONENUMBER:
                        case PAGENO:
                        case PAGESIZE:
                            if (!RegexUtils.isPhoneNumber(value.toString())) {
                                CommonExceptionUtils.throwParamException(description + CommonConstants.ERR_DESC_PARAM.FORMAT_ERROR);
                            }
                            break;
                        case TIME:
                            if (!RegexUtils.isTimeYMDHMS(value.toString())) {
                                CommonExceptionUtils.throwParamException(description + CommonConstants.ERR_DESC_PARAM.FORMAT_ERROR);
                            }
                            break;
                        default:
                            break;
                    }
                }
                if (!"".equals(dv.regexExpression())) {
                    if (!value.toString().matches(dv.regexExpression())) {
                        CommonExceptionUtils.throwParamException(description + CommonConstants.ERR_DESC_PARAM.FORMAT_ERROR);
                    }
                }
            }
        }

        /************* 注解解析工作结束 ******************/
    }


    @Override
    public String  toString() {
        Class<?> c=this.getClass();
        StringBuilder sbuilder=new StringBuilder();
        Field[] fields=c.getDeclaredFields();
        sbuilder.append(c.getName()).append("[");
        for(int i=0;i<fields.length; i++){
            fields[i].setAccessible(true);
            fields[i].getName();
            try {
                if(i == fields.length-1 ){
                    sbuilder.append(fields[i].getName()+":"+fields[i].get(this).toString());
                }else{
                    sbuilder.append(fields[i].getName()+":"+fields[i].get(this).toString()+",");
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sbuilder.append("]");
        return sbuilder.toString();
    }

}
