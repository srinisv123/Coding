import java.io.File;
import java.text.DecimalFormat;

/**
 * @author ssingan on 7/8/15.
 */
class SizeComputeWorker extends Worker {

    public void doWork(File file) {
        System.out.println("The size of file " + file.getAbsolutePath() + " is: " + fileSizeString(file.length()));
    }

    /**
     * Pretty print the file sizes
     */
    private String fileSizeString(long size) {
        float floatSize = (float) size;
        if (size / 1000000000 > 0) {
            return new DecimalFormat("#.##").format(floatSize / 1000000000) + " GB";
        } else if (size / 1000000 > 0) {
            return new DecimalFormat("#.##").format(floatSize / 1000000) + " MB";
        } else if (size / 1000 > 0) {
            return new DecimalFormat("#.##").format(floatSize / 1000) + " KB";
        } else {
            return size + " bytes";
        }
    }
}
