package uit.huyphambuinhat.lab02;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

public class CalculateSalaryForm extends Activity {

    EditText full_name;
    EditText gross_salary;
    Button calculateBtt;
    ListView net_salary;
    ArrayList<employee> List;
    ArrayAdapter<employee> ListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_salary_form);

        full_name = (EditText) findViewById(R.id.full_name);
        gross_salary  = (EditText) findViewById(R.id.gross_salary);
        net_salary = (ListView) findViewById(R.id.net_salary);
        calculateBtt = (Button) findViewById(R.id.calculateBtt);

        List = new ArrayList<>();
        ListAdapter = new ArrayAdapter<employee>(this, android.R.layout.simple_expandable_list_item_1, List);
        net_salary.setAdapter(ListAdapter);

        calculateBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employee tmp = new employee();
                tmp.set_full_name(full_name.getText().toString());
                tmp.set_gross_salary(Float.parseFloat(gross_salary.getText().toString()));
                List.add(tmp);
                ListAdapter.notifyDataSetChanged();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        net_salary.setAdapter(ListAdapter);
    }
}
