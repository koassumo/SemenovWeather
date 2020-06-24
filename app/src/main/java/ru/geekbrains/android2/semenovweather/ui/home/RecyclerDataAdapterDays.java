package ru.geekbrains.android2.semenovweather.ui.home;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.geekbrains.android2.semenovweather.R;

public class RecyclerDataAdapterDays extends RecyclerView.Adapter<RecyclerDataAdapterDays.ViewHolder> {
    private List<String> dataDate;
    private List<String> dataTime;
    private List<String> dataSky;
    private List<String> dataTemp;
    private Fragment fragment;
    private int menuPosition;
    private int selectedPosition = 0;
    Typeface weatherFont;

    public RecyclerDataAdapterDays(List<String> dataDate, List<String> dataTime, List<String> dataSky, List<String> dataTemp, Fragment fragment) {
        this.dataDate = dataDate;
        this.dataTime = dataTime;
        this.dataSky = dataSky;
        this.dataTemp = dataTemp;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view_days_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
//        setText(holder, position);
//        setOnItemClickBehavior(holder, position);
//        highlightSelectedPosition(holder, position);

        // Заполнение элементов холдера
        TextView textElement = holder.getTextElement();
        textElement.setText(dataDate.get(position));
        TextView textElementTime = holder.getTextElementTime();
        textElementTime.setText(dataTime.get(position));
        TextView textElementSky = holder.getTextElementSky();
        textElementSky.setText(dataSky.get(position));
        TextView textElementTemp = holder.getTextElementTemp();
        textElementTemp.setText(dataTemp.get(position));

    }

    @Override
    public int getItemCount() {
        return dataDate == null ? 0 : dataDate.size();
    }

    // Заменить элемент в списке
    public void updateItem(String forecastDate, String forecastTime, String forecastSky, String forecastTemp, int position){
        dataDate.set(position, forecastDate);
        dataTime.set(position, forecastTime);
        dataSky.set(position, forecastSky);
        dataTemp.set(position, forecastTemp);
        notifyItemChanged(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textElement;
        private TextView textElementTime;
        private TextView textElementSky;
        private TextView textElementTemp;
        Typeface weatherFont = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/weather.ttf");


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textElement = itemView.findViewById(R.id.textForecastDate);
            textElementTime = itemView.findViewById(R.id.textForecastTime);
            textElementSky = (TextView) itemView.findViewById(R.id.textForecastSky);
            //Typeface weatherFont = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/weather.ttf");
            textElementSky.setTypeface(weatherFont);
            textElementTemp = itemView.findViewById(R.id.textForecastTemperature);
        }
        public TextView getTextElement() {
            return textElement;
        }
        public TextView getTextElementTime() {
            return textElementTime;
        }
        public TextView getTextElementSky() {
            return textElementSky;
        }
        public TextView getTextElementTemp() {
            return textElementTemp;
        }
    }

}
