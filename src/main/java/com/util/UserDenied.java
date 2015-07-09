package com.util;

import java.util.Map;

import com.wms.model.User;

/**
 * @author   Sabo
 * @created  Jul 9, 2015
 **/

public class UserDenied {
    
    /*
     * @users: userDao.findDeniedMapIdAndName(user)
     * @role : 当前用户角色
     */
    public static String getUserIds(Map<Long, String> users, int role) {
        
        String usersIds = null;
        if (role == User.ROLE_ADMIN || role == User.ROLE_BOSS ) {
            usersIds = "";
        } else {
            usersIds = users.keySet().toString().replace("[", "(").replace("]", ")");
        }
        
        return usersIds;
        
    }

}
