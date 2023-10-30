package br.projeto.financasdados.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.projeto.financasdados.R
import br.projeto.financasdados.databinding.TransactionItemBinding
import br.projeto.financasdados.model.Transaction

class ExtractAdapter(private var transactionsList: List<Transaction>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ExtractAdapter.ExtractViewHolder>() {

    interface OnItemClickListener {
        fun delete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtractViewHolder {
        val binding = TransactionItemBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ExtractViewHolder(binding)
    }

    override fun getItemCount() = transactionsList.size

    override fun onBindViewHolder(holder: ExtractViewHolder, position: Int) {

        holder.binding.ivRemove.setOnClickListener {
            listener.delete(position)
        }
        holder.bind(transactionsList[position])
    }

    class ExtractViewHolder(val binding: TransactionItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction) = with(itemView) {
            binding.tvDescriptionItem.text = transaction.description
            binding.tvDate.text = transaction.date

            if (transaction.savedMoney) {
                binding.tvAmount.text = String.format("R$ %.2f", transaction.amount)
                binding.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green))
                binding.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.saved_money))
            } else {
                binding.tvAmount.text = String.format("R$ -%.2f", transaction.amount)
                binding.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.red))
                binding.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.spent_money))
            }
        }
    }
}