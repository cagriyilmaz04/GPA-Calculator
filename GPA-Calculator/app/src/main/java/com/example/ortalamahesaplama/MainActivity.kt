package com.example.ortalamahesaplama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.isVisible
import com.example.ortalamahesaplama.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var dersler = ArrayList<Dersler>()
    var average = 0.0
    var credit = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val harf_notu_dizi = resources.getStringArray(R.array.harf_notlari)
        val kredi_dizi = resources.getStringArray(R.array.kredi)
        if (binding.spinnerHarfNotu != null) {
            val adapter_harf_notu =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, harf_notu_dizi)
            binding.spinnerHarfNotu.adapter = adapter_harf_notu
        }
        if (binding.spinnerKredi != null) {
            val kredi_spinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, kredi_dizi)
            binding.spinnerKredi.adapter = kredi_spinner
        }
        var a = 0
        var b = 0
        binding.spinnerKredi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    Toast.makeText(
                        applicationContext,
                        "${kredi_dizi.get(position)}",
                        Toast.LENGTH_LONG
                    ).show()
                    a = position
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        binding.spinnerHarfNotu.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(applicationContext, "${position}", Toast.LENGTH_LONG).show()
                    if (position > 0) {
                        b = position
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        binding.buttonKaydet.setOnClickListener {
            val dersAdi = binding.editTextDersAdi.text.toString()
            if ((!dersAdi.isNullOrEmpty()) && a > 0 && b > 0) {
                val inflate = LayoutInflater.from(this).inflate(R.layout.row, null)

                inflate.text_ders_adi_row.setText("Dersin Adı : ${dersAdi}")
                inflate.text_kredi.text = "Kredisi : ${kredi_dizi.get(a)}"
                inflate.text_harf_notu.text = "Harf Notu : ${harf_notu_dizi.get(b)}"
                binding.scrolls.addView(inflate)
                var ders = Dersler(dersAdi, kredi_dizi.get(a), harf_notu_dizi.get(b))
                dersler.add(ders)
            }
        }
        binding.textToplamKredi.isVisible = false
        binding.buttonHesapla.setOnClickListener {
            if (binding.scrolls.childCount > 0) {
                kaydet(dersler)
                binding.textToplamKredi.isVisible = true
                binding.textToplamKredi.text = "Toplam Kredi : ${credit}\nOrtalamanız : ${average}"
            }
        }
        binding.buttonSil.setOnClickListener {
            if(binding.scrolls.childCount>0){
                binding.scrolls.removeAllViews()
                binding.editTextDersAdi.setText("")
                binding.editTextDersAdi.hint="Dersin Adını Giriniz"
            }
        }

    }
    fun kaydet(dersler: ArrayList<Dersler>) {
        var total = 0.0
        var count = 0
        var katsayi = -1.0
        var dersKredi = 0.0
        for (i in dersler) {
            when (i.dersHarfNotu) {
                "AA" -> {
                    katsayi = 4.0
                }
                "BA" -> {
                    katsayi = (3.5)
                }
                "BB" -> {
                    katsayi = (3.0)
                }
                "CB" -> {
                    katsayi = (2.5)
                }
                "CC" -> {
                    katsayi = (2.0)
                }
                "DC" -> {
                    katsayi = (1.5)
                }
                "DD" -> {
                    katsayi = (1.0)
                }
                "FF" -> {
                    katsayi = (0.0)
                }


            }
            total += (i.dersKredi!!.toFloat() * katsayi)
            dersKredi += i.dersKredi!!.toFloat()
            ++count
        }
        credit = dersKredi
        average = total / credit

    }



}