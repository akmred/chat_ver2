package server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleAuthService implements AuthService {

    private BasesUsers userData = new BasesUsers();

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {

        String nick = userData.CheckingLoginPassword(login, password);
        if (!nick.isEmpty()) {
            return  nick;
        }

        return null;
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        if (userData.CheckingLogin(login)){
                return false;
            }

        try {
            userData.addUser(nickname, login, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
