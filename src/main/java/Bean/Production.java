package Bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Production {

  private long id;
  private String prodName;
  private double prodPrice;
  private String prodImg;
  private String prodDesc;
  private Date createTime;
  private Date updateTime;
  private String timeStr;


}
