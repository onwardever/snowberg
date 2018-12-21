package info.yangdian.snowberg;

import java.util.Map;

public interface Paths<T extends Describable>
{
    void add(String path, T t);

    T get(String path, Map<String, String> parameters);
}
