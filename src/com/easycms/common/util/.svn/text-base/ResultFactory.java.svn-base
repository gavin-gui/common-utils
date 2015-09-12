package com.easycms.common.util;
/**
 * 自定义结果类
 * 如果需要返回更多的结果，请继承这个类
 * @author jiepeng
 *
 * @param <CustomMessage> 自定义消息类
 * @param <T>	由开发者提供类型，根据返回值不同而不同。
 */
public class ResultFactory {
	/**
	 * 创建一个大小为2的结果
	 * @param <A>
	 * @param <B>
	 * @param one
	 * @param two
	 * @return
	 */
	public static <A,B> TwoResult<A,B> createMultiResult(A one,B two) {
		return new TwoResult<A, B>(one, two);
	}
	/**
	 * 创建一个大小为3的结果
	 * @param <A>
	 * @param <B>
	 * @param one
	 * @param two
	 * @return
	 */
	public static <A,B,C> ThreeResult<A,B,C> createMultiResult(A one,B two,C three) {
		return new ThreeResult<A, B,C>(one, two,three);
	}
}
