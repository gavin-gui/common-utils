package com.easycms.common.util;
/**
 * 自定义结果类
 * 如果需要返回更多的结果，请继承这个类
 * @author jiepeng
 *
 * @param <CustomMessage> 自定义消息类
 * @param <T>	由开发者提供类型，根据返回值不同而不同。
 */
public class TwoResult<A,B>{
	private final A one;
	private final B two;
	public TwoResult(A one,B two){
		this.one = one;
		this.two = two;
	}
	public A getOne() {
		return one;
	}
	public B getTwo() {
		return two;
	}
}
