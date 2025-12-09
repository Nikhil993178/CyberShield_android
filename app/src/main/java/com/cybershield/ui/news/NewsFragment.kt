package com.cybershield.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cybershield.data.DummyData
import com.cybershield.data.model.NewsCategory
import com.cybershield.databinding.FragmentNewsBinding
import com.cybershield.ui.news.adapter.NewsAdapter

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private var category: NewsCategory = NewsCategory.INTERNATIONAL

    companion object {
        fun newInstance(position: Int): NewsFragment {
            val fragment = NewsFragment()
            fragment.arguments = Bundle().apply {
                putInt("position", position)
            }
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt("position", 0) ?: 0
        category = when (position) {
            0 -> NewsCategory.INTERNATIONAL
            1 -> NewsCategory.NATIONAL
            2 -> NewsCategory.LOCAL
            else -> NewsCategory.INTERNATIONAL
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val allNews = DummyData.getDummyNews()
        val filteredNews = allNews.filter { it.category == category }
        val adapter = NewsAdapter(filteredNews)
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.newsRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

