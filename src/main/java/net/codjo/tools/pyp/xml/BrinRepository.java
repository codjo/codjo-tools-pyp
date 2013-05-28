package net.codjo.tools.pyp.xml;
import java.util.Collections;
import java.util.List;
import net.codjo.tools.pyp.model.Brin;
/**
 *
 */
@Deprecated
public class BrinRepository {
    private List<Brin> repository;


    @Deprecated
    public List<Brin> getRepository() {
        return Collections.unmodifiableList(repository);
    }


    @Deprecated
    public void setRepository(List<Brin> repository) {
        this.repository = repository;
    }
}
