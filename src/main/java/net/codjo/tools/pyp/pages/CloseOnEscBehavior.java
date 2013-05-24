package net.codjo.tools.pyp.pages;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * TODO[codjo-web] copied from Magic
 */
public class CloseOnEscBehavior extends AbstractDefaultAjaxBehavior {
    private String elementToObserve;
    private String eventName;
    private ModalWindow modalWindow;
    private ModalWindow.CloseButtonCallback callback;


    public CloseOnEscBehavior(ModalWindow addApplicationWindow,
                              ModalWindow.CloseButtonCallback callback,
                              String elementToObserve,
                              String eventName) {
        this.modalWindow = addApplicationWindow;
        this.callback = callback;
        this.elementToObserve = elementToObserve;
        this.eventName = eventName;
    }


    @Override
    protected void respond(AjaxRequestTarget target) {
        if (modalWindow.isShown()) {
            modalWindow.close(target);
            callback.onCloseButtonClicked(target);
        }
    }


    @Override
    public void renderHead(IHeaderResponse response) {
        String javascript =
              "$(" + elementToObserve + ").observe('" + eventName + "', function(e){\n"
              + "  if (e.keyCode == Event.KEY_ESC){"
              + getCallbackScript()
              + "}"
              + "});";
        response.renderOnDomReadyJavascript(javascript);
        super.renderHead(response);
    }
}