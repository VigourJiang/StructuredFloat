package com.vigour.StructuredFloat;
import static com.vigour.StructuredFloat.StructedFloatBuilder.buildDouble;
import static com.vigour.StructuredFloat.StructedFloatBuilder.buildFloat;

public class App {

	public static void main(String[] args) {

		System.out.println("****************Double***************");
		printBiggerSmaller(0d);
		printBiggerSmaller(Double.MIN_VALUE);
		printBiggerSmaller(Double.MIN_NORMAL);
		printBiggerSmaller(Double.MAX_VALUE);
		printBiggerSmaller(0.1d);
		printBiggerSmaller(1.0d);
		printBiggerSmaller(10.0d);
		printBiggerSmaller(1e14d);
		printBiggerSmaller(1e15d);
		printBiggerSmaller(1e16d);
		printBiggerSmaller(1e17d);
		printBiggerSmaller(1e304d);

		System.out.println("****************Float***************");
		printBiggerSmaller(0f);
		printBiggerSmaller(Float.MIN_VALUE);
		printBiggerSmaller(Float.MIN_NORMAL);
		printBiggerSmaller(Float.MAX_VALUE);
		printBiggerSmaller(0.1f);
		printBiggerSmaller(1.0f);
		printBiggerSmaller(10.0f);
		printBiggerSmaller(1e5f);
		printBiggerSmaller(1e6F);
		printBiggerSmaller(1e7f);
		printBiggerSmaller(1e8f);
		printBiggerSmaller(1e38f);

		float f1 = 1e8f;
		float f2 = f1 + 1f - f1;
		System.out.println(f2);
	}

	private static void printBiggerSmaller(double cur) {
		System.out.println("------->");

		StructuredFloat sd = buildDouble(cur);
		StructuredFloat smaller = sd.absSmaller();
		StructuredFloat bigger = sd.absBigger();

		System.out.print("A: ");
		System.out.println(smaller == null ? "null" : smaller);
		System.out.print("B: ");
		System.out.println(sd);
		System.out.print("C: ");
		System.out.println(bigger == null ? "null" : bigger);
	}

	private static void printBiggerSmaller(float cur) {
		System.out.println("------->");

		StructuredFloat sd = buildFloat(cur);
		StructuredFloat smaller = sd.absSmaller();
		StructuredFloat bigger = sd.absBigger();

		System.out.print("A: ");
		System.out.println(smaller == null ? "null" : smaller);
		System.out.print("B: ");
		System.out.println(sd);
		System.out.print("C: ");
		System.out.println(bigger == null ? "null" : bigger);
	}

}
