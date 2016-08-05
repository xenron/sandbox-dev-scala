package chap04;

import java.util.List;

import com.google.common.collect.Lists;

public class Driver {

	public static void main(String[] args) {
		List<Thumbnail> imageIconsList = Lists.newArrayList();

		imageIconsList.add(new Thumbnail("/images/first.jpg"));
		imageIconsList.add(new Thumbnail("/images/second.jpg"));
		imageIconsList.add(new Thumbnail("/images/third.jpg"));
		
		for (Thumbnail thumbnail : imageIconsList) {
			thumbnail.renderImage();
		}
		System.out.println();
		for (Thumbnail thumbnail : imageIconsList) {
			thumbnail.renderImage();
		}

	}

}
