package vn.myclass.web.controller.command;

import vn.myclass.core.dto.UserDTO;
import vn.myclass.core.web.command.AbstractCommand;

public class UserCommand extends AbstractCommand<UserDTO> {
    private String confirmPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public UserCommand()
    {
        this.pojo = new UserDTO();
    }
}
