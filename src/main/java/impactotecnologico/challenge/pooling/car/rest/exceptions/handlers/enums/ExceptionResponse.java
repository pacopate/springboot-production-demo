package impactotecnologico.challenge.pooling.car.rest.exceptions.handlers.enums;

public enum ExceptionResponse {

	BIG_PROBLEM("big problems happen", 1000), NOT_READABLE("Your request couldn't be Readable", 1001),
	INVALID_CONTENT_TYPE("Your request headers are invalid", 1002),
	INVALID_CONTENT("You need to send a valid content", 1003);

	private ExceptionResponse(String message, int code) {
		this.message = message;
		this.code = code;
	}

	private String message;
	private int code;

	public String message() {
		return message;
	}

	public int code() {
		return code;
	}

}
