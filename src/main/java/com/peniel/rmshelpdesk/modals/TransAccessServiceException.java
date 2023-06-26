package com.peniel.rmshelpdesk.modals;

public class TransAccessServiceException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	private int errorCode;

	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public TransAccessServiceException(int errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	public TransAccessServiceException() {
		super();
	}
}

