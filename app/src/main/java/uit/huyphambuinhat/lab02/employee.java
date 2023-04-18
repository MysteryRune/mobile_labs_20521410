package uit.huyphambuinhat.lab02;
import androidx.annotation.NonNull;

public class employee {
    private String full_name;
    private float gross_salary;

    //set and get for full_name of employee
    public void set_full_name(String text)
    {
        full_name = text;
    }
    public String get_full_name()
    {
        return full_name;
    }

    //setter and getter for gross salary of employee
    public void set_gross_salary(float gross_sal)
    {
        gross_salary = gross_sal;
    }
    public float get_gross_salary()
    {
        return gross_salary;
    }

    //calculate net salary for employee
    public float net_salary() {
        float a = (float) (get_gross_salary() - get_gross_salary() * 0.105);
        if (a <= 11000000.0) {
            return a;
        } else {
            float tax = (float) ((a - 11000000) * 0.05);
            return a - tax;
        }
    }

    @NonNull
    public String toString()
    {
        return "Full name: " + get_full_name() + "\n" + "Net Salary: " + ((float) Math.round(net_salary()));
    }
}
