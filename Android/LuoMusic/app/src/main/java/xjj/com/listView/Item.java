package xjj.com.listView;

/**
 * Created by XJJ on 2018/12/10.
 */

public class Item {
    private String itemName;
    private int imgId;

    public Item(String itemName, int imgId) {
        this.itemName = itemName;
        this.imgId = imgId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
