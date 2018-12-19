package info.yangdian.snowberg;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.LinkedHashMap;
import java.util.Map;

public class Registry
{
    public static final Registry INSTANCE = new Registry();

    private Registry()
    {

    }

    private Map<String, Controller> map = new LinkedHashMap<>();

    public void register(String path, Controller controller)
    {
        map.put(path, controller);
    }

    public Controller find(HttpRequest request)
    {
        return map.get(new QueryStringDecoder(request.uri()).path());
    }
}
