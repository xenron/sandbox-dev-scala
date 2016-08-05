import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.io.DirectoryWalker;

public class FilesFinder extends DirectoryWalker implements Runnable {

	private final String directory;
	private final BlockingQueue<File> inputQueue;

	public FilesFinder(final String directory, final BlockingQueue<File> inputQueue) {
		this.directory = directory;
		this.inputQueue = inputQueue;
	}

	@Override
	protected void handleFile(final File file, final int depth, final Collection results) throws IOException {
		try {
			inputQueue.put(file);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		final File startDir = new File(directory);
		try {
			walk(startDir, null);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}
