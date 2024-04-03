package com.example.my
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import com.example.my.R
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.ln
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var textViewResult: TextView
    private var isEOperation: Boolean = false
    private var baseE: Double = 2.71 // Число e равное 2.71
    private var expression: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewResult = findViewById(R.id.textViewResult)
        val gridLayout: GridLayout = findViewById(R.id.gridLayout)

        val buttons = arrayOf(
            "7", "8", "9", "C",
            "4", "5", "6", "/",
            "1", "2", "3", "*",
            "0", ".", "=", "+",
            "mod", "x!", "E^Y", "-"
        )

        buttons.forEach { text ->
            val button = Button(this)
            button.text = text
            button.setTextColor(resources.getColor(android.R.color.white))
            button.setBackgroundColor(resources.getColor(androidx.appcompat.R.color.material_grey_600))
            button.setOnClickListener {
                when (text) {
                    "=" -> calculateResult()
                    "C" -> clearResult()
                    "mod" -> {
                        textViewResult.text = abs(textViewResult.text.toString().toDouble()).toString()
                    }
                    "x!" -> {
                        val number = textViewResult.text.toString().toDouble()
                        val result = calculateFactorial(number.toInt())
                        textViewResult.text = result.toString()
                    }
                    "E^Y" -> {
                        expression = "$baseE^"
                        textViewResult.text = expression
                        isEOperation = true
                    }
                    else -> appendToResult(text)
                }
            }
            val params = GridLayout.LayoutParams()
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            button.layoutParams = params
            gridLayout.addView(button)
        }
    }

    private fun appendToResult(value: String) {
        if (isEOperation) {
            textViewResult.append(value)
        } else {
            if (textViewResult.text == "0" && value != ".") {
                textViewResult.text = value
            } else {
                textViewResult.append(value)
            }
        }
    }

    private fun calculateResult() {
        val expressionToEvaluate = textViewResult.text.toString()
        try {
            if (isEOperation) {
                val exponent = expressionToEvaluate.substringAfterLast("^").toDouble()
                val result = baseE.pow(exponent)
                textViewResult.text = result.toString()
            } else {
                // Evaluate other expressions if needed
            }
        } catch (e: Exception) {
            textViewResult.text = "Error"
        }
    }

    private fun clearResult() {
        textViewResult.text = "0"
        isEOperation = false
        expression = ""
    }

    private fun calculateFactorial(number: Int): Long {
        var result: Long = 1
        for (i in 1..number) {
            result *= i
        }
        return result
    }
}