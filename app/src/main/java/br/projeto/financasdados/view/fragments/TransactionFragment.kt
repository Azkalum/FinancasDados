package br.projeto.financasdados.view.fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.projeto.financasdados.databinding.FragmentTransactionBinding
import br.projeto.financasdados.model.Transaction
import br.projeto.financasdados.viewmodel.FinancesViewModel
import br.projeto.financasdados.viewmodel.FinancesViewModel.FinancesViewModelFactory

class TransactionFragment : Fragment() {

    private var savedMoney: Boolean = false

    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!

    lateinit var application: Application

    val viewModel by lazy {
        ViewModelProvider(this, FinancesViewModelFactory(application))
            .get(FinancesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View {

        application = requireActivity().getApplication()!!

        // Inflate the layout for this fragment
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupIconBack()
        setupButtons()
        setupRadioButton()
    }

    private fun setupIconBack() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupButtons() {
        binding.btnSave.setOnClickListener {
            val transaction = transactionInformation()

            if (transaction == -1) {
                Toast.makeText(context, "Preencha os campos corretamente!", Toast.LENGTH_LONG)
                    .show()
            } else {
                viewModel.add(transaction as Transaction)
                findNavController().popBackStack()
            }
        }

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRadioButton() {
        binding.rbSavedMoney.setOnClickListener {
            savedMoney = true
        }
        binding.rbSpentMoney.setOnClickListener {
            savedMoney = false
        }
    }

    private fun transactionInformation(): Any {
        try {
            if (binding.etDescription.editText!!.text.toString().isNotEmpty()) {
                var data = calendar()

                return Transaction(
                    null,
                    binding.etDescription.editText!!.text.toString(),
                    binding.etAmount.editText!!.text.toString().toDouble(),
                    data,
                    savedMoney
                )
            } else {
                return -1
            }
        } catch (ex: Exception) {
            return -1
        }
    }

    fun calendar(): String {
        val datePicker = binding.dpDate

        val day = datePicker.dayOfMonth
        val month = datePicker.month + 1
        val year = datePicker.year

        return "$day/$month/$year"
    }
}