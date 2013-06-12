package pl.tls.repo;

import javax.ejb.Stateless;
import pl.tls.entity.Model;

@Stateless
public class ModelRepo extends AbstractRepo<Model> {

    public ModelRepo() {
        super(Model.class);
    }
}
