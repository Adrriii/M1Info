package decorator.domain.entities.classe.mount;

import decorator.domain.entities.classe.*;

public class Horse extends Mount {

    public Horse(String nickname) {
        super(nickname);

        Tanking = 10;
    }
    
}