package com.giuseppeliguori.today;

import com.giuseppeliguori.todayapi.apiclass.Birth;
import com.giuseppeliguori.todayapi.apiclass.Data;
import com.giuseppeliguori.todayapi.apiclass.Death;
import com.giuseppeliguori.todayapi.apiclass.Event;
import com.giuseppeliguori.todayapi.apiclass.Yearable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by giuseppeliguori on 24/07/2017.
 */

public class RecycleViewPresenter implements RecycleViewContract.Presenter {

    private Data data;
    private List<Birth> births = new ArrayList<>();
    private List<Death> deaths = new ArrayList<>();
    private List<Event> events = new ArrayList<>();
    private List<Object> datas = new ArrayList<>();

    public RecycleViewPresenter(Data data) {
        this.data = data;
        births = data.getBirths();
        deaths = data.getDeaths();
        events = data.getEvents();

        addListToList(births, datas);
        addListToList(deaths, datas);
        addListToList(events, datas);

        sortList();
    }

    private void sortList() {
        Collections.sort(datas, new Comparator<Object>() {
            @Override
            public int compare(Object yearable1, Object yearable2) {
                Integer year1 = Integer.parseInt(((Yearable)yearable1).getYear());
                Integer year2 = Integer.parseInt(((Yearable)yearable2).getYear());
                return Integer.valueOf(year2.compareTo(year1));
            }
        });
    }

    private void addListToList(List<?> listToAdd, List<Object> listResult) {
        for (Object object : listToAdd) {
            listResult.add(object);
        }
    }

    @Override
    public int getSize() {
        return datas.size();
    }

    @Override
    public void onBindRepositoryRowViewAtPosition(int position, ViewHolder holder) {
        Object item = datas.get(position);
        if (item instanceof Birth) {
            holder.setItem(item, ViewHolder.Type.BIRTH);
        } else if (item instanceof Death) {
            holder.setItem(item, ViewHolder.Type.DEATH);
        } else if (item instanceof Event) {
            holder.setItem(item, ViewHolder.Type.EVENT);
        }
    }
}
