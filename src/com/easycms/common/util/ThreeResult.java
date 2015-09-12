package com.easycms.common.util;
/**
 * 自定义结果类
 * 如果需要返回更多的结果，请继承这个类
 * @author jiepeng
 *
 * @param <CustomMessage> 自定义消息类
 * @param <T>	由开发者提供类型，根据返回值不同而不同。
 */
public class ThreeResult<A,B,C> extends TwoResult<A, B>{
	private final C three;
	public ThreeResult(A one,B two,C three){
		super(one, two);
		this.three = three;
	}
	public C getThree() {
		return this.three;
	}
}
