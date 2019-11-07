package com.dliberty.recharge.common.advice;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dliberty.recharge.common.exception.CommonException;
import com.dliberty.recharge.common.vo.Response;

/**
 * 统一异常处理
 * 
 * @author LG
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	}

	@ExceptionHandler(value = CommonException.class)
	@ResponseBody
	public Response<Object> jsonErrorHandler(HttpServletRequest req, CommonException e) throws Exception {
		String url = req.getRequestURL().toString();
		logger.warn("访问{}页面发生异常:{}", url, e.getMessage());
		return Response.ofError(e.getMessage());
	}

	/**
	 * 全局异常捕捉处理
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public Response<Object> errorHandler(HttpServletRequest req, Exception ex) {
		String url = req.getRequestURL().toString();
		logger.warn("访问{}页面发生异常:{}", url, ex.getMessage());
		return Response.ofError(ex.getMessage());
	}

	/**
	 * 全局@Validated异常捕捉处理
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public Response<Object> MyMethodArgumentNotValidException(HttpServletRequest req,
			MethodArgumentNotValidException ex) {
		String url = req.getRequestURL().toString();
		logger.warn("访问{}页面发生异常:{}", url, ex.getMessage());
		BindingResult bindingResult = ex.getBindingResult();
		return Response.ofError(bindingResult.getFieldError().getDefaultMessage());
	}

}
