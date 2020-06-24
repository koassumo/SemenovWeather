package ru.geekbrains.android2.semenovweather.ui.home;

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
//    private Context context;
    private Fragment fragment;
    private int menuPosition;
    private int selectedPosition = 0;

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
        //context = parent.getContext();
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

        // Определяем текущую позицию в списке
        textElement.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                menuPosition = position;
                return false;
            }
        });

        // Так регистрируется контекстное меню
        if (fragment != null){
            fragment.registerForContextMenu(textElement);
        }
    }

    @Override
    public int getItemCount() {
        return dataDate == null ? 0 : dataDate.size();
    }
//    // endregion
//
//    // region Изменение списка
//    // Добавить элемент в список
//    public void addItem(String element){
//        data.add(element);
//        notifyItemInserted(data.size()-1);
//    }

    // Заменить элемент в списке
    public void updateItem(String forecastDate, String forecastTime, String forecastSky, String forecastTemp, int position){
        dataDate.set(position, forecastDate);
        dataTime.set(position, forecastTime);
        dataSky.set(position, forecastSky);
        dataTemp.set(position, forecastTemp);
        notifyItemChanged(position);
    }

//    // Удалить элемент из списка
//    public void removeItem(int position){
//        data.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    // Очистить список
//    public void clearItems(){
//        data.clear();
//        notifyDataSetChanged();
//    }
//    // endregion

    public int getMenuPosition() {
        return menuPosition;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textElement;
        private TextView textElementTime;
        private TextView textElementSky;
        private TextView textElementTemp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textElement = itemView.findViewById(R.id.textForecastDate);
            textElementTime = itemView.findViewById(R.id.textForecastTime);
            textElementSky = itemView.findViewById(R.id.textForecastSky);
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



//    private void setText(@NonNull ViewHolder holder, final int position) {
//        holder.listItemTextView.setText(data[position]);
//    }
//
//    private void setOnItemClickBehavior(@NonNull ViewHolder holder, final int position) {
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectedPosition = position;
//                notifyDataSetChanged();
//            }
//        });
//    }
//
//    private void highlightSelectedPosition(@NonNull ViewHolder holder, final int position) {
//        if(position == selectedPosition) {
//            int color = ContextCompat.getColor(context, R.color.colorPrimaryDark);
//            holder.itemView.setBackgroundColor(color);
//        }
//        else {
//            int color = ContextCompat.getColor(context, android.R.color.transparent);
//            holder.itemView.setBackgroundColor(color);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return data == null ? 0 : data.length;
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        TextView listItemTextView;
//        View itemView;
//        ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            this.itemView = itemView;
//            initViews(itemView);
//        }
//
//        private void initViews(View itemView) {
//            listItemTextView = itemView.findViewById(R.id.textStudentFirstName);
//        }
//    }

}
