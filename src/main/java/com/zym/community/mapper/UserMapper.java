package com.zym.community.mapper;

import com.zym.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author
 * @date 2020/3/15-9:24
 */
@Mapper
public interface UserMapper {

    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
