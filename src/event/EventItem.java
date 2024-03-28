package event;


import java.awt.Component;
import model.bean.ModelItem;

public interface EventItem {

    public void itemClick(Component com, ModelItem item);
}
