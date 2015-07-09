import java.io.File;


/**
 * The Crawler has a root directory and a Dispatcher object to dispatch files. This implements Runnable and can be run
 * as a separate thread to crawl the file system
 *
 * @author ssingan on 7/7/15.
 */
public class Crawler implements Runnable {

    File entryPointPath;
    Dispatcher dispatcher;
    public Crawler(String entryPointPathName, Dispatcher dispatcher) {

        this.entryPointPath = new File(entryPointPathName);
        this.dispatcher = dispatcher;
    }

    /**
     * Entry point for the crawler thread. Starts crawling from the entryPoint
     */
    public void run() {
        crawlDirectory(entryPointPath);
        dispatcher.completeCrawl();
    }


    /**
     * Does the actual crawling. Detects if a file is a directory and recursively crawls the directory. For files, it
     * dispatches them to the dispatcher to process them
     *
     * @param startingFile The starting point for the current crawl
     */
    public void crawlDirectory(File startingFile) {
        if (!startingFile.exists()) {
            System.out.println("The given entry point does not exist in file system");
            return;
        }
        if (startingFile.isDirectory()) {
            File[] filesInDirectory = startingFile.listFiles();

            if (filesInDirectory == null) {
                return;
            }

            for (File file : filesInDirectory) {
                crawlDirectory(file);
            }
        } else {
            System.out.println("Crawling " + startingFile);
            dispatcher.dispatch(startingFile);

        }
    }
}
