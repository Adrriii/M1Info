package decorator.domain.entities.classe;

import decorator.domain.entities.*;

public class Infantryman extends UnitSimple {

    public Infantryman(String nickname) {
        super(nickname);
    }

    public void addSword() throws ImpossibleExtensionException {
    }

    public void addShield() throws ImpossibleExtensionException {
    }

}