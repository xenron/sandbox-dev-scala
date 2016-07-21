package chap04;

public class Thumbnail implements Image {

	private final String pathToImage;
	private final ActualImage actualImage;
	private boolean thumbNailLoaded;

	public Thumbnail(final String pathToImage) {
		super();
		this.pathToImage = pathToImage;
		this.thumbNailLoaded = false;
		this.actualImage = new ActualImage(pathToImage);
	}

	@Override
	public void renderImage() {
		if (!thumbNailLoaded) {
			load();
			thumbNailLoaded = true;
			System.out.println("Render the thumb nail");
		} else {
			actualImage.load();
			actualImage.renderImage();
		}
	}

	@Override
	public void load() {
		System.out.println("Loading Thumb Nail <" + pathToImage + ">");
	}

}
