package decorator.domain.classe.mount;

import decorator.domain.classe.*;

public class Horse extends Mount {

    public Horse(String nickname) {
        super(nickname);

        Tanking = 10;
    }
    
}