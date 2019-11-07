package com.dliberty.recharge.common.utils;

import java.util.Date;

import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlContext;

import com.dliberty.recharge.common.constants.Constants;
import com.dliberty.recharge.common.ognl.DefaultMemberAccess;

/**
 * 实体类工具类
 * 
 * @author LG
 *
 */
public class EntityUtil {

	private static OgnlContext context = new OgnlContext(null, null, new DefaultMemberAccess(true));

	public static void setValue(Object obj) {
		setValue(obj, "id");
	}

	public static void setUpdateValue(Object obj) {
		try {
			Ognl.setValue("updateTime", context, obj, new Date());
		} catch (Exception e) {
		}
	}

	public static void setValue(Object obj, String primaryKeyName) {
		try {

			Object id = Ognl.getValue(primaryKeyName, context, obj);

			Ognl.setValue("updateTime", context, obj, new Date());
			if (id == null) {
				Ognl.setValue("createTime", context, obj, new Date());
				Ognl.setValue("isDeleted", context, obj, Constants.COMMON_FLAG_NO);
			}
			Ognl.setValue("updateTime", context, obj, new Date());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
