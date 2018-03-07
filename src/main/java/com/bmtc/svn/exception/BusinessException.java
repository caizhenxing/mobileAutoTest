package com.bmtc.svn.exception;

/**
 * @author HaoYong
 * @category 业务异常
 * @名称： 业务异常，例如读取文件，文件路径异常
 * @简述：用于请求参数出错的异常
 */
public class BusinessException extends RuntimeException 
{

	/** serialVersionUID */
	private static final long serialVersionUID = 2332608236621015980L;

	private String code;

	public BusinessException()
	{
		super();
	}

	public BusinessException(String code) 
	{
		this.code = code;
	}

	public BusinessException(String code, String message) 
	{
		super(message);
		this.code = code;
	}

	public BusinessException(Throwable cause) 
	{
		super(cause);
	}

	public BusinessException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public BusinessException(String code, String message, Throwable cause)
	{
		super(message, cause);
		this.code = code;
	}

	public String getCode() 
	{
		return code;
	}

	public void setCode(String code) 
	{
		this.code = code;
	}

}
