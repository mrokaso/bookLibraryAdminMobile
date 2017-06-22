package admin.mobile.rsi.adminmobile.api.models;


import java.util.Date;

public class SearchAuthorCriteria {

    private String nameLike;
    private Date birthdateFrom;
    private Date birthdateTo;

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public Date getBirthdateFrom() {
        return birthdateFrom;
    }

    public void setBirthdateFrom(Date birthdateFrom) {
        this.birthdateFrom = birthdateFrom;
    }

    public Date getBirthdateTo() {
        return birthdateTo;
    }

    public void setBirthdateTo(Date birthdateTo) {
        this.birthdateTo = birthdateTo;
    }
}
