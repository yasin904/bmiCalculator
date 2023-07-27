package com.example.bmicalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weightText = findViewById<EditText>(R.id.etWeight)
        val heightText = findViewById<EditText>(R.id.etHeight)

        weightText.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(weightText.windowToken,0)
                true
            }
            else{
                false
            }
        }

        heightText.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(heightText.windowToken,0)
                true
            }else{
                false
            }
        }


        val calcButton = findViewById<Button>(R.id.btn_calculate)


        calcButton.setOnClickListener {
            val weight = weightText.text.toString()
            val height = heightText.text.toString()

            if(validateInput(weight,height)) {

                val bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                // get result with two decimal places
                val bmi2Digits = String.format("%.2f", bmi).toFloat()
                displayResult(bmi2Digits)


            }


        }

    }

    private fun validateInput(weight : String?,height:String?): Boolean {

      return  when{
          weight.isNullOrEmpty() || weight == "0" -> {

              if (weight == "0") {

                  Toast.makeText(this, "Weight cannot be 0", Toast.LENGTH_LONG).show()

              } else {
                  Toast.makeText(this, "Weight is Empty", Toast.LENGTH_LONG).show()

              }
              return false
          }
          height.isNullOrEmpty() || height == "0" -> {


               if(height == "0"){

                  Toast.makeText(this, "Height cannot be 0", Toast.LENGTH_LONG).show()
              }
              else {
                  Toast.makeText(this, "Height is Empty", Toast.LENGTH_LONG).show()

              }
              return false

          }
          else ->{
              return true
          }

        }

    }

    private fun displayResult(bmi : Float){

        val resultIndex = findViewById<TextView>(R.id.tvIndex)
        val resultDescription = findViewById<TextView>(R.id.tvResult)
        val info = findViewById<TextView>(R.id.tvInfo)

        resultIndex.text = bmi.toString()
        info.text = "Normal range is 18.5 - 24.9"

        var resultText = ""
        var color = 0

         //no break statement is needed for when block

        when {
            bmi < 15.0 ->{
                resultText = "Severely Underweight"
                color = R.color.Severely_under_weight
            }

            bmi in 15.00 ..18.50 -> {
                resultText = "Underweight"
                color = R.color.under_weight
            }

            bmi in 18.50..24.99->{
                resultText = "Healthy"
                color = R.color.normal
            }
            bmi in 25.00..29.99->{
                resultText = "Overweight"
                color = R.color.over_weight
            }
            bmi > 29.99->{
                resultText = "Obese"
                color = R.color.obese

            }

        }

        // to give color to the respective description
        resultDescription.setTextColor(ContextCompat.getColor(this,color))

        resultDescription.text = resultText



    }
}