package com.ankita.emicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ankita.emicalculator.modal.EmiEntitty;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText edtLoanAmount,edtRateOfInterest,edtNoOfInstallment,edtLockPeriod,edtStartDate,edtEndDate;
    private Button btnSubmit,btnClear,btnTimePeriod,btnShowEmiCal;
    private Spinner spinnerDateFormate;
    private String[] arrayString={"yyyy-MM-dd","MM/DD/YY","dd/mm/yyyy"};
    private int endMonth,endYear,endDate;
    private Double emi;
    double roi;
    private TextInputLayout tilLoanAmount,tilRateOfInterest,tilNoOfInstallment,tilPrePayment;
    private int item=0;
    private EmiEntitty emiEntitty;
    SimpleDateFormat sdf;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    public static ArrayList<EmiEntitty> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
         myCalendar = Calendar.getInstance();
        edtLockPeriod.setText("6");
        btnSubmit.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnTimePeriod.setOnClickListener(this);
        btnShowEmiCal.setOnClickListener(this);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
                setEndDate(year,monthOfYear,dayOfMonth);
            }

        };


    }

    private void setEndDate(int year, int monthOfYear, int dayOfMonth) {
        monthOfYear+=1;
        endYear=year;
        endDate=dayOfMonth;
        endMonth=monthOfYear+Integer.parseInt(edtNoOfInstallment.getText().toString());
        if(endMonth>12){
            endMonth=(monthOfYear+Integer.parseInt(edtNoOfInstallment.getText().toString()))%12;
            endYear=year+1;
        }

        String Date=endMonth+"/"+endDate+"/"+endYear;
        edtEndDate.setText(Date);
        setDataIntoArrayList();
    }

    private void setDataIntoArrayList() {
        sdf = new SimpleDateFormat("MM/DD/YY", Locale.US);


        for(int i=0;i<Integer.parseInt(edtNoOfInstallment.getText().toString());i++){
            double interest=roi*Integer.parseInt(edtLoanAmount.getText().toString());
            emiEntitty=new EmiEntitty();
            emiEntitty.setInstNo(i+1);
            emiEntitty.setLoanAmount(Double.parseDouble(edtLoanAmount.getText().toString()));
            emiEntitty.setDate(sdf.format(myCalendar.getTime()));
            emiEntitty.setEmi(emi);
            emiEntitty.setInterest(interest);
            emiEntitty.setPrinciple(emi-interest);
            arrayList.add(emiEntitty);
        }
    }

    private void updateLabel() {
        String myFormat;
        if(item==1){
             myFormat = "MM/dd/yy";
        }
        else if(item==0) {
            myFormat = "yyyy-MM-dd";
        }
        else {
            myFormat="dd/mm/yyyy";
        }

       sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtStartDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void init() {
        tilLoanAmount=findViewById(R.id.tilLoanAmount);
        tilRateOfInterest=findViewById(R.id.tilRateOfInterest);
        tilNoOfInstallment=findViewById(R.id.tilNoOfInstallment);
        tilPrePayment=findViewById(R.id.tilPrePayment);
        edtLoanAmount=findViewById(R.id.edtLoanAmount);
        edtRateOfInterest=findViewById(R.id.edtRateOfInterest);
        edtNoOfInstallment=findViewById(R.id.edtNoOfInstallment);
        edtLockPeriod=findViewById(R.id.edtPrePayment);
        btnSubmit=findViewById(R.id.btnSubmit);
        btnClear=findViewById(R.id.btnClear);
        btnTimePeriod=findViewById(R.id.btnTimePeriod);
        arrayList=new ArrayList<>();
        btnShowEmiCal=findViewById(R.id.btnShowEMICalculation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmit:
                if(validate()){
                    btnTimePeriod.setVisibility(View.VISIBLE);
                    roi= rateOfInterestInMonth(Double.parseDouble(edtRateOfInterest.getText().toString()));
                    emi=calEMI(Integer.parseInt(edtLoanAmount.getText().toString()),roi,Double.parseDouble(edtNoOfInstallment.getText().toString()));
                }
                break;
            case R.id.btnClear:
                clearEditText();
                break;
            case R.id.btnTimePeriod:
                showAlert();
                break;
            case R.id.btnShowEMICalculation:
                startActivity(new Intent(MainActivity.this,ShowEMICalculation.class));
                break;
        }
    }

    private void showAlert() {
        LayoutInflater layoutInflater=getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.custome_dialog,null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(view);
        AlertDialog dialog=alert.create();
        spinnerDateFormate=view.findViewById(R.id.spinnerSelectDateFormate);
        edtStartDate=view.findViewById(R.id.edtStartDate);
        edtEndDate=view.findViewById(R.id.edtEndDate);
        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        setSpinnerAdapter();
        dialog.show();
    }

    private void setSpinnerAdapter() {
        //create array adapter
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayString);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerDateFormate.setAdapter(dataAdapter);
        spinnerDateFormate.setOnItemSelectedListener(this);

    }

    private void clearEditText() {
        edtLoanAmount.setText("");
        edtRateOfInterest.setText("");
        edtNoOfInstallment.setText("");
        edtLockPeriod.setText("6");
    }

    private boolean validate() {
        if(edtLoanAmount.getText().toString().isEmpty()){
            tilLoanAmount.setError("this field is required");
            return false;
        }
        else if(edtRateOfInterest.getText().toString().isEmpty()){
            tilLoanAmount.setError(null);
            tilRateOfInterest.setError("this field is required");
            return false;
        }
        else if(edtNoOfInstallment.getText().toString().isEmpty()){
            tilLoanAmount.setError(null);
            tilRateOfInterest.setError(null);
            tilNoOfInstallment.setError("this field is required");
            return false;
        }
        else if(edtLockPeriod.getText().toString().isEmpty()){
            tilLoanAmount.setError(null);
            tilRateOfInterest.setError(null);
            tilNoOfInstallment.setError(null);
            tilPrePayment.setError("this field is required");
            return false;
        }
        else {
            tilLoanAmount.setError(null);
            tilRateOfInterest.setError(null);
            tilNoOfInstallment.setError(null);
            tilPrePayment.setError(null);
            return true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item =position;

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private double calEMI(int p,double r,double n){
        double res=Math.pow((1+r),n);
        double emi=p*r*res/res-1;
        return emi;
    }
    private double rateOfInterestInMonth(double r){
        return r/(12*100);
    }
}
