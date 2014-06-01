package com.vigour.StructuredFloat;

/**
 * ��ʾһЩ�����˫������ֵ����Щ��ֵ�ǺϷ������֣�������������ȡ�
 * 
 * @author vigour
 * 
 */
public enum FloatNumberSpecialValue {
	/**
	 * Zero
	 */
	Zero,
	
	/**
	 * Correspond to {@link Double}.MAX_VALUE
	 */
	MaxValue,
	
	/**
	 * Correspond to {@link Double}.MIN_NORMAL
	 */
	MinNormal,
	
	/**
	 * Max Subnormal number, but also smaller than any normal number
	 */
	MaxSubNormal,
	
	/**
	 * Correspond to {@link Double}.MIN_VALUE
	 */
	MinSubNormal,
	
	/**
	 * Not special value
	 */
	NoSpecial
}
