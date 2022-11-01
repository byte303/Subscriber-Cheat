package frog.company.subscribercheat.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import frog.company.subscribercheat.helper.AppConst
import frog.company.subscribercheat.model.Orders
import frog.company.subscribercheat.model.User
import kotlinx.serialization.json.Json
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class UtilsDB : ViewModel() {

    private var user: MutableLiveData<User?> = MutableLiveData()
    fun getUser(): LiveData<User?> {
        return user
    }

    private var listOrders: MutableLiveData<ArrayList<Orders>?> = MutableLiveData()
    fun getListOrders(): LiveData<ArrayList<Orders>?> {
        return listOrders
    }

    fun onSelectOrders(type : Int){
        val client = OkHttpClient()

        val formBody = FormBody.Builder()
            .add("orderType", type.toString())
            .build()

        val request = Request.Builder()
            .url(AppConst.url + "/select_orders.php")
            .addHeader("Content-Type", "application/json")
            .get()
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("onOrders_onFailure", e.message.toString())
                listOrders.postValue(null)
            }

            override fun onResponse(call: Call, response: Response) {
                try{
                    val strResponse = response.body!!.string()
                    Log.e("onOrders_onResponse", strResponse)
                    val obj = JSONArray(strResponse)

                    val list = ArrayList<Orders>()
                    var element : Orders

                    if(obj.length() > 0){
                        for(i in 0 until obj.length()){
                            element = Json.decodeFromString(Orders.serializer(), obj[i].toString())
                            list.add(element)
                        }
                        listOrders.postValue(list)
                    }else
                        listOrders.postValue(null)

                } catch (ex : Exception){
                    Log.e("onOrders_ex", ex.message.toString())
                    listOrders.postValue(null)
                }
            }
        })
    }

    fun onLogin(login : String, password : String){
        val client = OkHttpClient()

        val formBody = FormBody.Builder()
            .add("login", login)
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url(AppConst.url + "/select_user.php")
            .addHeader("Content-Type", "application/json")
            .get()
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("onLogin_onFailure", e.message.toString())
                user.postValue(null)
            }

            override fun onResponse(call: Call, response: Response) {
                try{
                    val strResponse = response.body!!.string()
                    Log.e("onLogin_onResponse", strResponse)
                    val obj = JSONObject(strResponse)

                    val element = Json.decodeFromString(User.serializer(), obj.toString())
                    user.postValue(element)

                } catch (ex : Exception){
                    Log.e("onLogin_ex", ex.message.toString())
                    user.postValue(null)
                }
            }
        })
    }

    fun onInsertUser(users : User){
        val client = OkHttpClient()

        val formBody = FormBody.Builder()
            .add("login", users.login)
            .add("password", users.password)
            .build()

        val request = Request.Builder()
            .url(AppConst.url + "/insert_user.php")
            .addHeader("Content-Type", "application/json")
            .get()
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("onInsertUser", e.message.toString())
                user.postValue(null)
            }

            override fun onResponse(call: Call, response: Response) {
                try{
                    val strResponse = response.body!!.string()
                    Log.e("onInsertUser", strResponse)

                    if(strResponse != "code_error: 400"){
                        val obj = JSONObject(strResponse)
                        val element = Json.decodeFromString(User.serializer(), obj.toString())
                        user.postValue(element)
                    }

                } catch (ex : Exception){
                    Log.e("onInsertUser", ex.message.toString())
                    user.postValue(null)
                }
            }
        })
    }

    fun onAddCoin(token : String, coin : Int){
        val client = OkHttpClient()

        val formBody = FormBody.Builder()
            .add("token", token)
            .add("coin", coin.toString())
            .build()

        val request = Request.Builder()
            .url(AppConst.url + "/add_coin.php")
            .addHeader("Content-Type", "application/json")
            .get()
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("onAddCoin", e.message.toString())
                user.postValue(null)
            }

            override fun onResponse(call: Call, response: Response) {
                try{
                    val strResponse = response.body!!.string()
                    Log.e("onAddCoin", strResponse)

                    val obj = JSONObject(strResponse)
                    val element = Json.decodeFromString(User.serializer(), obj.toString())
                    user.postValue(element)

                } catch (ex : Exception){
                    Log.e("onAddCoin", ex.message.toString())
                    user.postValue(null)
                }
            }
        })
    }

    fun onMinusOrder(id : Int){
        val client = OkHttpClient()

        val formBody = FormBody.Builder()
            .add("orderId", id.toString())
            .build()

        val request = Request.Builder()
            .url(AppConst.url + "/minus_order.php")
            .addHeader("Content-Type", "application/json")
            .get()
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("onMinusOrder", e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                try{
                    val strResponse = response.body!!.string()
                    Log.e("onMinusOrder", strResponse)

                } catch (ex : Exception){
                    Log.e("onMinusOrder", ex.message.toString())
                }
            }
        })
    }

    fun onCreateOrder(token : String, price : Int, order : Orders){
        val client = OkHttpClient()

        val formBody = FormBody.Builder()
            .add("orderName", order.orderName)
            .add("orderPrice", order.orderPrice.toString())
            .add("orderCount", order.orderCount.toString())
            .add("orderType", order.orderType.toString())
            .add("orderUrl", order.orderUrl)
            .add("token", token)
            .add("price", price.toString())
            .build()

        val request = Request.Builder()
            .url(AppConst.url + "/create_order.php")
            .addHeader("Content-Type", "application/json")
            .get()
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("onCreateOrder", e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                try{
                    val strResponse = response.body!!.string()
                    Log.e("onCreateOrder", strResponse)

                } catch (ex : Exception){
                    Log.e("onCreateOrder", ex.message.toString())
                }
            }
        })
    }
}