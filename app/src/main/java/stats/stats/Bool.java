package stats.stats;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by Mike on 1/29/2017.
 */

public class Bool
{
    public Bool(){ _val = false; }
    public Bool(boolean val) { _val = val; }

    public boolean getValue(){ return _val; }
    public void setValue(boolean val){
        _changesupport.firePropertyChange("value",_val,_val=val);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener ){
        _changesupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener){
        _changesupport.removePropertyChangeListener( listener );
    }

    private boolean _val = false;
    private final PropertyChangeSupport _changesupport = new PropertyChangeSupport(this);
}
