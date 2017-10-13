package com.giuseppeliguori.today;

/**
 * Created by giuseppeliguori on 24/07/2017.
 */

public interface RecycleViewContract {
    interface View {
        void setItem(Object item, ViewHolder.Type type);
    }

    interface Presenter {
        int getSize();
        void onBindRepositoryRowViewAtPosition(int position, ViewHolder holder);
    }
}
