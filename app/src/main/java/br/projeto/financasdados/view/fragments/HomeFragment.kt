package br.projeto.financasdados.view.fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.projeto.financasdados.R
import br.projeto.financasdados.databinding.FragmentHomeBinding
import br.projeto.financasdados.viewmodel.FinancesViewModel
import br.projeto.financasdados.viewmodel.FinancesViewModel.FinancesViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateList()
        setupObserver()
        setupButtons()
    }

    private fun setupObserver() {
        viewModel.balance.observe(viewLifecycleOwner) {
            binding.tvAmount1.text = String.format("R$ %.2f", it.income)
            binding.tvAmount2.text = String.format("R$ %.2f", it.expense)
            binding.tvAmount3.text = String.format("R$ %.2f", it.total)
        }
    }

    private fun setupButtons() {
        binding.btnNewTransaction.setOnClickListener {
            callTransactions()
        }

        binding.btnSearch.setOnClickListener {
            callExtract()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateList()
    }

    fun callExtract() {
        findNavController().navigate(R.id.action_homeFragment_to_extractFragment)
    }

    fun callTransactions() {
        findNavController().navigate(R.id.action_homeFragment_to_transactionFragment)
    }
}