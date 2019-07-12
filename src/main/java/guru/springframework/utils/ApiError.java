package guru.springframework.utils;

public enum ApiError {

	@ApiDescription("Parameter error: the request parameter is invalid.")
	PARAMETER_INVALID,

	@ApiDescription("Header error: the request header is invalid.")
	HEADER_INVALID,

	@ApiDescription("Authenticate error: Authorization header is invalid.")
	AUTH_AUTHORIZATION_INVALID,

	@ApiDescription("Authenticate error: Authorization header is expired.")
	AUTH_AUTHORIZATION_EXPIRED,

	@ApiDescription("Authenticate error: API key is invalid.")
	AUTH_APIKEY_INVALID,

	@ApiDescription("Authenticate error: API key is disabled.")
	AUTH_APIKEY_DISABLED,

	@ApiDescription("Authenticate error: IP forbidden.")
	AUTH_IP_FORBIDDEN,

	@ApiDescription("Authenticate error: API signature is invalid.")
	AUTH_SIGNATURE_INVALID,

	@ApiDescription("Authenticate error: user forbidden to access the resource.")
	AUTH_USER_FORBIDDEN,

	@ApiDescription("Authenticate error: user not active.")
	AUTH_USER_NOT_ACTIVE,

	AUTH_SIGNIN_REQUIRED, AUTH_SIGNIN_FAILED, AUTH_CANNOT_SIGNIN, AUTH_CANNOT_CHANGE_PWD, AUTH_BAD_OLD_PWD,

	@ApiDescription("Account error: cannot freeze asset.")
	ACCOUNT_FREEZE_FAILED,

	@ApiDescription("Account error: unfreeze failed.")
	ACCOUNT_UNFREEZE_FAILED,

	ACCOUNT_ADD_BALANCE_FAILED,

	USER_NOT_FOUND,

	@ApiDescription("Permission error: user cannot signin.")
	USER_CANNOT_SIGNIN,

	@ApiDescription("Permission error: user cannot trade.")
	USER_CANNOT_TRADE,

	@ApiDescription("Permission error: user cannot withdraw.")
	USER_CANNOT_WITHDRAW,

	@ApiDescription("Registration error: user email already exist.")
	USER_EMAIL_EXIST,

	@ApiDescription("Order error: the specific order not found.")
	ORDER_NOT_FOUND,

	ORDER_CANNOT_CANCEL,

	DEPOSIT_FAILED,

	@ApiDescription("This operation cannot be done but can retry later.")
	RETRY_LATER,

	@ApiDescription("Invalid address.")
	ADDRESS_INVALID,

	@ApiDescription("Address failed to check.")
	ADDRESS_CHECK_FAILED,

	@ApiDescription("Address is not allowed.")
	ADDRESS_NOT_ALLOWED,

	@ApiDescription("Cannot add more address.")
	ADDRESS_MAXIMUM,

	@ApiDescription("Withdraw is disabled.")
	WITHDRAW_DISABLED,

	@ApiDescription("Invalid withdraw status.")
	WITHDRAW_INVALID_STATUS,

	@ApiDescription("The requested operation cannot be done.")
	OPERATION_FAILED,

	@ApiDescription("The request body is too large.")
	REQUEST_BODY_TOO_LARGE,

	@ApiDescription("Internal error: internal server error.")
	INTERNAL_SERVER_ERROR;

	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
