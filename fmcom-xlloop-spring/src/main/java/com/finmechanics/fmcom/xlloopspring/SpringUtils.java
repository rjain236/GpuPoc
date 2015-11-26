/*
 * @author Avinash Johnson
 */
package com.finmechanics.fmcom.xlloopspring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;

/**
 * The Class SpringUtils.
 */
public class SpringUtils {

	/**
	 * Gets the bean.
	 *
	 * @param <T> the generic type
	 * @param bf the bf
	 * @param beanClass the bean class
	 * @return the bean
	 */
	public static <T> T getBean(ListableBeanFactory bf, Class<?> beanClass) {
		String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(bf, beanClass);
		@SuppressWarnings("unchecked")
		T bean = (T) ((names.length == 0) ? null : bf.getBean(names[0]));
		return bean;
	}

	/**
	 * Gets the bean names.
	 *
	 * @param bf the bf
	 * @param beanClass the bean class
	 * @return the bean names
	 */
	public static String[] getBeanNames(ListableBeanFactory bf, Class<?> beanClass) {

		return BeanFactoryUtils.beanNamesForTypeIncludingAncestors(bf, beanClass);
	}

	/**
	 * Gets the beans.
	 *
	 * @param <T> the generic type
	 * @param bf the bf
	 * @param beanClass the bean class
	 * @return the beans
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getBeans(ListableBeanFactory bf, Class<T> beanClass) {
		String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(bf, beanClass);
		List<T> beans = new ArrayList<T>(names.length);
		for (String name : names) {
			beans.add((T) bf.getBean(name));
		}
		return beans;
	}

	/**
	 * Gets the bean.
	 *
	 * @param <T> the generic type
	 * @param bf the bf
	 * @param beanName the bean name
	 * @return the bean
	 */
	public static <T> T getBean(ListableBeanFactory bf, String beanName) {
		@SuppressWarnings("unchecked")
		T bean = (T) (bf.containsBean(beanName) ? bf.getBean(beanName) : null);
		return bean;
	}
}
