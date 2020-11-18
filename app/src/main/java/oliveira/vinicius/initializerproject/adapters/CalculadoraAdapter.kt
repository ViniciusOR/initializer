package oliveira.vinicius.initializerproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.linha_calculadora.view.*
import oliveira.vinicius.initializerproject.modelo.Calculadora
import oliveira.vinicius.initializerproject.R

class CalculadoraAdapter(private val calculadoras: List<Calculadora>,
                         private val context: Context,
                         private val listener: (Calculadora) -> Unit):
RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.linha_calculadora, parent, false))
    }

    override fun getItemCount(): Int {
        return calculadoras.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val calculadora = calculadoras[position]

        if (holder is ViewHolder) {
            holder?.let { it.bind(calculadora,position,listener) }
        }

    }

    public fun updateList() {
        this.notifyDataSetChanged()
    }
}

class ViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
    fun bind(calculadora: Calculadora, position: Int?, listener: (Calculadora) -> Unit) {
        val textViewCapital = itemView.linhaCalculadoraTextViewCapital
        val textViewTaxa = itemView.linhaCalculadoraTextViewTaxa
        val textViewPeriodo = itemView.linhaCalculadoraTextViewPeriodo
        val textViewDesconto = itemView.linhaCalculadoraTextViewDesconto
        val textViewJuros = itemView.linhaCalculadoraTextViewJuros
        val textViewMontante = itemView.linhaCalculadoraTextViewMontante
        val textViewTaxaAdministrativa = itemView.linhaCalculadoraTextViewTaxaAdministrativa
        val textViewValorAtual = itemView.linhaCalculadoraTextViewValorAtual
        val textViewValorDescontadoComercial = itemView.linhaCalculadoraTextViewValorDescontadoComercial
        val textViewCapitalizacoes = itemView.linhaCalculadoraTextViewCapitalizacao

        calculadora?.let {
            textViewCapital.text = it.capital.toString()
            textViewTaxa.text = it.taxa.toString()
            textViewPeriodo.text = it.periodo.toString()
            textViewDesconto.text = it.desconto.toString()
            textViewJuros.text = it.juros.toString()
            textViewMontante.text = it.montante.toString()
            textViewTaxaAdministrativa.text = it.taxaAdministrativa.toString()
            textViewValorAtual.text = it.valorAtual.toString()
            textViewValorDescontadoComercial.text = it.valorDescontadoComercial.toString()
            textViewCapitalizacoes.text = it.capitalizacoes.toString()


            itemView.setOnClickListener {
                listener(calculadora)
            }

        }

    }
}