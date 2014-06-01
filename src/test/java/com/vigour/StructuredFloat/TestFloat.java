package com.vigour.StructuredFloat;

import static com.vigour.StructuredFloat.StructedFloatBuilder.buildFloat;
import static com.vigour.StructuredFloat.StructedFloatBuilder.buildFloatByType;
import static org.junit.Assert.assertTrue;

public class TestFloat {

	@org.junit.Test
	public void test_buildByType() {
		{
			StructuredFloat sd = buildFloatByType(false, FloatNumberType.Nan,
					null);
			assertTrue(sd.isNan() && sd.getBigDecimal() == null);
		}

		{
			StructuredFloat sd = buildFloatByType(true, FloatNumberType.Nan,
					null);
			assertTrue(sd.isNan() && sd.getBigDecimal() == null);
		}

		{
			StructuredFloat sd = buildFloatByType(false,
					FloatNumberType.Infinite, null);
			assertTrue(sd.isPositiveInfinite() && sd.getBigDecimal() == null);
		}

		{
			StructuredFloat sd = buildFloatByType(true,
					FloatNumberType.Infinite, null);
			assertTrue(sd.isNegativeInfinite() && sd.getBigDecimal() == null);
		}

		{
			StructuredFloat sd = buildFloatByType(false,
					FloatNumberType.Normalized,
					FloatNumberSpecialValue.MaxValue);
			assertTrue(sd.getBigDecimal().floatValue() == (Float.MAX_VALUE));
		}
		{
			StructuredFloat sd = buildFloatByType(false,
					FloatNumberType.Normalized,
					FloatNumberSpecialValue.MinNormal);
			assertTrue(sd.getBigDecimal().floatValue() == (Float.MIN_NORMAL));
		}
		{
			StructuredFloat sd = buildFloatByType(false,
					FloatNumberType.SubNormalized,
					FloatNumberSpecialValue.MinSubNormal);
			assertTrue(sd.getBigDecimal().floatValue() == Float.MIN_VALUE);
		}
	}

	@org.junit.Test
	public void test_buildByFloat() {
		{
			StructuredFloat sd = buildFloat(Float.MAX_VALUE);
			assertTrue(sd.getBigDecimal().floatValue() == Float.MAX_VALUE);
		}
		{
			StructuredFloat sd = buildFloat(Float.MIN_NORMAL);
			assertTrue(sd.getBigDecimal().floatValue() == Float.MIN_NORMAL);
		}
		{

			StructuredFloat sd = buildFloat(Float.MIN_VALUE);
			assertTrue(sd.getBigDecimal().floatValue() == Float.MIN_VALUE);
		}
		{
			StructuredFloat sd = buildFloat(Float.NEGATIVE_INFINITY);
			assertTrue(sd.getBigDecimal() == null && sd.isNegativeInfinite());
		}
		{
			StructuredFloat sd = buildFloat(Float.POSITIVE_INFINITY);
			assertTrue(sd.getBigDecimal() == null && sd.isPositiveInfinite());
		}

		{
			StructuredFloat sd = buildFloat(0f);
			assertTrue(sd.getBigDecimal().floatValue() == 0f);
			assertTrue(sd.getBigDecimal().floatValue() == -0f);
		}

		{
			StructuredFloat sd = buildFloat(-0f);
			assertTrue(sd.getBigDecimal().floatValue() == 0f);
			assertTrue(sd.getBigDecimal().floatValue() == -0f);
		}

		{
			StructuredFloat sd = buildFloat(1.0f);
			assertTrue(sd.getBigDecimal().floatValue() == 1.0f);
		}
		{
			StructuredFloat sd = buildFloat(-1.0f);
			assertTrue(sd.getBigDecimal().floatValue() == -1.0f);
		}
		{
			StructuredFloat sd = buildFloat(2.3f);
			assertTrue(sd.getBigDecimal().floatValue() == 2.3f);
		}
		{
			StructuredFloat sd = buildFloat(-2.3f);
			assertTrue(sd.getBigDecimal().floatValue() == -2.3f);
		}
		{
			StructuredFloat sd = buildFloat(1e38f);
			assertTrue(sd.getBigDecimal().floatValue() == 1e38f);
		}
		{
			StructuredFloat sd = buildFloat(-1e38f);
			assertTrue(sd.getBigDecimal().floatValue() == -1e38f);
		}
	}

	@org.junit.Test
	public void test_absBigger() {
		{
			{
				StructuredFloat sd = buildFloat(0f);
				assertTrue(sd.absBigger().getBigDecimal().floatValue() == (Float.MIN_VALUE));
			}
			{
				StructuredFloat sd = buildFloatByType(false,
						FloatNumberType.SubNormalized,
						FloatNumberSpecialValue.MaxSubNormal);
				assertTrue(sd.absBigger().getBigDecimal().floatValue() == Float.MIN_NORMAL);
			}
			{
				StructuredFloat sd = buildFloatByType(false,
						FloatNumberType.Infinite, null);
				assertTrue(sd.absBigger() == null);
			}
		}
	}

	@org.junit.Test
	public void test_absSmaller() {
		{
			{
				StructuredFloat sd = buildFloat(0f);
				assertTrue(sd.absSmaller() == null);
			}
			{
				StructuredFloat sd = new StructuredFloat(
						FloatConfig.DoublePrecision).buildByType(false,
						FloatNumberType.SubNormalized,
						FloatNumberSpecialValue.MinSubNormal);
				assertTrue(sd.absSmaller().getBigDecimal().floatValue() == 0);
			}
			{
				StructuredFloat sd = new StructuredFloat(
						FloatConfig.DoublePrecision).buildByType(false,
						FloatNumberType.Normalized,
						FloatNumberSpecialValue.MinNormal);
				assertTrue(sd.absSmaller().getBigDecimal().floatValue() < Float.MIN_NORMAL);
			}
		}
	}
}
