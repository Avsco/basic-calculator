package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
    private var resultValue: BigDecimal = BigDecimal.ZERO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val numberButtons = intArrayOf(
            R.id.button_0,
            R.id.button_1,
            R.id.button_2,
            R.id.button_3,
            R.id.button_4,
            R.id.button_5,
            R.id.button_6,
            R.id.button_7,
            R.id.button_8,
            R.id.button_9,
            R.id.button_point,
            R.id.button_division,
            R.id.button_minus,
            R.id.button_times,
            R.id.button_plus
        )

        val previewTextView = findViewById<TextView>(R.id.preview)
        val resultTextView = findViewById<TextView>(R.id.result)

        val buttonDel = findViewById<Button>(R.id.button_del)
        val buttonSubmit = findViewById<Button>(R.id.button_submit)

        listenButtonsNumber(numberButtons, previewTextView, resultTextView)

        buttonDel.setOnClickListener {
            previewTextView.text = ""
            this.resultValue = BigDecimal.ZERO
            resultTextView.text = "0"
        }

        buttonSubmit.setOnClickListener {
            val valuesPreview = previewTextView.text.split(" ").size == 3
            if (valuesPreview) getResult(previewTextView, resultTextView)
        }

    }

    private fun listenButtonsNumber(idButtons: IntArray, preview: TextView, result: TextView) {
        idButtons.forEach {
            val button = findViewById<Button>(it)
            button.setOnClickListener {
                val valuesPreview = preview.text.split(" ")
                val sb = StringBuilder()

                when (button.text) {
                    "+", "-", "x", "/" -> {
                        if (valuesPreview.size == 3) getResult(preview, result)
                        preview.text =
                            sb.append(preview.text).append(" ").append(button.text).append(" ")
                                .toString()
                    }
                    else -> {
                        if (valuesPreview.size == 1 && valuesPreview[0].compareTo("ans") == 0) {
                            preview.text = sb.append(button.text).toString()
                            this.resultValue = BigDecimal.ZERO
                        } else {
                            preview.text = sb.append(preview.text).append(button.text).toString()
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getResult(preview: TextView, result: TextView) {
        val valuesPreview = preview.text.split(" ")

        var firstValue: String = valuesPreview[0]
        if (valuesPreview[0].compareTo("ans") == 0) firstValue = this.resultValue.toString()

        when (valuesPreview[1]) {
            "+" -> {
                this.resultValue = BigDecimal(firstValue).plus(BigDecimal(valuesPreview[2]))
            }
            "-" -> {
                this.resultValue = BigDecimal(firstValue).minus(BigDecimal(valuesPreview[2]))
            }
            "/" -> {
                this.resultValue = BigDecimal(firstValue).div(BigDecimal(valuesPreview[2]))
            }
            "x" -> {
                this.resultValue = BigDecimal(firstValue).times(BigDecimal(valuesPreview[2]))
            }

        }
        result.text = this.resultValue.toString()
        preview.text = "ans"
    }
}