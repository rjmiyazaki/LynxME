package br.com.lynx.control.misc;

public final class OpcaoMenu {
	private int id;
	private int imageIndex;
	private String caption;
	
	public OpcaoMenu(int id, int imageIndex, String caption){
		this.id = id;
		this.imageIndex = imageIndex;
		this.caption = caption;
	}
	
	public int getId(){
		return this.id;
	}
	
	public int getImageIndex(){
		return this.imageIndex;
	}
	
	public String getCaption(){
		return caption;
	}
}
