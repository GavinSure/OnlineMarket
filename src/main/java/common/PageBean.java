package common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageBean<T> {
    private static final long serialVersionUID = 1014601105346011758L;
    private long count;//总记录数
    private int page;//当前页
    private int size;//每页展示数据量
    private long totalPage;//总页数
    private List<T> list;//当前页要展示的数据
}
