package com.example.base.log;

import android.text.TextUtils;

public enum LogLevel {
    ON(0, "on"),
    V(1, "v"),
    D(2, "d"),
    I(3, "i"),
    W(4, "w"),
    E(5, "e"),
    OFF(6, "off");

    private int level;
    private String name;

    private LogLevel(int level, String name) {
        this.level = level;
        this.name = name;
    }

    public int getValue() {
        return this.level;
    }

    public String getName() {
        return this.name;
    }

    public int compare(LogLevel level) {
        if (this.level > level.level) {
            return 1;
        } else {
            return this.level == level.level ? 0 : -1;
        }
    }

    public static LogLevel valueOf(String name, LogLevel def) {
        if (TextUtils.isEmpty(name)) {
            return def;
        } else {
            LogLevel[] values = values();
            LogLevel[] var3 = values;
            int var4 = values.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                LogLevel level = var3[var5];
                if (name.equalsIgnoreCase(level.name)) {
                    return level;
                }
            }

            return def;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("LogLevel{");
        sb.append("level=").append(this.level);
        sb.append(", name='").append(this.name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
