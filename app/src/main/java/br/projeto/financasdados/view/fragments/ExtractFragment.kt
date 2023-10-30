package br.projeto.financasdados.view.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.projeto.financasdados.databinding.FragmentExtractBinding
import br.projeto.financasdados.model.Transaction
import br.projeto.financasdados.view.adapter.ExtractAdapter
import br.projeto.financasdados.viewmodel.FinancesViewModel
import br.projeto.financasdados.viewmodel.FinancesViewModel.FinancesViewModelFactory

class ExtractFragment : Fragment(), ExtractAdapter.OnItemClickListener {

    private var _binding: FragmentExtractBinding? = null
    private val binding get() = _binding!!

    lateinit var application: Application

    lateinit var adapter: ExtractAdapter

    val viewModel by lazy {
        ViewModelProvider(this, FinancesViewModelFactory(application))
            .get(FinancesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        application = requireActivity().getApplication()!!

        // Inflate the layout for this fragment
        _binding = FragmentExtractBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel.updateList()
        setupObserver()
        setupIconBack()
    }

    private fun setupIconBack() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupObserver() {
        viewModel.transactionList.observe(viewLifecycleOwner) {
            setupRecyclerView(it)
        }
    }

    private fun setupRecyclerView(transactionList: List<Transaction> = mutableListOf()) {
        adapter = ExtractAdapter(transactionList, this)
        binding.rvTransactions.layoutManager = LinearLayoutManager(context)
        binding.rvTransactions.setHasFixedSize(true)
        binding.rvTransactions.adapter = adapter
    }

    override fun delete(position: Int) {
        viewModel.remove(position)
        setupRecyclerView()
    }
}