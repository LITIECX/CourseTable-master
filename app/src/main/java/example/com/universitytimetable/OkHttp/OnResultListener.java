/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package example.com.universitytimetable.OkHttp;


public interface OnResultListener<T> {
    void onResult(T result);

    void onError(Exception error);
}
