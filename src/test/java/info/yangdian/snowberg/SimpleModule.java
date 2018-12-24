package info.yangdian.snowberg;

public class SimpleModule extends Module
{
    @Override
    public void register(Paths<Controller> paths)
    {
        paths.add("/hello", new SimpleController());
    }
}
