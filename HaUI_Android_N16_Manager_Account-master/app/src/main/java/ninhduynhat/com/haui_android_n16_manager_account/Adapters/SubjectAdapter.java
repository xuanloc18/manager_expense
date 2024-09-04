package ninhduynhat.com.haui_android_n16_manager_account.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import ninhduynhat.com.haui_android_n16_manager_account.Model.SubjectObject;
import ninhduynhat.com.haui_android_n16_manager_account.R;

//public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
//
//    private List<SubjectObject> subjects;
//    private Set<Integer> selectedSubjects;
//    private Context context;
//    public SubjectAdapter(List<SubjectObject> subjects, Context context) {
//        this.subjects = subjects;
//        this.selectedSubjects = new HashSet<>();
//        this.context = context;
//    }
//    public void clearSelection() {
//        selectedSubjects.clear();
//        notifyDataSetChanged();
//    }
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public CardView cardView;
//        public TextView description;
//        public TextView credit;
//        public TextView amount;
//
//        public ViewHolder(View view) {
//            super(view);
//            cardView = view.findViewById(R.id.cardSubject);
//            description = view.findViewById(R.id.nameSubject);
//            credit = view.findViewById(R.id.tvcredit);
//            amount = view.findViewById(R.id.subject_amount);
//        }
//    }
//    @Override
//    public SubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_subject, parent, false);
//        return new ViewHolder(view);
//    }
//    @Override
//    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        SubjectObject subject = subjects.get(position);
//        int semester = subject.getSemester();
//        // Giả sử bạn có TextView trong ViewHolder để hiển thị thông tin
////        holder.description.setText(subject.getSubjectName());
////        holder.credit.setText(String.format("Số tín chỉ: %d", subject.getStudyCredits()));
////        holder.amount.setText(String.format("Số tiền: %.2f", subject.getAmount()));
//
//        holder.description.setText(subject.getSubjectName());
//        holder.credit.setText(String.format("Số tín chỉ: %d", subject.getStudyCredits()));
//
//        // Sử dụng NumberFormat để định dạng số tiền
//        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
//        String formattedAmount = numberFormat.format(subject.getAmount());
//        holder.amount.setText(String.format("Số tiền: %s", formattedAmount));
//
//        // Sử dụng context để truy cập tài nguyên
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (selectedSubjects.contains(position)) {
//                    selectedSubjects.remove(position);
//                    holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.white));
//                } else {
//                    selectedSubjects.add(position);
//                    holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.xanhla));
//                }
//            }
//        });
//        holder.itemView.setBackgroundColor(selectedSubjects.contains(position) ?
//                context.getResources().getColor(R.color.xanhla) :
//                context.getResources().getColor(android.R.color.white));
//    }
//    @Override
//    public int getItemCount() {
//        return subjects.size();
//    }
//    public Set<Integer> getSelectedPositions() {
//        return selectedSubjects;
//    }
//
//    public Set<Integer> setSelectedPositions(Set<Integer> selectedPositions) {
//        this.selectedSubjects = selectedPositions;
//        return selectedSubjects;
//    }
//}
public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private List<SubjectObject> subjects;
    private Map<Integer, Set<Integer>> selectedSubjectsBySemester; // Map to store selected positions by semester
    private Context context;
    private Map<Integer, Set<Integer>> selectedPositionsBySemester;


    public SubjectAdapter(List<SubjectObject> subjects, Context context) {
        this.subjects = subjects;
        this.context = context;
        this.selectedPositionsBySemester = new HashMap<>(); // Khởi tạo selectedPositionsBySemester
    }


    public void setSelectedSubjectsForSemester(int semester, Set<Integer> selectedPositions) {
        selectedSubjectsBySemester.put(semester, selectedPositions);
        notifyDataSetChanged(); // Notify adapter that dataset has changed
    }

    public Set<Integer> getSelectedPositionsForSemester(int semester) {
        if (selectedPositionsBySemester == null) {
            return new HashSet<>();
        }
        return selectedPositionsBySemester.getOrDefault(semester, new HashSet<>());
    }


    public void clearSelectionForSemester(int semester) {
        if (selectedSubjectsBySemester.containsKey(semester)) {
            selectedSubjectsBySemester.get(semester).clear();
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView description;
        public TextView credit;
        public TextView amount;

        public ViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.cardSubject);
            description = view.findViewById(R.id.nameSubject);
            credit = view.findViewById(R.id.tvcredit);
            amount = view.findViewById(R.id.subject_amount);
        }
    }

    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subject, parent, false);
        return new ViewHolder(view);
    }
    public void clearAllSelections() {
        if (selectedPositionsBySemester != null) {
            selectedPositionsBySemester.clear();
            notifyDataSetChanged();
        }
    }
    public void setSelectedPositionsForSemester(int semester, Set<Integer> selectedPositions) {
        if (selectedPositionsBySemester == null) {
            selectedPositionsBySemester = new HashMap<>();
        }
        selectedPositionsBySemester.put(semester, selectedPositions);
        notifyDataSetChanged();
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SubjectObject subject = subjects.get(position);


        holder.description.setText(subject.getSubjectName());
        holder.credit.setText(String.format("Số tín chỉ: %d", subject.getStudyCredits()));

        // Sử dụng NumberFormat để định dạng số tiền
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedAmount = numberFormat.format(subject.getAmount());
        holder.amount.setText(String.format("Số tiền: %s", formattedAmount));

        holder.itemView.setOnClickListener(v -> {
            int semester = subject.getSemester();
            Set<Integer> selectedPositions = selectedPositionsBySemester.get(semester);
            if (selectedPositions == null) {
                selectedPositions = new HashSet<>();
                selectedPositionsBySemester.put(semester, selectedPositions);
            }

            if (selectedPositions.contains(position)) {
                selectedPositions.remove(position);
                holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.white));
            } else {
                selectedPositions.add(position);
                holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.xanhla));
            }
        });
        int semester = subject.getSemester();
        Set<Integer> selectedPositions = selectedPositionsBySemester.get(semester);
        holder.itemView.setBackgroundColor(selectedPositions != null && selectedPositions.contains(position) ?
                ContextCompat.getColor(context, R.color.xanhla) :
                ContextCompat.getColor(context, R.color.white));
    }




    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
