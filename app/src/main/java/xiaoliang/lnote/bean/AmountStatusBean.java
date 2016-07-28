package xiaoliang.lnote.bean;

/**
 * Created by LiuJ on 2016/6/18.
 * 金额类型
 */
public class AmountStatusBean {
    public int id;
    public int color;
    public String name;

    public AmountStatusBean(int color, int id, String name) {
        this.color = color;
        this.id = id;
        this.name = name;
    }

    public AmountStatusBean() {
        this.color = 0;
        this.id = 0;
        this.name = "";
    }
}
