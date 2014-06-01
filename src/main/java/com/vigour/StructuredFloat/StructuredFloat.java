package com.vigour.StructuredFloat;

import java.math.BigDecimal;

/**
 * ���Ծ�ȷ�Ը�������нṹ���������ࡣ
 * 
 * @author vigour
 * 
 */
public class StructuredFloat implements Cloneable {
	public StructuredFloat(FloatConfig config) {
		this.config = config;
	}

	/**
	 * Build float number by three parts: signed bit, exponent value, fraction value.
	 * @param isNegative
	 * @param normalizedExpValue
	 * @param fractionValue
	 * @return
	 */
	public StructuredFloat buildByParts(boolean isNegative,
			int normalizedExpValue, long fractionValue) {
		negative = isNegative;
		normalizedExp = normalizedExpValue;
		fraction = fractionValue;
		buildSpecialValue();
		return this;
	}

	/**
	 * set value for fields: specialValue and numberType
	 * @return
	 */
	private StructuredFloat buildSpecialValue() {
		specialValue = FloatNumberSpecialValue.NoSpecial;
		numberType = FloatNumberType.Normalized;
		if (normalizedExp == config.MinExp - 1) {
			if (fraction == 0) {
				specialValue = FloatNumberSpecialValue.Zero;
			} else {
				numberType = FloatNumberType.SubNormalized;
			}
		} else if (normalizedExp == config.MaxExp + 1) {
			if (fraction == 0) {
				numberType = FloatNumberType.Infinite;
			} else {
				numberType = FloatNumberType.Nan;
			}
		} else {
			if (normalizedExp == config.MaxExp
					&& fraction == config.MaxFraction) {
				specialValue = FloatNumberSpecialValue.MaxValue;
			} else if (normalizedExp == config.MinExp && fraction == 0) {
				specialValue = FloatNumberSpecialValue.MinNormal;
			} else if (normalizedExp == config.MinExp - 1 && fraction == 1) {
				specialValue = FloatNumberSpecialValue.MinSubNormal;
			}
		}
		return this;
	}

	public StructuredFloat buildByType(boolean isNegative,
			FloatNumberType doubleType, FloatNumberSpecialValue specialValue) {
		switch (doubleType) {
		case Nan:
			normalizedExp = config.MaxExp + 1;
			fraction = 1;
			negative = isNegative;
			break;
		case Infinite: {
			normalizedExp = config.MaxExp + 1;
			fraction = 0;
			negative = isNegative;
			break;
		}
		case Normalized: {
			switch (specialValue) {
			case Zero:
				normalizedExp = config.MinExp;
				fraction = 0;
				negative = isNegative;
				break;
			case MaxValue:
				normalizedExp = config.MaxExp;
				fraction = config.MaxFraction;
				negative = isNegative;
				break;

			case MinNormal:
				normalizedExp = config.MinExp;
				fraction = 0;
				negative = isNegative;
				break;
			default:
				throw new IllegalArgumentException();
			}
			// Attetion
			break;
		}
		case SubNormalized:
			switch (specialValue) {
			case MaxSubNormal:
				normalizedExp = config.MinExp - 1;
				fraction = config.MaxFraction;
				negative = isNegative;
				break;
			case MinSubNormal:
				normalizedExp = config.MinExp - 1;
				fraction = 1;
				negative = isNegative;
				break;
			default:
				throw new IllegalArgumentException();
			}
			// Attetion
			break;
		}
		buildSpecialValue();
		return this;
	}

	public StructuredFloat buildByBitStr(String bitStr) {
		while (bitStr.length() < config.ExpBitCount + config.FractionBitCount
				+ 1)
			bitStr = "0" + bitStr;
		final boolean isMinus = bitStr.charAt(0) == '1';

		final String expStr = bitStr.substring(1, config.ExpBitCount + 1);
		final int biasedExp = Integer.parseInt(expStr, 2);

		final int normalizedExpValue = biasedExp - config.ExpBias;

		final String fractionStr = bitStr.substring(config.ExpBitCount + 1);
		final long fraction = Long.parseLong(fractionStr, 2);

		return buildByParts(isMinus, normalizedExpValue, fraction);
	}

