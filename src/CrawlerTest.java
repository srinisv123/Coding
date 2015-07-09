import java.io.File;
import java.util.LinkedList;



/**
 * A Test for the Crawler to ensure that it crawls a given file system correctly
 * @author ssingan on 7/8/15.
 */
public class CrawlerTest {

    public static void main(String[] args) {

        try {

            // Crawling empty directory
            MockDispatcher dispatcher = new MockDispatcher();
            Crawler emptyCrawler = new Crawler("./emptyDirectory", dispatcher);
            emptyCrawler.run();
            assertEquals(new LinkedList<File>(), dispatcher.getQueue(), "Crawling on empty directory");
            assertEquals(true, dispatcher.getDispatchStatus(), "Dispatcher Complete Status");

            // Crawling non empty directory
            LinkedList<String> expected = new LinkedList<String>();
            expected.add("testFile1.txt");
            expected.add("testFile2.txt");

            MockDispatcher nonEmptyDispatcher = new MockDispatcher();
            Crawler nonEmptyCrawler = new Crawler("../directoryWithFiles", nonEmptyDispatcher);
            nonEmptyCrawler.run();

            assertEquals(expected, nonEmptyDispatcher.getQueue(), "Crawling on a non empty directory");
            assertEquals(true, nonEmptyDispatcher.getDispatchStatus(), "Dispatcher Complete Status");

            //Crawling non existing directory
            MockDispatcher nonExistantDispatcher = new MockDispatcher();
            Crawler nonExistantCrawler = new Crawler("foo", nonExistantDispatcher);
            nonExistantCrawler.run();
            assertEquals(new LinkedList<File>(), nonExistantDispatcher.getQueue(), "Crawling on non-existant directory");
            assertEquals(true, nonExistantDispatcher.getDispatchStatus(), "Dispatcher non-existant Status");



        } catch (Exception e) {
            System.out.print(" error, message: " + e.getMessage());
        }
    }

    public static void assertEquals(Object expected, Object actual, String message) {
        if (expected.equals(actual)) {
            System.out.println("OK: " + message);
        } else {
            System.out.println("FAIL: " + message);
        }
    }


}


class MockDispatcher extends Dispatcher {

    MockDispatcher () {
        super();
    }


    public LinkedList<String> getQueue() {
        LinkedList<String> fileNames = new LinkedList<String>();
        for(File file : queue) {
            fileNames.add(file.getName());
        }
        return fileNames;
    }

    public boolean getDispatchStatus() {
        return complete;
    }
}
