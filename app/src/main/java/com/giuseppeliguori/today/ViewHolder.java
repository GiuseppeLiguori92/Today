package com.giuseppeliguori.today;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.giuseppeliguori.todayapi.apiclass.Textable;
import com.giuseppeliguori.todayapi.apiclass.Yearable;

/**
 * Created by giuseppeliguori on 24/07/2017.
 */

public class ViewHolder extends RecyclerView.ViewHolder implements RecycleViewContract.View {

    public enum Type {
        BIRTH,
        DEATH,
        EVENT
    }

    private TextView textViewYear;
    private TextView textViewText;
    private TextView textViewEmojy;

    public ViewHolder(LinearLayout linearLayout) {
        super(linearLayout);
        textViewYear = linearLayout.findViewById(R.id.year);
        textViewText = linearLayout.findViewById(R.id.text);
        textViewEmojy = linearLayout.findViewById(R.id.textView_emojy);
    }

    @Override
    public void setItem(Object item, Type type) {
        if (item instanceof Yearable) {
            textViewYear.setText(((Yearable)item).getYear());
        }

        if (item instanceof Textable) {
            textViewText.setText(((Textable)item).getText());
        }

        String emojy;
        switch (type) {
            case BIRTH:
                emojy = new String(Character.toChars(0x1F476));
                break;
            case DEATH:
                emojy = new String(Character.toChars(0x1F480));
                break;
            case EVENT:
                emojy = new String(Character.toChars(0x1F4C5));
                break;
            default:
                emojy = "";
                break;
        }

        textViewEmojy.setText(emojy);

    }
}

