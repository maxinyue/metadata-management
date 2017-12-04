package cn.maxinyue.metadata.management.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.sql.JDBCType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by obama on 2017/4/24.
 */
@XmlType
public class Element {

    private String name;

    private JDBCType type;

    private Map<String, Object> limitations = new HashMap<>();

    private int length;

    private String comment;

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public JDBCType getType() {
        return type;
    }

    public void setType(JDBCType type) {
        this.type = type;
    }

    @XmlElement
    public Map<String, Object> getLimitations() {
        return limitations;
    }

    public void setLimitations(Map<String, Object> limitations) {
        this.limitations = limitations;
    }

    @XmlElement
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @XmlElement
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Element{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", limitations=" + limitations +
                ", length=" + length +
                ", comment='" + comment + '\'' +
                '}';
    }
}
