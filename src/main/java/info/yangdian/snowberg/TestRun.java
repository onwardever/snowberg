package info.yangdian.snowberg;

public class TestRun
{
    public static void main(String[] args) throws InterruptedException
    {
        Server server = new Server(9200);
        server.addModule(new SimpleModule());
        server.start();
    }
}
