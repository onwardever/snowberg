package info.yangdian.snowberg;

public class TestRun
{
    public static void main(String[] args) throws InterruptedException
    {
        Snowberg snowberg = new Snowberg(9200);
        snowberg.addModule(new SimpleModule());
        snowberg.start();
    }
}
