package net.codjo.tools.pyp.pages;
import java.io.Serializable;
/**
 *
 */
public interface CallBack<T> extends Serializable {
    void onClickCallBack(T onclickParameter);


    public String getLabel();


    public String getImagePath();
}
