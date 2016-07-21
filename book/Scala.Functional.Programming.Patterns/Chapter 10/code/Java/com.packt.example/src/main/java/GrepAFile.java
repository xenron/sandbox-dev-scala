import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.io.FileUtils;

public class GrepAFile implements Runnable {
	private final String pat;
	private final BlockingQueue<File> inputQueue;
	
	public GrepAFile(final String pat, final BlockingQueue<File> inputQueue) {
		super();
		this.pat = pat;
		this.inputQueue = inputQueue;
	}

	public void run() {
		while(true) {
			try {
				final File file = inputQueue.take();
				if (itsAPoisonPill(file)) {
					return;
				}
				final List<String> lines = FileUtils.readLines(file, "UTF-8");
				for (final String line: lines) {
					if (line.contains(pat)) {
						System.out.println(file.getName() + " : " + line);
					}
				}
			} catch (final InterruptedException e) {
				e.printStackTrace();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean itsAPoisonPill(final File file) {
		return file.getName().equals("last.txt");
	}

}
