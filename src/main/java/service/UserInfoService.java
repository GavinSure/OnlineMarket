package service;

import Bean.UserInfo;

public interface UserInfoService {
    UserInfo finduserInfo(String email,String password);
}
