package com.example.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    var lastNumeric = false
    var lastDot = false

    lateinit var inputTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    //this function gets called each time any digit button is pressed
    //it takes the text of the button and appends it to the textview
    fun onDigit(view: View){

    inputTextView = findViewById(R.id.input_text_view)
        inputTextView.append((view as Button).text)

        lastNumeric = true
    }
    //this function clears the entire textview
    fun clear(view: View){
        inputTextView = findViewById(R.id.input_text_view)
        inputTextView.text = ""
         lastNumeric = false
         lastDot = false
    }
    //this function deletes the last added digit or operator
    fun deleteLastDigit(view: View){
        inputTextView = findViewById(R.id.input_text_view)
        inputTextView.text = inputTextView.text.dropLast(1)

    }
    //this function adds decimal point
    fun onDecimalPoint(view: View){

        if (lastNumeric && !lastDot){
            inputTextView = findViewById(R.id.input_text_view)
            inputTextView.append(".")

            lastNumeric = false
            lastDot = false
        }
    }
    //this function adds any of the 4 maths operators when pressed
    fun onOperator(view: View){
        inputTextView = findViewById(R.id.input_text_view)
        if(lastNumeric && !isOperatorAdded(inputTextView.text.toString())){
            inputTextView.append((view as Button).text)
            lastNumeric = false
            lastDot =  false
        }
    }
    //this function does the mathematical calculation when the = button is pressed
    fun onEqual(view: View){
        if (lastNumeric){
            inputTextView = findViewById(R.id.input_text_view)
            var textViewValue = inputTextView.text.toString()
            var prefix = ""

            try {
                    if (textViewValue.startsWith("-")){
                        prefix = "-"
                        textViewValue = textViewValue.substring(1)
                    }

                    if(textViewValue.contains("-")){
                        val splitValue = textViewValue.split("-")

                        var left = splitValue[0]
                        var right = splitValue[1]

                        if (prefix.isNotEmpty()){
                            left = prefix + left
                        }

                        var result = removeZeroAfterDot((left.toDouble() - right.toDouble()).toString())
                        inputTextView.text = result.toString()

                    }else if (textViewValue.contains("*")){
                        val splitValue = textViewValue.split("*")

                        var left = splitValue[0]
                        var right = splitValue[1]

                        if (prefix.isNotEmpty()){
                            left = prefix + left
                        }

                        var result = removeZeroAfterDot((left.toDouble() * right.toDouble()).toString())
                        inputTextView.text = result.toString()

                    }else if(textViewValue.contains("+")){
                        val splitValue = textViewValue.split("+")

                        var left = splitValue[0]
                        var right = splitValue[1]

                        if (prefix.isNotEmpty()){
                            left = prefix + left
                        }

                        var result = removeZeroAfterDot((left.toDouble() + right.toDouble()).toString())
                        inputTextView.text = result.toString()

                    }else{


                            val splitValue = textViewValue.split("/")

                            var left = splitValue[0]
                            var right = splitValue[1]

                            if (prefix.isNotEmpty()){
                                left = prefix + left
                            }

                            var result = removeZeroAfterDot((left.toDouble() / right.toDouble()).toString())
                            inputTextView.text = result.toString()

                        }




            }catch (e: ArithmeticException){
                e.printStackTrace()
            }

        }

    }
    //checks when an operator is added so it wont be added again
    private fun isOperatorAdded(value: String): Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("+") || value.contains("*") || value.contains("-")
        }
    }
   // this function removes the last zero decimal in case of something like 12.0, to have 12
    private fun removeZeroAfterDot(result: String): String{
        var value = result
        if (result.contains(".0")){
            value = result.substring(0, result.length - 2)
        }

        return value
    }
}