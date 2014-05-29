package info.airsen.classloader.calculator;

import info.airsen.classloader.Calculate;

/**
 * <p>TODO</p>
 *
 * @author 主公 (jason61719@gmail.com)
 * @since 14-5-28 下午6:09
 */
public class Add implements Calculate {

	@Override
	public Integer calculate(Integer... numbers) {
		return numbers[0] + numbers[1];
	}
}
