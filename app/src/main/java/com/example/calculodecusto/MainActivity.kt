package com.example.calculodecusto

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sh = getSharedPreferences("BlocodeNotas", Context.MODE_PRIVATE)



        BtLimpar.setOnClickListener { v: View? ->
            TxtCusto.text.clear()
            TxtVenda.text.clear()
            TxtNomeAnotacao.text.clear()
            TxtLucro.setText("")
        }

        BtCalcular.setOnClickListener { v: View? ->
            if (TxtCusto.text.isNotEmpty() and TxtVenda.text.isNotEmpty()) {

                val custo = TxtCusto.text.toString()
                val venda = TxtVenda.text.toString()

                val beneficio = venda.toDouble() - custo.toDouble()

                if (beneficio > 0) {
                    Toast.makeText(this, "Lucro de :" + beneficio, Toast.LENGTH_SHORT).show()
                    TxtLucro.setText("Lucro:" + beneficio.toString())
                } else if (beneficio < 0) {
                    Toast.makeText(this, "Prejuizo de:" + beneficio, Toast.LENGTH_SHORT).show()
                    TxtLucro.setText("Prejuizo:" + beneficio.toString())
                } else {
                    Toast.makeText(this, "Sem lucro ou perda = 0", Toast.LENGTH_SHORT).show()
                    TxtLucro.setText("Lucro:0")
                }
            } else {
                Toast.makeText(this, "Informar Custo e Venda para calculo", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        BtSalvar.setOnClickListener { v: View? ->
            if (TxtNomeAnotacao.text.isNotEmpty()) {

                if (TxtLucro.text.isNotEmpty()) {

                    val custo = TxtCusto.text.toString()
                    val venda = TxtVenda.text.toString()
                    val lucro = TxtLucro.text.split(":")


                    if (lucro[1].toDouble() + custo.toDouble() == venda.toDouble()) {

                        val texto =
                            (TxtCusto.text.toString() + ";" + TxtVenda.text.toString() + ";" + TxtLucro.text.toString())

                        sh.edit().putString(TxtNomeAnotacao.text.toString(), texto).apply()

                        Toast.makeText(this, "Anotação Salva", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Calcular novamente", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Realize Calculo", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Nome não informado ", Toast.LENGTH_SHORT).show()
            }
        }

        BtAbrir.setOnClickListener { v: View? ->
            if (TxtNomeAnotacao.text.isNotEmpty()) {


                var texto = sh.getString(TxtNomeAnotacao.text.toString(), "")

                if (texto.isNullOrEmpty()) {

                    Toast.makeText(this, "Anotação Vazia", Toast.LENGTH_SHORT).show()
                } else {

                    val lista = texto.split(";")
                    TxtLucro.setText(lista[2])
                    TxtVenda.setText(lista[1])
                    TxtCusto.setText(lista[0])

                    Toast.makeText(this, "Anotação carregada com sucesso ", Toast.LENGTH_SHORT)
                        .show()
                }
                Toast.makeText(this, "Abrindo anotação", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Nome da anotação não localizada", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}

