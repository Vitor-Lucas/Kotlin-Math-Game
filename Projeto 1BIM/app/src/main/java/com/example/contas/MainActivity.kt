package com.example.contas

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var vibrator: Vibrator

    private lateinit var num_esq: TextInputEditText
    private lateinit var num_dir: TextInputEditText
    private lateinit var num_res: TextInputEditText

    private lateinit var Button_limpar: Button
    private lateinit var Button_verificar: Button
    private lateinit var Button_aleatorio: Button
    private lateinit var Button_change_operation: Button

    private lateinit var Text_acerto: TextView
    private lateinit var Text_erro: TextView
    private lateinit var Text_Operation: TextView

    private lateinit var operation:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        num_dir = findViewById(R.id.textInputEditNum_dir)
        num_esq = findViewById(R.id.textInputEditNum_esq)
        num_res = findViewById(R.id.textInputEdit_Resposta)

        Button_aleatorio = findViewById(R.id.button_aleatorio)
        Button_limpar = findViewById(R.id.button_limpar)
        Button_verificar = findViewById(R.id.button_verificar)
        Button_change_operation = findViewById(R.id.Button_change_operation)

        Text_acerto = findViewById(R.id.text_acertos)
        Text_erro = findViewById(R.id.text_erros)
        Text_Operation = findViewById(R.id.textOperation)

        Button_aleatorio.setOnClickListener { randomize_values() }
        Button_limpar.setOnClickListener { clear_texts() }
        Button_verificar.setOnClickListener { verify_result() }
        Button_change_operation.setOnClickListener { change_operation() }

        randomize_values()
    }
    private fun clear_texts(){
        num_res.setText("")
        num_esq.setText("0")
        num_dir.setText("0")
    }
    private fun update_operation_text(){
        if(operation == "sum")
            Text_Operation.text = "+"
        else
            Text_Operation.text = "-"
    }
    private fun change_operation(){
        if(operation == "sum")
            operation = "subtraction"
        else
            operation = "sum"
        update_operation_text()
    }
    private fun verify_result(){
        if(num_esq.text.toString().isBlank() || num_dir.text.toString().isBlank() || num_res.text.toString().isBlank())
            return

        val a:Int = num_esq.text.toString().toInt()
        val b:Int = num_dir.text.toString().toInt()
        val resp:Int = num_res.text.toString().toInt()

        var condition:Boolean = (a+b == resp)
        if(operation == "subtraction")
            condition = (a - b == resp)

        if(condition){
            vibrate(2000)
            val num:Int = Text_acerto.text.toString().toInt() + 1
            Text_acerto.setText(num.toString())
        }
        else{
            vibrate(1000)
            val num:Int = Text_erro.text.toString().toInt() + 1
            Text_erro.setText(num.toString())
        }

        num_res.setText("")
        randomize_values()
    }
    private fun randomize_values(){
        //Pega um numero aleatório entre 0 e 100
        num_esq.setText((0..100).random().toString())
        num_dir.setText((0..100).random().toString())

        val num:Int = (1..2).random()
        if(num == 1)
            operation = "sum"
        else
            operation = "subtraction"
        update_operation_text()
    }
    private fun vibrate(durationMillis: Long) {
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                vibrator.vibrate(VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE))
            else
                vibrator.vibrate(durationMillis)
        }
    }
}