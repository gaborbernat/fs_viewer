/**
 * Created with IntelliJ IDEA.
 * User: gabor.bernat
 * Date: 4/22/12
 * Time: 7:14 PM
 * To change this template use File | Settings | File Templates.
 */
package com.primeranks.net.fs_data;

public class User {
    /**
     * The username.
     */
    private String userName;
    /**
     * The domain of the user.
     */
    private String domain;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
