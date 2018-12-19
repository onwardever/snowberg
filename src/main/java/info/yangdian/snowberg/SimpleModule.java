package info.yangdian.snowberg;

public class SimpleModule extends Module
{
    @Override
    public void register(Registry registry)
    {
        registry.register("/hello", new SimpleController());
    }
}