	public StructuredFloat clone() {
		try {
			return (StructuredFloat) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	private FloatConfig config;

	private boolean negative;

	/**
	 * ���
	 * 
	 * @return
	 */
	public boolean isNegative() {
		return negative;
	}

	/**
	 * ��ƫ�Ƶ�ָ������
	 * 
	 * @return
	 */
	public int getBiasedExp() {
		return normalizedExp + config.ExpBias;
	}

	/**
	 * ����ƫ�Ƶ�ָ������
	 */
	private int normalizedExp;

	public int getExp() {
		return normalizedExp;
	}

	/**
	 * С������
	 */
	private long fraction;

	public long getFraction() {
		return fraction;
	}

	private FloatNumberType numberType;
	private FloatNumberSpecialValue specialValue;

	public FloatNumberSpecialValue getSpecialValue() {
		return specialValue;
	}

	public FloatNumberType getNumberType() {
		return numberType;
	}

	/**
	 * ��ȡBigDecimal��ֵ���������֣�����null
	 * 
	 * @return
	 */
	public BigDecimal getBigDecimal() {
		BigDecimal expValue2 = null;
		BigDecimal fraction2 = null;

		switch (numberType) {
		case Nan:
			return null;
		case Infinite: {
			return null;
		}
		case SubNormalized:
			// ����SubNormal����ֵ
			expValue2 = new BigDecimal("2").pow(config.MaxExp - 1);
			expValue2 = new BigDecimal("1").divide(expValue2);
			fraction2 = BigDecimal.valueOf(fraction).divide(
					new BigDecimal(2).pow(config.FractionBitCount));
			// Attetion
			break;
		case Normalized:
			if (specialValue == FloatNumberSpecialValue.Zero) {
				if (negative)
					return BigDecimal.valueOf(-0d);
				else
					return BigDecimal.valueOf(0d);
			}
			expValue2 = new BigDecimal("2").pow(Math.abs(normalizedExp));
			if (normalizedExp < 0)
				expValue2 = new BigDecimal("1").divide(expValue2);

			fraction2 = BigDecimal.valueOf(fraction)
					.divide(new BigDecimal(2).pow(config.FractionBitCount))
					.add(new BigDecimal(1));
			// Attetion
			break;
		}

		BigDecimal r = fraction2.multiply(expValue2);
		if (negative)
			r = r.multiply(new BigDecimal(-1));
		return r;
	}

	public boolean isNan() {
		return numberType == FloatNumberType.Nan;
	}

	public boolean isNegativeInfinite() {
		return numberType == FloatNumberType.Infinite && negative;
	}

	public boolean isPositiveInfinite() {
		return numberType == FloatNumberType.Infinite && !negative;
	}

	/**
	 * ��ȡһ�����ֵ��С��double
	 * 
	 * @param pair
	 * @return
	 */
	public StructuredFloat absSmaller() {
		switch (numberType) {
		case Nan:
		case Infinite:
			return null;
		case SubNormalized: {
			if (specialValue == FloatNumberSpecialValue.MinSubNormal) {
				return new StructuredFloat(config).buildByType(negative,
						FloatNumberType.Normalized,
						FloatNumberSpecialValue.Zero);
			} else {
				return new StructuredFloat(config).buildByParts(negative,
						normalizedExp, fraction - 1);
			}
		}
		case Normalized: {
			if (specialValue == FloatNumberSpecialValue.Zero)
				return null;
			else if (fraction != 0) {
				return new StructuredFloat(config).buildByParts(negative,
						normalizedExp, fraction - 1);
			} else if (specialValue == FloatNumberSpecialValue.MinNormal) {
				return new StructuredFloat(config).buildByType(negative,
						FloatNumberType.SubNormalized,
						FloatNumberSpecialValue.MaxSubNormal);
			} else {
				return new StructuredFloat(config).buildByParts(negative,
						normalizedExp - 1, config.MaxFraction);
			}
		}
		}
		return null;

	}

	/**
	 * ��ȡһ�����ֵ����double
	 * 
	 * @param pair
	 * @return
	 */
	public StructuredFloat absBigger() {
		switch (numberType) {
		case Nan:
		case Infinite:
			return null;
		case SubNormalized: {
			if (specialValue == FloatNumberSpecialValue.MaxSubNormal) {
				return new StructuredFloat(config).buildByType(negative,
						FloatNumberType.Normalized,
						FloatNumberSpecialValue.MinNormal);
			} else {
				return new StructuredFloat(config).buildByParts(negative,
						normalizedExp, fraction + 1);
			}
		}
		case Normalized: {
			if (specialValue == FloatNumberSpecialValue.MaxValue)
				return null;
			else if (fraction == config.MaxFraction) { // maximum
				return new StructuredFloat(config).buildByParts(negative,
						normalizedExp + 1, 0);

			} else {// ������0�����Ƿ�0��normalized number������������ȷ��
				return new StructuredFloat(config).buildByParts(negative,
						normalizedExp, fraction + 1);
			}
		}
		}
		return null;

	}

	@Override
	public String toString() {
		if (isNan()) {
			return "Nan";
		} else if (isPositiveInfinite()) {
			return "PositiveInfinite";
		} else if (isNegativeInfinite()) {
			return "NegativeInfinite";
		} else {
			BigDecimal r = this.getBigDecimal();
			if (r.equals(0)) {
				if (negative)
					return "-0";
				else
					return "0";
			} else
				return r.toString();
		}
	}

}
