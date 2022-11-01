package frog.company.subscribercheat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import frog.company.subscribercheat.R
import frog.company.subscribercheat.database.UtilsDB
import frog.company.subscribercheat.databinding.ListOrderBinding
import frog.company.subscribercheat.helper.AppConst
import frog.company.subscribercheat.helper.IClickOrder
import frog.company.subscribercheat.model.Orders
import frog.company.subscribercheat.model.User
import frog.company.subscribercheat.ui.home.HomeFragment
import frog.company.subscribercheat.ui.web.WebFragment
import io.paperdb.Paper

class AdapterOrders(val items : ArrayList<Orders>) : RecyclerView.Adapter<AdapterOrders.ViewHolder>(), IClickOrder {

    private lateinit var binding : ListOrderBinding

    inner class ViewHolder(binding: ListOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ListOrderBinding? = null

        init {
            this.binding = binding
            binding.listener = this@AdapterOrders
        }
        fun bind(order: Orders?) {
            binding?.data = order
            binding?.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.list_order, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onSelectOrder(order: Orders) {
        val fragment = WebFragment.newInstance(order.orderUrl)
        (binding.root.context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(
            R.id.viewPager,
            fragment
        ).addToBackStack(null).commit()

        val utils = UtilsDB()
        val user = Paper.book().read<User>(AppConst.DATA_USER, User())!!
        utils.onAddCoin(user.token,order.orderPrice)
        utils.onMinusOrder(order.orderId)

        val myList = Paper.book().read<ArrayList<Int>>(AppConst.DATA_LIST, ArrayList())!!
        if(myList.size > 100)
            myList.clear()

        myList.add(order.orderId)
        Paper.book().write<ArrayList<Int>>(AppConst.DATA_LIST, myList)

        items.remove(order)
        notifyDataSetChanged()
    }
}