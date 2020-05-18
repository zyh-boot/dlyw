package com.cx.common.validator;

import com.cx.common.annotation.IsMobile;
import com.cx.common.entity.RegexpConstant;
import com.cx.common.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验是否为合法的手机号码
 *
 * @author Administrator·
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isBlank(s)) {
                return true;
            } else {
                String regex = RegexpConstant.MOBILE_REG;
                return CommonUtil.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}
