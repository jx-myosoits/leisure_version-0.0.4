package cc.myosotis.leisure.model;

import javax.persistence.Entity;

@Entity
public class Msg extends Operator {

    /**
     * 文本格式 hell word
     * 资源格式 \(资源连接)
     * 表情格式 \[笑脸]
     */
    private String context;

    // 收件者
    private String toUser;

    // 发件者 = this.creator
    private String byUser;

    // 未读 默认true
    private Boolean unread = true;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getByUser() {
        return byUser;
    }

    public void setByUser(String byUser) {
        this.byUser = byUser;
    }

    public Boolean getUnread() {
        return unread;
    }

    public void setUnread(Boolean unread) {
        this.unread = unread;
    }
}
