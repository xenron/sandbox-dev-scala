import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class GrepRecursive {
	private final String pattern;
	private final String directory;
	private final BlockingQueue<File> requestQueue;
	
	public GrepRecursive(final String pattern, final String directory) {
		super();
		this.pattern = pattern;
		this.directory = directory;
		this.requestQueue = new LinkedBlockingDeque<File>();
		new LinkedBlockingDeque<String>();
	}

	private void grep() throws InterruptedException {
		final FilesFinder ff = new FilesFinder(directory, requestQueue);
		final GrepAFile gaf = new GrepAFile(pattern, requestQueue);
		final Thread producer = new Thread(ff);
		final Thread consumer = new Thread(gaf);

		consumer.start();
		producer.start();

		producer.join();
		consumer.join();
	}

	public static void main(final String[] args) throws InterruptedException {
		final GrepRecursive gr = new GrepRecursive("this", "../../data");
		gr.grep();
	}

}
