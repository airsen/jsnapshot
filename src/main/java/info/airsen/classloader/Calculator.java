package info.airsen.classloader;

/**
 * @author 主公 (jason61719@gmail.com)
 * @since 14-5-28 下午6:11
 */
public class Calculator implements Calculate {

	@Override
	public Integer calculate(Integer... numbers) {
		return numbers[0] + numbers[1];
	}
}