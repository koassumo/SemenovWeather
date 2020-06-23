package ru.geekbrains.android2.semenovweather.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.geekbrains.android2.semenovweather.R;

public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.ViewHolder> {
    private List<String> data;
//    private Context context;
    private Fragment fragment;
    private int menuPosition;
    private int selectedPosition = 0;

    public RecyclerDataAdapter(List<String> data, Fragment fragment) {
        this.data = data;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view_item, parent, false);
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
    // endregion

    // region Изменение списка
    // Добавить элемент в список
    public void addItem(String element){
        data.add(element);
        notifyItemInserted(data.size()-1);
    }

    // Заменить элемент в списке
    public void updateItem(String element, int position){
        data.set(position, element);
        notifyItemChanged(position);
    }

    // Удалить элемент из списка
    public void removeItem(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }

    // Очистить список
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
            textElement = itemView.findViewById(R.id.textStudentFirstName);
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
