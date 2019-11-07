package com.dliberty.recharge.common.vo;


import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.dliberty.recharge.common.constants.Constants.ErrorCode;
import com.dliberty.recharge.common.utils.BeanUtil;

import lombok.val;

public class Response<T extends Object> implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean success = true;

	private int code = 0;

	private T data;

	private PageBase pageInfo;

	private List<T> rows;

	private String msg;

	public Response() {
		this.rows = Collections.emptyList();
		this.success = true;
		this.code = 0;
	}

	public static <E> Response<E> of(boolean success) {
		return of(success, success ? "操作成功" : "操作失败");
	}

	public static <E> Response<E> of(boolean success, String msg) {
		Response<E> response = new Response<>();
		response.success = success;
		response.code = success ? 0 : 1;
		response.msg = msg;
		return response;
	}

	public static <E> Response<E> ofObject(Pair<Boolean, String> pair) {
		return pair.getValue0() ? of(pair.getValue0()) : ofError(ErrorCode.PARAM_ERROR, pair.getValue1());
	}

	public static <E> Response<E> ofObject(ErrorCode code, Pair<Boolean, String> pair) {
		return pair.getValue0() ? of(pair.getValue0()) : ofError(code, pair.getValue1());
	}

	public static <E, T> Response<E> ofObject(ErrorCode code, Triplet<Boolean, T, String> triplet) {
		return triplet.getValue0() ? of(triplet.getValue0()) : ofError(code, triplet.getValue2());
	}

	public static <E> Response<E> ofData(E data) {
		Response<E> response = new Response<>();
		response.msg = ObjectUtils.isEmpty(data) ? "查询无数据" : "";
		response.data = data;
		return response;
	}

	public static <E, T> Response<E> ofData(T data, Class<E> clazz) {
		Response<E> response = new Response<>();
		response.msg = ObjectUtils.isEmpty(data) ? "查询无数据" : "";
		response.data = BeanUtil.toBean(data, clazz);
		return response;
	}

	public static <E, T> Response<E> ofData(boolean success, E data, String msg) {
		Response<E> response = ofData(data);
		response.success = success;
		response.code = success ? 0 : 1;
		response.msg = msg;
		return response;
	}

	public static <E> Response<E> ofData(E data, String msg) {
		Response<E> response = new Response<>();
		response.data = data;
		response.msg = msg;
		return response;
	}

	public static <E> Response<E> ofList(List<E> rows) {
		Response<E> response = new Response<>();
		response.rows = rows;
		return response;
	}

	public static <E> Response<E> ofList(Pair<List<E>, String> pair) {
		Response<E> response = new Response<>();
		response.rows = pair.getValue0();
		response.msg = pair.getValue1();
		return response;
	}

	public static <E, T> Response<E> ofList(List<T> rows, Class<E> clazz) {
		Response<E> response = new Response<>();
		response.rows = BeanUtil.toBeans(rows, clazz);
		return response;
	}

	public static <E> Response<E> ofPage(IPage<E> page) {
		Response<E> response = new Response<>();
		response.pageInfo = new PageBase(page.getTotal(), page.getCurrent(), page.getSize());
		response.rows = page.getRecords();
		return response;
	}

	public static <E> Response<E> ofPage(Pair<List<E>, PageInfo> pair) {
		Response<E> response = new Response<>();
		if (ObjectUtils.isNotEmpty(pair.getValue1())) {
			val page = pair.getValue1();
			response.pageInfo = new PageBase(page.getTotalRecords(), page.getPageNum(), page.getPageSize());
		}
		response.rows = pair.getValue0();
		return response;
	}

	public static <E> Response<E> ofPage(List<E> rows, PageInfo page) {
		Response<E> response = new Response<>();
		response.pageInfo = new PageBase(page.getTotalRecords(), page.getPageNum(), page.getPageSize());
		response.rows = CollectionUtils.isNotEmpty(rows) ? rows : new LinkedList<>();
		return response;
	}

	public static <E, T> Response<E> ofPage(IPage<T> page, Class<E> clazz) {
		Response<E> response = new Response<>();
		response.pageInfo = new PageBase(page.getTotal(), page.getCurrent(), page.getSize());
		response.rows = BeanUtil.toBeans(page.getRecords(), clazz);
		return response;
	}

	public static <E> Response<E> ofPair(Pair<E, String> pair) {
		Response<E> response = new Response<>();
		response.data = pair.getValue0();
		response.msg = pair.getValue1();
		return response;
	}

	public static <E> Response<E> ofPair(Pair<Boolean, List<String>> pair, String defaultMsg) {
		Response<E> response = new Response<>();
		response.success = pair.getValue0();
		response.msg = pair.getValue0() ? defaultMsg : String.join("\r\n", pair.getValue1());
		return response;
	}

	public static <E> Response<E> ofError(ErrorCode error) {
		return ofError(error, "操作失败");
	}

	public static <E> Response<E> ofError(Exception e) {
		return ofError(ErrorCode.REQ_ERROR, e);
	}

	public static <E> Response<E> ofError(String errorMsg) {
		Response<E> response = new Response<>();
		response.success = false;
		response.code = ErrorCode.PARAM_ERROR.getCode();
		response.msg = errorMsg;
		return response;
	}

	public static <E> Response<E> ofError(Pair<Boolean, String> pair) {
		Response<E> response = new Response<>();
		response.success = false;
		response.code = ErrorCode.PARAM_ERROR.getCode();
		response.msg = StringUtils.isNotBlank(pair.getValue1()) ? pair.getValue1() : "操作失败";
		return response;
	}

	public static <E> Response<E> ofError(ErrorCode error, String defaultErrorMsg) {
		Response<E> response = new Response<>();
		response.success = false;
		response.code = error.getCode();
		response.msg = StringUtils.isNotBlank(defaultErrorMsg) ? defaultErrorMsg : error.getDesc();
		return response;
	}

	public static <E> Response<E> ofError(ErrorCode error, Exception e) {
		return ofError(error, e.getStackTrace().toString());
	}

	public boolean isSuccess() {
		return success;
	}

	public int getCode() {
		return code;
	}

	public T getData() {
		return data;
	}

	public List<T> getRows() {
		return rows;
	}

	public PageBase getPageInfo() {
		return pageInfo;
	}

	public String getMsg() {
		return msg;
	}

}