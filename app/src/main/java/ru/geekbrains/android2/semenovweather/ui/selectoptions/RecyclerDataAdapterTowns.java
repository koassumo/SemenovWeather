package ru.geekbrains.android2.semenovweather.ui.selectoptions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.geekbrains.android2.semenovweather.R;

public class RecyclerDataAdapterTowns extends RecyclerView.Adapter<RecyclerDataAdapterTowns.ViewHolder> {
    private List<String> data;
//    private Context context;
    private Fragment fragment;
    private int menuPosition;
    private int selectedPosition = 0;

    public RecyclerDataAdapterTowns(List<String> historyList, Fragment fragment) {
        this.data = historyList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view_towns_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
//        setText(holder, position);
//        setOnItemClickBehavior(holder, position);
//        highlightSelectedPosition(holder, position);

        // Заполнение элементов холдера
        TextView textElement = holder.getTextElement();
        textElement.setText(data.get(position));

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
        return data == null ? 0 : data.size();
    }

    public void addItem(String element){
        data.add(element);
        notifyItemInserted(data.size()-1);
    }

    public String readItem(int position){
        return data.get(position);
        //notifyItemChanged(position);
    }

    public void updateItem(String element, int position){
        data.set(position, element);
        notifyItemChanged(position);
    }

    public void removeItem(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void clearItems(){
        data.clear();
        notifyDataSetChanged();
    }
    // endregion

    public int getMenuPosition() {
        return menuPosition;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textElement;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textElement = itemView.findViewById(R.id.textForecastDate);
        }
        public TextView getTextElement() {
            return textElement;
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
