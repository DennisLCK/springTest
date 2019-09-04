package _00_init.ude;

public class OrderAmountLimitException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OrderAmountLimitException() {
	}

	public OrderAmountLimitException(String message) {
		super(message);
	}

	public OrderAmountLimitException(Throwable cause) {
		super(cause);
	}

	public OrderAmountLimitException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrderAmountLimitException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
