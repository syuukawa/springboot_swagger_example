package guru.springframework.utils;

/**
 * Base exception for API.
 *
 * @author liaoxuefeng
 */
public class ApiException extends RuntimeException {

	public final String error;
	public final String data;

	public ApiException(ApiError error) {
		super(error.toString());
		this.error = error.toString();
		this.data = null;
	}

	public ApiException(ApiError error, String data) {
		super(error.toString());
		this.error = error.toString();
		this.data = data;
	}

	public ApiException(ApiError error, String data, String message) {
		super(message);
		this.error = error.name();
		this.data = data;
	}

	public ApiException(String error, String data, String message) {
		super(message);
		this.error = error;
		this.data = data;
	}

	public ApiErrorResponse toErrorResponse() {
		return new ApiErrorResponse(this.error, this.data, this.getMessage());
	}

	public static class ApiErrorResponse {

		public String error;
		public String data;
		public String message;

		public ApiErrorResponse() {
		}

		public ApiErrorResponse(String error, String data, String message) {
			this.error = error;
			this.data = data;
			this.message = message;
		}
	}
}
