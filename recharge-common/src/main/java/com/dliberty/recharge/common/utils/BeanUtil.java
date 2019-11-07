package com.dliberty.recharge.common.utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class BeanUtil extends BeanUtils {
	
	public static <T> T toBean(Object source, Class<T> clazz) {
		if(ObjectUtils.isEmpty(source)) return null;
		
		T target = instantiateClass(clazz);
		copyProperties(source, target);
		return target;
	}

	public static <S, T> List<T> toBeans(List<S> sources, Class<T> clazz) {
		if(CollectionUtils.isEmpty(sources)) return Collections.emptyList();
		
		List<T> beans = new ArrayList<>();
		for (S source : sources) {
			T bean = instantiateClass(clazz);
			copyProperties(source, bean);
			beans.add(bean);
		}
		
		return beans;
	}
}