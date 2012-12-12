package net.codjo.tools.pyp.pages;

public abstract class AbstractCallBack<T> implements CallBack<T> {
    private String label;
    private String imagePath;


    protected AbstractCallBack(String label, String imagePath) {
        this.label = label;
        this.imagePath = imagePath;
    }


    public String getLabel() {
        return label;
    }


    public String getImagePath() {
        return imagePath;
    }
}