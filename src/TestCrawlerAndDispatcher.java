

/**
 * @author ssingan on 7/8/15.
 */
public class TestCrawlerAndDispatcher {

    public static final String STARTING_FILE_NAME="/";

    public static void main(String[] args) {


        SizeComputeWorker worker = new SizeComputeWorker();
        Worker[] workers = new Worker[1];
        workers[0] = worker;
        Dispatcher dispatcher = new Dispatcher(workers);
        Thread dispatcherThread = new Thread(dispatcher);
        Crawler crawler = new Crawler(STARTING_FILE_NAME, dispatcher);
        Thread crawlerThread = new Thread(crawler);
        dispatcherThread.start();
        crawlerThread.start();

    }

}
