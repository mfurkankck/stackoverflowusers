
package com.example.sofuser.Model;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Root {

    @SerializedName("has_more")
    private Boolean mHasMore;
    @SerializedName("items")
    private List<User> mUsers;
    @SerializedName("quota_max")
    private Long mQuotaMax;
    @SerializedName("quota_remaining")
    private Long mQuotaRemaining;

    public Boolean getHasMore() {
        return mHasMore;
    }

    public void setHasMore(Boolean hasMore) {
        mHasMore = hasMore;
    }

    public List<User> getItems() {
        return mUsers;
    }

    public void setItems(List<User> users) {
        mUsers = users;
    }

    public Long getQuotaMax() {
        return mQuotaMax;
    }

    public void setQuotaMax(Long quotaMax) {
        mQuotaMax = quotaMax;
    }

    public Long getQuotaRemaining() {
        return mQuotaRemaining;
    }

    public void setQuotaRemaining(Long quotaRemaining) {
        mQuotaRemaining = quotaRemaining;
    }

}
