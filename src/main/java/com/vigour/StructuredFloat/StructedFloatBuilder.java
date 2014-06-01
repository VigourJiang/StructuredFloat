package com.vigour.StructuredFloat;

public class StructedFloatBuilder {

	/**
	 * build double precision StructuredFloat object from double
	 * @param d
	 * @return
	 */
	public static StructuredFloat buildDouble(double d) {
		long longValue = Double.doubleToRawLongBits(d);
		String bitStr = Long.toBinaryString(longValue);

		return new StructuredFloat(FloatConfig.DoublePrecision)
				.buildByBitStr(bitStr);
	}
	

	/**
	 * build double precision StructuredFloat object from specific value
	 * @param d
	 * @return
	 */
	public static StructuredFloat buildDoubleByType(boolean isNegative,
			FloatNumberType numberType, FloatNumberSpecialValue specialValue) {
		return new StructuredFloat(FloatConfig.DoublePrecision).buildByType(
				isNegative, numberType, specialValue);
	}

	/**
	 * build single precision StructuredFloat object from float
	 * @param d
	 * @return
	 */
	public static StructuredFloat buildFloat(float f) {
		int bitValue = Float.floatToRawIntBits(f);
		String bitStr = Integer.toBinaryString(bitValue);

		return new StructuredFloat(FloatConfig.SinglePrecision)
				.buildByBitStr(bitStr);
	}

	/**
	 * build single precision StructuredFloat object from specific value
	 * @param d
	 * @return
	 */
	public static StructuredFloat buildFloatByType(boolean isNegative,
			FloatNumberType numberType, FloatNumberSpecialValue specialValue) {
		return new StructuredFloat(FloatConfig.SinglePrecision).buildByType(
				isNegative, numberType, specialValue);
	}
}
