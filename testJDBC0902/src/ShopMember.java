import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor   // 매개변수 없는 기본 생성자
@AllArgsConstructor  // 매개변수 전부 다 가지고 있는 생성자
@ToString            // 필드 정보 하나의 문자열로 반환
public class ShopMember {

	// 필드
	private String memeberId;
	private String memberPw;
	private String phone;
	private String gender;
	
	
	
}
