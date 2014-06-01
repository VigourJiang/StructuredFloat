package com.vigour.StructuredFloat;

/**
 * Configuration class for {@link StructuredFloat}
 * @author vigour
 *
 */
public class FloatConfig {
	public static final FloatConfig DoublePrecision;
	public static final FloatConfig SinglePrecision;

	private FloatConfig(int expBitCount, int fractionCount) {
		ExpBitCount = expBitCount;
		FractionBitCount = fractionCount;

		ExpBias = (1 << expBitCount - 1) - 1;
		MinExp = 1 - ExpBias;
		MaxExp = (1 << expBitCount) - 1 - 1 - ExpBias;

		MaxFraction = (1L << fractionCount) - 1;
	}

	static {
		DoublePrecision = new FloatConfig(11, 52);
		SinglePrecision = new FloatConfig(8, 23);
	}

	/**
	 * Exponent part bits count
	 */
	public final int ExpBitCount;

	/**
	 * Fraction part bits count
	 */
	public final int FractionBitCount;

	/**
	 * unbiased maximum exponent number
	 */
	public final int MaxExp;
	
	/**
	 * unbiased minimum exponent number
	 */
	public final int MinExp;
	
	/**
	 * exponent bias
	 */
	public final int ExpBias;

	/**
	 * max fraction number
	 */
	public final long MaxFraction;

}
