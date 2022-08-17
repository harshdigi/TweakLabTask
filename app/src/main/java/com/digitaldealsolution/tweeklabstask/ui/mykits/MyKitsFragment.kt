package com.digitaldealsolution.tweeklabstask.ui.mykits

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.digitaldealsolution.tweeklabstask.R
import com.digitaldealsolution.tweeklabstask.databinding.FragmentAccountBinding
import com.digitaldealsolution.tweeklabstask.databinding.FragmentMyKitsBinding
import com.digitaldealsolution.tweeklabstask.ui.account.AccountViewModel

class MyKitsFragment : Fragment() {

    private var _binding: FragmentMyKitsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myKitsViewModel =
            ViewModelProvider(this).get(MyKitsViewModel::class.java)

        _binding = FragmentMyKitsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMyKits
        myKitsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}