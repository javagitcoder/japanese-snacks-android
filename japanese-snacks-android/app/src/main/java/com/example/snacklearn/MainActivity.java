package com.example.snacklearn;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.snacklearn.adapter.SnackAdapter;
import com.example.snacklearn.config.AppConfig;
import com.example.snacklearn.db.DatabaseHelper;
import com.example.snacklearn.db.Snack;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SnackAdapter adapter;
    private DatabaseHelper dbHelper;
    private Button btnPrev, btnNext;
    private TextView tvPageInfo;

    private int currentPage = 1;
    private int totalPages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initDatabase();
        setupRecyclerView();
        loadPageData(currentPage);
        updatePageInfo();

        setupPagination();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        btnPrev = findViewById(R.id.btn_prev);
        btnNext = findViewById(R.id.btn_next);
        tvPageInfo = findViewById(R.id.tv_page_info);
    }

    private void initDatabase() {
        dbHelper = new DatabaseHelper(this);
        try {
            dbHelper.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 计算总页数
        int totalCount = dbHelper.getTotalCount();
        int pageSize = AppConfig.getInstance(this).getPageSize();
        totalPages = (int) Math.ceil((double) totalCount / pageSize);
    }

    private void setupRecyclerView() {
        adapter = new SnackAdapter(this, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadPageData(int page) {
        List<Snack> snacks = dbHelper.getSnacks(page);
        adapter.updateData(snacks);
    }

    private void setupPagination() {
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage > 1) {
                    currentPage--;
                    loadPageData(currentPage);
                    updatePageInfo();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage < totalPages) {
                    currentPage++;
                    loadPageData(currentPage);
                    updatePageInfo();
                }
            }
        });
    }

    private void updatePageInfo() {
        tvPageInfo.setText(String.format("第%d页/共%d页", currentPage, totalPages));

        // 更新按钮状态
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}