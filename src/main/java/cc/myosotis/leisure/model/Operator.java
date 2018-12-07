package cc.myosotis.leisure.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public abstract class Operator {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @JsonIgnore
    private String editor;
    @JsonIgnore
    private String creator;
    @JsonIgnore
    private Date editTime;
    @JsonIgnore
    private Date createTime;

    public Operator() {
    }

    public Operator(String creator) {
        create(creator);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void create(String who) {
        Date date = new Date();
        setCreateTime(date);
        setEditTime(date);
        setCreator(who);
        setEditor(who);
    }

    public void update(String who) {
        setEditTime(new Date());
        setEditor(who);
    }
}
