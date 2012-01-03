package li.klass.fhem.domain;

import li.klass.fhem.AndFHEMApplication;
import li.klass.fhem.R;
import li.klass.fhem.util.StringEscapeUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class Device<T extends Device> implements Serializable, Comparable<T> {

    protected String room = AndFHEMApplication.getContext().getResources().getString(R.string.defaultRoomName);

    protected String name;
    protected String state;
    protected String alias;
    protected String measured;

    protected volatile FileLog fileLog;

    public void loadXML(Node xml) {
        NodeList childNodes = xml.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item == null || item.getAttributes() == null) continue;

            Node keyAttribute = item.getAttributes().getNamedItem("key");
            if (keyAttribute == null) continue;

            String keyValue = keyAttribute.getTextContent().toUpperCase().trim();
            String nodeContent = item.getAttributes().getNamedItem("value").getTextContent().trim();
            nodeContent = StringEscapeUtils.unescapeHtml(nodeContent);

            if (nodeContent.length() == 0) {
                continue;
            }

            if (keyValue.equals("ROOM")) {
                room = nodeContent;
            } else if (keyValue.equals("NAME")) {
                name = nodeContent;
            } else if (state == null && keyValue.equals("STATE")) {
                state = nodeContent;
            } else if (keyValue.equals("ALIAS")) {
                alias = nodeContent;
            } else if (keyValue.equals("CUL_TIME")) {
                measured = nodeContent;
            }

            String tagName = item.getNodeName().toUpperCase();
            onChildItemRead(tagName, keyValue, nodeContent, item.getAttributes());
        }

        NamedNodeMap attributes = xml.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attributeNode = attributes.item(i);
            onAttributeRead(attributeNode.getNodeName().toUpperCase(), attributeNode.getTextContent());
        }
    }

    public String getAliasOrName() {
        if (alias != null) {
            return alias;
        }
        return getName();
    }

    public String getName() {
        return name;
    }

    public String getRoom() {
        return room;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasured() {
        return measured;
    }

    protected abstract void onChildItemRead(String tagName, String keyValue, String nodeContent, NamedNodeMap attributes);
    
    protected void onAttributeRead(String attributeKey, String attributeValue) {
    }

    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", room_list_name='" + room + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public boolean equalsAny(String key, String... values) {
        for (String value : values) {
            if (key.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        return !(name != null ? !name.equals(device.name) : device.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public int compareTo(T t) {
        return getName().compareTo(t.getName());
    }

    public FileLog getFileLog() {
        return fileLog;
    }

    public void setFileLog(FileLog fileLog) {
        this.fileLog = fileLog;
    }

    public Map<Integer, String> getFileLogColumns() {
        return new HashMap<Integer, String> ();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
