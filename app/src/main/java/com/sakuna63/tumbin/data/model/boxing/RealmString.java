package com.sakuna63.tumbin.data.model.boxing;

import io.realm.RealmObject;

public class RealmString extends RealmObject {
    public String val;

    public RealmString() {
    }

    public RealmString(String val) {
        this.val = val;
    }
}
