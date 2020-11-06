package unsw.gloriaromanus.Model;

import org.json.JSONObject;

public interface Factory {
    public void initialise();
    public Object deserialise(JSONObject obj);
}
