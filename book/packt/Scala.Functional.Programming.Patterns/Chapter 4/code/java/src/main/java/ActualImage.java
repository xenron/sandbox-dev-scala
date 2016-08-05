package chap04;

public class ActualImage implements Image {

	private final String pathToImageFile;

	public ActualImage(final String pathToImageFile) {
		super();
		this.pathToImageFile = pathToImageFile;
	}

	@Override
	public void renderImage() {
		System.out.println("Rendering image <" + pathToImageFile + ">");
	}

	@Override
	public void load() {		
		System.out.println("Loading image from file <" + pathToImageFile + ">");
	}

}
