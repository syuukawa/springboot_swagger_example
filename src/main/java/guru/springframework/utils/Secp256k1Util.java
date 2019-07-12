package guru.springframework.utils;

import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Arrays;

import java.math.BigInteger;
import java.security.Provider;

public class Secp256k1Util {

	static final Provider BC = new BouncyCastleProvider();

	static final ECParameterSpec SPEC = ECNamedCurveTable.getParameterSpec("secp256k1");

	static final X9ECParameters PARAMS = SECNamedCurves.getByName("secp256k1");
	static final ECDomainParameters ECPARAMS = new ECDomainParameters(PARAMS.getCurve(), PARAMS.getG(), PARAMS.getN(),
			PARAMS.getH());

	static final byte PRIVATE_KEY_PREFIX = (byte) 0x80;

	static final byte[] PUBLIC_KEY_COMPRESSED_02 = { 0x02 };
	static final byte[] PUBLIC_KEY_COMPRESSED_03 = { 0x03 };

	public static byte[] toCompressedPublicKey(byte[] privateKey) {
		BigInteger[] pks = toUncompressedPublicKey(new BigInteger(1, privateKey));
		byte[] xs = bigIntegerToBytes(pks[0], 32);
		byte[] ys = bigIntegerToBytes(pks[1], 32);
		if ((ys[31] & 0xff) % 2 == 0) {
			return ByteUtil.concat(PUBLIC_KEY_COMPRESSED_02, xs);
		} else {
			return ByteUtil.concat(PUBLIC_KEY_COMPRESSED_03, xs);
		}
	}

	/**
	 * Parse WIF string as private key.
	 */
	public static byte[] parseWIF(String wif) {
		byte[] data = Base58Util.decodeChecked(wif);
		if (data[0] != PRIVATE_KEY_PREFIX) {
			throw new IllegalArgumentException("Leading byte is not 0x80.");
		}
		if (wif.charAt(0) == '5') {
			// remove first 0x80:
			return Arrays.copyOfRange(data, 1, data.length);
		} else {
			if (data[data.length - 1] != 0x01) {
				throw new IllegalArgumentException("Ending byte is not 0x01.");
			}
			// remove first 0x80 and last 0x01:
			return Arrays.copyOfRange(data, 1, data.length - 1);
		}
	}

	public static byte[] toUncompressedPublicKey(byte[] privateKey) {
		BigInteger[] pks = toUncompressedPublicKey(new BigInteger(1, privateKey));
		byte[] xs = bigIntegerToBytes(pks[0], 32);
		byte[] ys = bigIntegerToBytes(pks[1], 32);
		return ByteUtil.concat(xs, ys);
	}

	public static BigInteger[] toUncompressedPublicKey(BigInteger privateKey) {
		ECPoint point = Secp256k1Util.getG().multiply(privateKey).normalize();
		byte[] x = point.getXCoord().getEncoded();
		byte[] y = point.getYCoord().getEncoded();
		return new BigInteger[] { new BigInteger(1, x), new BigInteger(1, y) };
	}

	public static byte[] bigIntegerToBytes(BigInteger bi, int length) {
		byte[] data = bi.toByteArray();
		if (data.length == length) {
			return data;
		}
		// remove leading zero:
		if (data[0] == 0) {
			data = Arrays.copyOfRange(data, 1, data.length);
		}
		if (data.length > length) {
			throw new IllegalArgumentException("BigInteger is too large.");
		}
		byte[] copy = new byte[length];
		System.arraycopy(data, 0, copy, length - data.length, data.length);
		return copy;
	}

	final static ECCurve curve = SPEC.getCurve();
	final static ECPoint G = SPEC.getG();
	final static BigInteger N = SPEC.getN();
	final static BigInteger H = SPEC.getH();

	public static ECPoint getPoint(BigInteger k) {
		return G.multiply(k.mod(SPEC.getN()));
	}

	public static ECPoint getG() {
		return G;
	}

	public static BigInteger getN() {
		return N;
	}

	public static BigInteger getH() {
		return H;
	}

	public static int getFieldSize() {
		return curve.getFieldSize();
	}

	public static ECCurve getCurve() {
		return curve;
	}

}
