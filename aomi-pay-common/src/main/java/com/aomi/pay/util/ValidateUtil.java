package com.aomi.pay.util;


import com.aomi.pay.annotations.Validator;
import com.aomi.pay.constants.CommonConstants;
import com.aomi.pay.vo.BaseRequest;
import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.enums.RegexEnum;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.ValidationException;
import java.lang.reflect.Field;
import java.util.List;

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

    /**
     * Desc: 通过反射机制获取对象属性进行校验
     *
     * @param object 1
     * @return : void
     * @author : hdq
     * @date : 2020/7/22 15:47
     */
    public static void valid(Object object) throws ValidationException {
        // 获取object的类型
        Class<? extends Object> clazz = object.getClass();
        // 获取该类型声明的成员
        Field[] fields = clazz.getDeclaredFields();
        // 遍历属性
        for (Field field : fields) {
            fieldValidate(field, object);
            //获取基础请求封装中的param对象成员进行校验
            if (field.getName().equals("param") && field.getClass() != null) {
                //Field[] fieldsParam = field.getClass().getDeclaredFields();
                Field[] fieldsParam = ((BaseRequest) object).getParam().getClass().getDeclaredFields();
                for (Field fieldParam : fieldsParam) {
                    //获取封装的参数object
                    Object objectParam = ((BaseRequest) object).getParam();
                    fieldValidate(fieldParam, (objectParam));
                }
                //获取param父类的对象成员进行校验
                if (field.getClass().getSuperclass() != null) {
                    Field[] fieldsParamBasePage = ((BaseRequest) object).getParam().getClass().getSuperclass().getDeclaredFields();
                    for(Field fieldParamBasePage : fieldsParamBasePage){
                        fieldValidate(fieldParamBasePage, ((BaseRequest) object).getParam());
                    }
                }
            }
        }
    }


    public static void fieldValidate(Field field,Object object) throws ValidationException {
        // 对于private私有化的成员变量，通过setAccessible来修改器访问权限
        field.setAccessible(true);
        validate(field, object);
        // 重新设置会私有权限
        field.setAccessible(false);
    }

    /**
     * Desc: 获取对象成员的注解信息进行校验
     *
     * @param field 1
     * @param object 2
     * @return : void
     * @author : hdq
     * @date : 2020/7/22 15:48
     */
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
        //null校验
        if (dv.isNotNull()) {
            if (value == null || "".equals(value.toString())) {
                CommonExceptionUtils.throwParamException(description+ CommonConstants.ERR_DESC_PARAM.NONE_ERROR);
            }
        }
        if (value != null) {
            //如果参数是list,则遍历校验
            if (value instanceof java.util.List) {
                if(((List) value).isEmpty()){
                    CommonExceptionUtils.throwParamException(description+ CommonConstants.ERR_DESC_PARAM.NONE_ERROR);
                }
                for (Object o : ((List) value)) {
                    validate(o, dv, description);
                }
            } else {
                validate(value, dv, description);
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

    /**
     * Desc: 校验复用
     *
     * @author : hdq
     * @date : 2020/7/24 14:53
     */
    private static void validate(Object value, Validator dv, String description){
        //长度校验
        if (value.toString().length() > dv.maxLength() && dv.maxLength() != 0) {
            CommonExceptionUtils.throwParamException(description + "长度不能超过" + dv.maxLength());
        }
        if (value.toString().length() < dv.minLength() && dv.minLength() != 0) {
            CommonExceptionUtils.throwParamException(description + "长度不能小于" + dv.minLength());
        }
        //正则校验
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
}
