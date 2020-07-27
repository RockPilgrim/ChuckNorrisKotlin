package my.rockpilgrim.chucknorriskotlin

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.jokes_fragment.*
import my.rockpilgrim.chucknorriskotlin.adapters.JokeAdapter
import my.rockpilgrim.chucknorriskotlin.data.Event
import my.rockpilgrim.chucknorriskotlin.data.pogo.Joke
import my.rockpilgrim.chucknorriskotlin.databinding.JokesFragmentBinding
import my.rockpilgrim.chucknorriskotlin.viewModels.MainViewModel

class JokeFragment : Fragment() {

//    private val mainViewModels by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    companion object{
        private val TAG = JokeFragment::class.java.simpleName
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var imm: InputMethodManager

    val adapter = JokeAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG,"onCreateView()")
        val binding = JokesFragmentBinding.inflate(inflater, container, false)

        binding.viewModel = mainViewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        initEditor()
        initRecycler()
        initRefresh()
    }

    private fun initEditor() {
        countEditText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                reload()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        reloadButton.setOnClickListener {
            reload()
        }
    }

    private fun reload() {
        val count: Int? = countEditText.text.toString().toIntOrNull()
        Log.i(TAG, "Count: ${count ?: 0}")
        mainViewModel.loadJokes(count ?: -1)
        closeKeyBoard()
        countEditText.setText("")
    }

    private fun initRefresh() {
        refreshView.setOnRefreshListener {
            mainViewModel.loadJokes()
        }
    }

    private fun initRecycler() {
        val adapter = JokeAdapter()
        jokeRecyclerView.adapter = adapter
        subscribeUI(adapter)
    }

    private fun subscribeUI(
        adapter: JokeAdapter) {
        mainViewModel.jokeList.observe(viewLifecycleOwner, Observer {event ->
            when (event) {
                is Event.Success -> success(jokes = event.jokes, adapter = adapter)
                is Event.Error -> error(message = event.message)
                Event.Loading -> loading()
            }
        })
    }

    private fun loading() {
        refreshView.isRefreshing = true
    }

    private fun success(jokes: List<Joke>, adapter: JokeAdapter) {
        adapter.submitList(jokes)
        refreshView.isRefreshing=false
    }

    private fun error(message:String) {
        refreshView.isRefreshing=false
        makeToast(message)
    }

    private fun closeKeyBoard() {
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun makeToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView()")
    }
}