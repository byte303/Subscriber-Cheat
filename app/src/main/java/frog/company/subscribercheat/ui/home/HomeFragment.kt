package frog.company.subscribercheat.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import frog.company.subscribercheat.R
import frog.company.subscribercheat.adapter.AdapterOrders
import frog.company.subscribercheat.database.UtilsDB
import frog.company.subscribercheat.databinding.FragmentHomeBinding
import frog.company.subscribercheat.helper.AppConst
import io.paperdb.Paper

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var selectIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectIndex = arguments?.getInt(ARG_SECTION_NUMBER)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val utils = ViewModelProvider(this)[UtilsDB::class.java]

        utils.getListOrders().observe(viewLifecycleOwner) {
            if(it != null) {
                val list = it
                val myList = Paper.book().read<ArrayList<Int>>(AppConst.DATA_LIST, ArrayList())!!

                for(i in 0 until it.size)
                    for(q in 0 until myList.size)
                        if(it[i].orderId == myList[q])
                            list.remove(it[i])

                if(myList.size > 0) {
                    binding.txtEmpty.visibility = View.GONE
                    binding.list.adapter = AdapterOrders(list)
                }else
                    binding.txtEmpty.visibility = View.VISIBLE
            }else
                binding.txtEmpty.visibility = View.VISIBLE
        }
        utils.onSelectOrders(selectIndex)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Int): HomeFragment {
            return HomeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}