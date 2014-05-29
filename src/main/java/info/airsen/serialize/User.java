package info.airsen.serialize;

import lombok.Data;

/**
 * <p>模型类</p>
 *
 * @author 主公 (jason61719@gmail.com)
 * @since 14-5-28 下午10:02
 */
@Data
public class User {

	private Integer id;
	private String name;
	private Boolean male;
	private String nickname;
	private String desc;
	private String homepage;
	private Float price;
}
