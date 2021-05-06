/**
 * FileName: Entity
 * Author:   linwd
 * Date:     2021/5/5 14:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yangxf.demoBatch.entity;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author linwd
 * @create 2021/5/5
 * @since 1.0.0
 */
public class User {

    private Integer id;

    private String username;

    private String sex;

    private String addr;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
