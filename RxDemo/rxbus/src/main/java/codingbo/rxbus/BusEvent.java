package codingbo.rxbus;

/**
 * Created by bob
 * on 16.11.19.
 */

public class BusEvent {

    private int type;
    private String  content;

    public BusEvent(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
