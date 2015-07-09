import java.io.File;
import java.util.LinkedList;

/**
 *
 * The dispatcher holds the queue of files which are crawled by the Crawler. It also has knowledge of all the Workers
 * that will be working on these files. It implements Runnable
 *
 * @author ssingan on 7/8/15.
 */
class Dispatcher  implements Runnable {

    LinkedList<File> queue;
    Worker[] workers;
    boolean complete = false;

    Dispatcher () {
        queue = new LinkedList<File>();
    }

    Dispatcher(Worker[] workers) {
        queue = new LinkedList<File>();
        this.workers = workers;
    }

    /**
     * Indicate that crawling is complete and the thread can exit
     */
    public void completeCrawl() {
        complete = true;
    }

    /**
     * The dispatch function just adds the file to a queue and notifies anyone who is waiting on this queue to have data
     *
     * @param file The file to be added to the queue
     */
    public synchronized void dispatch(File file) {
        queue.add(file);
        notifyAll();
    }

    /**
     * This fetches data from the queue for the Dispatcher to process. It waits if there is no data in the queue
     * @return A file from the queue
     */
    public synchronized File fetch() {
        try {
            while (queue.size() <= 0) {
                wait();
            }
        } catch (InterruptedException intExp) {
            System.out.println("Interrupted");
            return null;
        }
        return queue.removeFirst();
    }

    /**
     * The Dispatcher thread that polls the queue and does work on the files if there are any files
     */
    public void run() {

        while (!complete || queue.size() > 0) {
            File file = fetch();
            for (Worker worker : workers) {
                worker.doWork(file);
            }
        }

    }
}
