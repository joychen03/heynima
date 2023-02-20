package itb.dam.jiafuchen.heynima.ui.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.core.text.set
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import itb.dam.jiafuchen.heynima.R
import itb.dam.jiafuchen.heynima.databinding.FragmentSearchBinding
import itb.dam.jiafuchen.heynima.ui.viewmodels.SharedViewModel

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var layout : FragmentSearchBinding

    private val sharedViewModel : SharedViewModel by activityViewModels()

    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        layout = FragmentSearchBinding.inflate(inflater,container,false)
        return layout.root
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initApp()

        val alert = AlertDialog.Builder(this.context)

        layout.searchBtn.setOnClickListener {

            val searchValue = layout.input.text

            if(searchValue.isEmpty()){
                alert.apply {
                    setTitle("Info")
                    setMessage("Por favor introduzca un nombre de juego")
                    setCancelable(true)
                    setPositiveButton("Ok"){dialog, it ->

                    }
                }
                alert.show()
                return@setOnClickListener
            }

            sharedViewModel.saveSearch(searchValue.toString())

            hideKeyboard()
            val direction = SearchFragmentDirections.actionSearchFragmentToListFragment(searchValue.toString())
            findNavController().navigate(direction)
        }

        layout.backToHome.setOnClickListener{

            sharedViewModel.currentSearch = ""
            hideKeyboard()

            val direction = SearchFragmentDirections.actionSearchFragmentToHomeFragment()
            findNavController().navigate(direction)
        }
    }

    private fun initApp() {
        if(sharedViewModel.currentSearch != ""){
            val editText = layout.input
            editText.setText(sharedViewModel.currentSearch)
        }
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}