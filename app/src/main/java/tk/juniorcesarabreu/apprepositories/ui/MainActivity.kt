package tk.juniorcesarabreu.apprepositories.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import org.koin.androidx.viewmodel.ext.android.viewModel
import tk.juniorcesarabreu.apprepositories.R
import tk.juniorcesarabreu.apprepositories.core.createDialog
import tk.juniorcesarabreu.apprepositories.core.createProgressDialog
import tk.juniorcesarabreu.apprepositories.core.hideSoftKeyboard
import tk.juniorcesarabreu.apprepositories.databinding.ActivityMainBinding
import tk.juniorcesarabreu.apprepositories.presentation.MainViewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val dialog by lazy { createProgressDialog() }

    private val viewModel by viewModel<MainViewModel>()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter by lazy { RepoListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        // adicionando toolbar
        setSupportActionBar(binding.toolbar)

        binding.rvRepos.adapter = adapter

        viewModel.repos.observe(this) {

            when (it) {
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Error -> {
                    createDialog {
                        setMessage(it.error.message)
                    }.show()

                    adapter.submitList(null)
                    dialog.dismiss()
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                    adapter.submitList(it.list)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.getRepoList(it) }
        binding.root.hideSoftKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.e(TAG, "onQueryTextChange: $newText")
        return false
    }

    companion object {
        private const val TAG = "TAG"
    }
}