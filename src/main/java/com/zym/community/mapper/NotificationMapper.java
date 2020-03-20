package com.zym.community.mapper;

import com.zym.community.model.Notification;
import com.zym.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author
 * @date 2020/3/19-21:28
 */
@Mapper
@Component
public interface NotificationMapper {

    @Insert("insert into notification(notifier,receiver,type,gmt_create,status,outer_id) values(#{notifier},#{receiver},#{type},#{gmtCreate},#{status},#{outerId})")
    void insert(Notification notification);

    //通过userId对应到notification的receiver,拿到所有的通知
    @Select("select * from notification where receiver = #{id} and status = 0")
    List<Notification> selectAllNotification(User user);
    //将已读通知设置为1
    @Update("update notification set status = 1 where id = #{id}")
    void setRead(User user);


}
