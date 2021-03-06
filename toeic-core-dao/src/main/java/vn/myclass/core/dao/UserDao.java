package vn.myclass.core.dao;

import vn.myclass.core.data.dao.GenericDao;
import vn.myclass.core.persistence.enity.UserEntity;


public interface UserDao extends GenericDao<Integer, UserEntity> {
//    UserEntity isUserExist(String name, String password);
//    UserEntity findRoleByUser(String name, String password);
    UserEntity findUserByUsernameAndPassword(String name, String password);
}
