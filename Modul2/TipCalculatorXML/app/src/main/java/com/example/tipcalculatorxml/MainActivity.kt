package com.example.tipcalculatorxml

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.tipcalculatorxml.databinding.ActivityMainBinding
import androidx.core.widget.addTextChangedListener
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tipOptions = listOf("15%", "18%", "20%")
    private var selectedTipPercentage = 15.0
    private var roundUp = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTipPercentageInput()
        setupBillInput()
        setupRoundUpTip()

        updateCalculatedTip(0.0)
    }

    private fun setupTipPercentageInput() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, tipOptions)
        binding.tipPercentageInput.setAdapter(adapter)

        binding.tipPercentageInput.setText(tipOptions[0], false)

        binding.tipPercentageInput.setOnItemClickListener { _, _, position, _ ->
            val selectedOption = adapter.getItem(position) ?: tipOptions[0]
            selectedTipPercentage = selectedOption.replace("%", "").toDouble()
            calculateAndShowTip()
        }

        binding.tipPercentageInput.setOnClickListener {
            binding.tipPercentageInput.showDropDown()
        }
    }

    private fun setupBillInput() {
        binding.billAmountInput.addTextChangedListener {
            calculateAndShowTip()
        }
    }

    private fun setupRoundUpTip() {
        binding.roundUpSwitch.setOnCheckedChangeListener { _, isChecked ->
            roundUp = isChecked
            calculateAndShowTip()
        }
    }

    private fun calculateAndShowTip() {
        val billInput = binding.billAmountInput.text.toString()
        val billAmount = billInput.toDoubleOrNull() ?: 0.0
        updateCalculatedTip(billAmount)
    }

    private fun updateCalculatedTip(billAmount: Double) {
        var tip = (selectedTipPercentage / 100) * billAmount
        if (roundUp) {
            tip = ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance(Locale.US).format(tip)
        binding.calculatedTip.text = getString(R.string.tip_amount, formattedTip)
    }
}