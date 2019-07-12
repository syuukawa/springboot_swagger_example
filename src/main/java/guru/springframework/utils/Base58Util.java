package guru.springframework.utils;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;

import java.math.BigInteger;
import java.util.Arrays;


public class Base58Util {
	/**
	 * Legal base58 characters.
	 */
	static final String BASE58_CHARS = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

	static final char[] ALPHABET = BASE58_CHARS.toCharArray();
	static final char ENCODED_ZERO = ALPHABET[0];
	static final int[] INDEXES = new int[128];

	static {
		Arrays.fill(INDEXES, -1);
		for (int i = 0; i < ALPHABET.length; i++) {
			INDEXES[ALPHABET[i]] = i;
		}
	}

	/**
	 * Encodes the given bytes as a base58 string (no checksum is appended).
	 */
	public static String encode(byte[] input) {
		if (input.length == 0) {
			return "";
		}
		// Count leading zeros.
		int zeros = 0;
		while (zeros < input.length && input[zeros] == 0) {
			zeros++;
		}
		// Convert base-256 digits to base-58 digits (plus conversion to ASCII
		// characters)
		input = Arrays.copyOf(input, input.length); // since we modify it
													// in-place
		char[] encoded = new char[input.length * 2]; // upper bound
		int outputStart = encoded.length;
		for (int inputStart = zeros; inputStart < input.length;) {
			encoded[--outputStart] = ALPHABET[divmod(input, inputStart, 256, 58)];
			if (input[inputStart] == 0) {
				inputStart++; // optimization - skip leading zeros
			}
		}
		// Preserve exactly as many leading encoded zeros in output as there
		// were leading zeros in input.
		while (outputStart < encoded.length && encoded[outputStart] == ENCODED_ZERO) {
			outputStart++;
		}
		while (--zeros >= 0) {
			encoded[--outputStart] = ENCODED_ZERO;
		}
		// Return encoded string (including encoded leading zeros).
		return new String(encoded, outputStart, encoded.length - outputStart);
	}

	/**
	 * Decodes the given base58 string into the original data bytes.
	 *
	 * @param input
	 *            the base58-encoded string to decode
	 * @return the decoded data bytes
	 */
	public static byte[] decode(String input) {
		if (input.length() == 0) {
			return new byte[0];
		}
		// Convert the base58-encoded ASCII chars to a base58 byte sequence
		// (base58 digits).
		byte[] input58 = new byte[input.length()];
		for (int i = 0; i < input.length(); ++i) {
			char c = input.charAt(i);
			int digit = c < 128 ? INDEXES[c] : -1;
			if (digit < 0) {
				throw new IllegalArgumentException("Illegal character " + c + " at position " + i);
			}
			input58[i] = (byte) digit;
		}
		// Count leading zeros.
		int zeros = 0;
		while (zeros < input58.length && input58[zeros] == 0) {
			++zeros;
		}
		// Convert base-58 digits to base-256 digits.
		byte[] decoded = new byte[input.length()];
		int outputStart = decoded.length;
		for (int inputStart = zeros; inputStart < input58.length;) {
			decoded[--outputStart] = divmod(input58, inputStart, 58, 256);
			if (input58[inputStart] == 0) {
				++inputStart; // optimization - skip leading zeros
			}
		}
		// Ignore extra leading zeroes that were added during the calculation.
		while (outputStart < decoded.length && decoded[outputStart] == 0) {
			++outputStart;
		}
		// Return decoded data (including original number of leading zeros).
		return Arrays.copyOfRange(decoded, outputStart - zeros, decoded.length);
	}

	public static BigInteger decodeToBigInteger(String input) {
		return new BigInteger(1, decode(input));
	}

	/**
	 * Decodes the given base58 string into the original data bytes, using the
	 * checksum in the last 4 bytes of the decoded data to verify that the rest
	 * are correct. The checksum is removed from the returned data.
	 *
	 * @param input
	 *            the base58-encoded string to decode (which should include the
	 *            checksum)
	 */
	public static byte[] decodeChecked(String input) {
		byte[] decoded = decode(input);
		if (decoded.length < 4)
			throw new ApiException(ApiError.ADDRESS_INVALID, "address", "Address too short: " + input);
		byte[] data = Arrays.copyOfRange(decoded, 0, decoded.length - 4);
		byte[] checksum = Arrays.copyOfRange(decoded, decoded.length - 4, decoded.length);
		byte[] actualChecksum = Arrays.copyOfRange(doubleSha256(data), 0, 4);
		if (!Arrays.equals(checksum, actualChecksum))
			throw new ApiException(ApiError.ADDRESS_INVALID, "address", "Address checksum failed: " + input);
		return data;
	}

	static byte divmod(byte[] number, int firstDigit, int base, int divisor) {
		// this is just long division which accounts for the base of the input
		// digits
		int remainder = 0;
		for (int i = firstDigit; i < number.length; i++) {
			int digit = (int) number[i] & 0xFF;
			int temp = remainder * base + digit;
			number[i] = (byte) (temp / divisor);
			remainder = temp % divisor;
		}
		return (byte) remainder;
	}

	/**
	 * Get SHA-256 hash.
	 */
	static byte[] sha256(byte[] input) {
		Digest d = new SHA256Digest();
		d.update(input, 0, input.length);
		byte[] out = new byte[d.getDigestSize()];
		d.doFinal(out, 0);
		return out;
	}

	/**
	 * Get double SHA-256 hash.
	 */
	static byte[] doubleSha256(byte[] input) {
		byte[] round1 = sha256(input);
		return sha256(round1);
	}

}
