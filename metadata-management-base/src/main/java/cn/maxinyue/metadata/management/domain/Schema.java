package cn.maxinyue.metadata.management.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by obama on 2017/4/24.
 */
@XmlType
public class Schema {

    private String name;

    private String domain;

    private String version;

    private List<Element> elements = new ArrayList<>();

    private Properties properties = new Properties();

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @XmlElement
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @XmlElement
    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        return "Schema{" +
                "name='" + name + '\'' +
                ", domain='" + domain + '\'' +
                ", version='" + version + '\'' +
                ", elements=" + elements +
                ", properties=" + properties +
                '}';
    }

    @XmlElement
    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

}
