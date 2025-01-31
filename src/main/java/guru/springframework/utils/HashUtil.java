package guru.springframework.utils;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.KeccakDigest;
import org.bouncycastle.jcajce.provider.digest.RIPEMD160;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for hashing.
 *
 * @author liaoxuefeng
 */
public class HashUtil {

	/**
	 * Get keccak hash as bytes.
	 */
	public static byte[] keccakAsBytes(byte[] input) {
		Digest d = new KeccakDigest(256);
		byte[] out = new byte[d.getDigestSize()];
		d.update(input, 0, input.length);
		d.doFinal(out, 0);
		return out;
	}

	/**
	 * Get RipeMD160 hash.
	 */
	public static byte[] ripeMd160AsBytes(byte[] input) {
		MessageDigest digest = new RIPEMD160.Digest();
		digest.update(input);
		return digest.digest();
	}

	/**
	 * Generate SHA-1 as hex string (all lower-case).
	 *
	 * @param input
	 *            Input as string.
	 * @return Hex string.
	 */
	public static String sha1(String input) {
		return sha1(input.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Generate SHA-1 as hex string (all lower-case).
	 *
	 * @param input
	 *            Input as bytes.
	 * @return Hex string.
	 */
	public static String sha1(byte[] input) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		md.update(input);
		byte[] digest = md.digest();
		return ByteUtil.toHexString(digest);
	}

	/**
	 * Generate SHA-1 as bytes.
	 *
	 * @param input
	 *            Input as bytes.
	 * @return Bytes.
	 */
	public static byte[] sha1AsBytes(byte[] input) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		md.update(input);
		return md.digest();
	}

	/**
	 * Generate SHA-256 as hex string (all lower-case).
	 *
	 * @param input
	 *            Input as bytes.
	 * @return Hex string.
	 */
	public static String sha256(byte[] input) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		md.update(input);
		byte[] digest = md.digest();
		return ByteUtil.toHexString(digest);
	}

	/**
	 * Generate SHA-256 as bytes.
	 *
	 * @param input
	 *            Input as bytes.
	 * @return SHA bytes.
	 */
	public static byte[] sha256AsBytes(byte[] input) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		md.update(input);
		return md.digest();
	}

	/**
	 * Generate SHA-512 as bytes.
	 *
	 * @param input
	 *            Input as bytes.
	 * @return SHA bytes.
	 */
	public static byte[] sha512AsBytes(byte[] input) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		md.update(input);
		return md.digest();
	}

	/**
	 * Do HMAC-SHA256.
	 *
	 * @return Hex string.
	 */
	public static String hmacSha256(byte[] data, byte[] key) {
		SecretKey skey = new SecretKeySpec(key, "HmacSHA256");
		Mac mac;
		try {
			mac = Mac.getInstance("HmacSHA256");
			mac.init(skey);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
		mac.update(data);
		return ByteUtil.toHexString(mac.doFinal());
	}

	/**
	 * Do HMAC-SHA1.
	 *
	 * @return byte[] as result.
	 */
	public static byte[] hmacSha1(byte[] data, byte[] key) {
		SecretKey skey = new SecretKeySpec(key, "HmacSHA1");
		Mac mac;
		try {
			mac = Mac.getInstance("HmacSHA1");
			mac.init(skey);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
		mac.update(data);
		return mac.doFinal();
	}

	/**
	 * Do HMAC-SHA256.
	 *
	 * @return byte[] as result.
	 */
	public static String hmacSha256(String data, String key) {
		return hmacSha256(data.getBytes(StandardCharsets.UTF_8), key.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Do HMAC-SHA256.
	 *
	 * @return byte[] as result.
	 */
	public static String hmacSha256(byte[] data, String key) {
		return hmacSha256(data, key.getBytes(StandardCharsets.UTF_8));
	}
}
