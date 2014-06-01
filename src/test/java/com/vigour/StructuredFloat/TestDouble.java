package com.vigour.StructuredFloat;

import static com.vigour.StructuredFloat.StructedFloatBuilder.buildDouble;
import static com.vigour.StructuredFloat.StructedFloatBuilder.buildDoubleByType;
import static org.junit.Assert.assertTrue;

public class TestDouble {


	@org.junit.Test
	public void test_buildByType() {
		{
			StructuredFloat sd = buildDoubleByType(false, FloatNumberType.Nan,
					null);
			assertTrue(sd.isNan() && sd.getBigDecimal() == null);
		}

		{
			StructuredFloat sd = buildDoubleByType(true, FloatNumberType.Nan,
					null);
			assertTrue(sd.isNan() && sd.getBigDecimal() == null);
		}

		{
			StructuredFloat sd = buildDoubleByType(false,
					FloatNumberType.Infinite, null);
			assertTrue(sd.isPositiveInfinite() && sd.getBigDecimal() == null);
		}

		{
			StructuredFloat sd = buildDoubleByType(true,
					FloatNumberType.Infinite, null);
			assertTrue(sd.isNegativeInfinite() && sd.getBigDecimal() == null);
		}

		{
			StructuredFloat sd = buildDoubleByType(false,
					FloatNumberType.Normalized,
					FloatNumberSpecialValue.MaxValue);
			assertTrue(sd.getBigDecimal().doubleValue() == (Double.MAX_VALUE));
		}
		{
			StructuredFloat sd = buildDoubleByType(false,
					FloatNumberType.Normalized,
					FloatNumberSpecialValue.MinNormal);
			assertTrue(sd.getBigDecimal().doubleValue() == (Double.MIN_NORMAL));
		}
		{
			StructuredFloat sd = buildDoubleByType(false,
					FloatNumberType.SubNormalized,
					FloatNumberSpecialValue.MinSubNormal);
			assertTrue(sd.getBigDecimal().doubleValue() == Double.MIN_VALUE);
		}
	}

	@org.junit.Test
	public void test_buildByDouble() {
		{
			StructuredFloat sd = buildDouble(Double.MAX_VALUE);
			assertTrue(sd.getBigDecimal().doubleValue() == Double.MAX_VALUE);
		}
		{
			StructuredFloat sd = buildDouble(Double.MIN_NORMAL);
			assertTrue(sd.getBigDecimal().doubleValue() == Double.MIN_NORMAL);
		}
		{

			StructuredFloat sd = buildDouble(Double.MIN_VALUE);
			assertTrue(sd.getBigDecimal().doubleValue() == Double.MIN_VALUE);
		}
		{
			StructuredFloat sd = buildDouble(Double.NEGATIVE_INFINITY);
			assertTrue(sd.getBigDecimal() == null && sd.isNegativeInfinite());
		}
		{
			StructuredFloat sd = buildDouble(Double.POSITIVE_INFINITY);
			assertTrue(sd.getBigDecimal() == null && sd.isPositiveInfinite());
		}

		{
			StructuredFloat sd = buildDouble(0d);
			assertTrue(sd.getBigDecimal().doubleValue() == 0d);
			assertTrue(sd.getBigDecimal().doubleValue() == -0d);
		}

		{
			StructuredFloat sd = buildDouble(-0d);
			assertTrue(sd.getBigDecimal().doubleValue() == 0d);
			assertTrue(sd.getBigDecimal().doubleValue() == -0d);
		}

		{
			StructuredFloat sd = buildDouble(1.0d);
			assertTrue(sd.getBigDecimal().doubleValue() == 1.0d);
		}
		{
			StructuredFloat sd = buildDouble(-1.0d);
			assertTrue(sd.getBigDecimal().doubleValue() == -1.0d);
		}
		{
			StructuredFloat sd = buildDouble(2.3d);
			assertTrue(sd.getBigDecimal().doubleValue() == 2.3d);
		}
		{
			StructuredFloat sd = buildDouble(-2.3d);
			assertTrue(sd.getBigDecimal().doubleValue() == -2.3d);
		}
		{
			StructuredFloat sd = buildDouble(1e308d);
			assertTrue(sd.getBigDecimal().doubleValue() == 1e308d);
		}
		{
			StructuredFloat sd = buildDouble(-1e308d);
			assertTrue(sd.getBigDecimal().doubleValue() == -1e308d);
		}
	}

	@org.junit.Test
	public void test_absBigger() {
		{
			{
				StructuredFloat sd = buildDouble(0d);
				assertTrue(sd.absBigger().getBigDecimal().doubleValue() == (Double.MIN_VALUE));
			}
			{
				StructuredFloat sd = buildDoubleByType(false,
						FloatNumberType.SubNormalized,
						FloatNumberSpecialValue.MaxSubNormal);
				assertTrue(sd.absBigger().getBigDecimal().doubleValue() == Double.MIN_NORMAL);
			}
			{
				StructuredFloat sd = buildDoubleByType(false,
						FloatNumberType.Infinite, null);
				assertTrue(sd.absBigger() == null);
			}
		}
	}

	@org.junit.Test
	public void test_absSmaller() {
		{
			{
				StructuredFloat sd = buildDouble(0d);
				assertTrue(sd.absSmaller() == null);
			}
			{
				StructuredFloat sd = buildDoubleByType(false,
						FloatNumberType.SubNormalized,
						FloatNumberSpecialValue.MinSubNormal);
				assertTrue(sd.absSmaller().getBigDecimal().doubleValue() == 0);
			}
			{
				StructuredFloat sd = buildDoubleByType(false,
						FloatNumberType.Normalized,
						FloatNumberSpecialValue.MinNormal);
				assertTrue(sd.absSmaller().getBigDecimal().doubleValue() < Double.MIN_NORMAL);
			}
		}
	}
}
