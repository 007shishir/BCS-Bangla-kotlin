package com.example.bcsbanglagrammar.ui.recyclerView

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bcsbanglagrammar.CheckingNetworkConnectivity
import com.example.bcsbanglagrammar.MemorizeVersion2
import com.example.bcsbanglagrammar.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.firebase.ui.database.FirebaseRecyclerAdapter;


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirebaseRecView.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirebaseRecView : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

//    recycler view and database referrence are here.
    private var mRecycler_Memorize: RecyclerView? = null
    private var mDatabase: DatabaseReference? = null
//    The child name is came from previous fragment
    var childName_fromFragment: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_firebase_rec_view, container, false)

        childName_fromFragment = requireArguments().getString("child_name")
        mDatabase = FirebaseDatabase.getInstance().reference.child(childName_fromFragment!!)
        mDatabase!!.keepSynced(false)
        mRecycler_Memorize = root.findViewById<RecyclerView>(R.id.mRecycler_Memorize)
        mRecycler_Memorize!!.setHasFixedSize(true)
        mRecycler_Memorize!!.setLayoutManager(LinearLayoutManager(context))
        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FirebaseRecView.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FirebaseRecView().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Parameter, ParameterViewHolder> =
            object : FirebaseRecyclerAdapter<Parameter, ParameterViewHolder>(
                Parameter::class.java, R.layout.option_recycler_view_list,
                ParameterViewHolder::class.java, mDatabase
            ) {
                protected override fun populateViewHolder(
                    viewHolder: ParameterViewHolder,
                    model: Parameter,
                    position: Int
                ) {
                    val post_key: String? = getRef(position).getKey()
                    viewHolder.setSource(model.getSource())
                    viewHolder.setTopic(model.getTopic())
                    viewHolder.setSum(model.getSum())
                    viewHolder.setTotal(model.getTotal())
                    viewHolder.mView.setOnClickListener {

                        if (isInternetAvailable(context!!)) {
                            val intent = Intent(activity, MemorizeVersion2::class.java)
                            intent.putExtra("key_name", post_key)
                            intent.putExtra("childName", childName_fromFragment)
                            Toast.makeText(
                                context,
                                "Please make sure you turn off the rotation of your device",
                                Toast.LENGTH_LONG
                            ).show()
                            startActivity(intent)
                        } else {
                            Toast.makeText(context, "No Network Connection", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        mRecycler_Memorize!!.adapter = firebaseRecyclerAdapter
    }

    class ParameterViewHolder(var mView: View) : RecyclerView.ViewHolder(
        mView
    ) {
        fun setSource(source: String?) {
            val post_source = mView.findViewById<TextView>(R.id.mTxt_source)
            post_source.text = source
        }

        fun setTopic(topic: String?) {
            val post_topic = mView.findViewById<TextView>(R.id.mTxt_topic)
            post_topic.text = topic
        }

        fun setSum(sum: String?) {
            val post_sum = mView.findViewById<TextView>(R.id.mTxt_sum)
            post_sum.text = sum
        }

        fun setTotal(total: String?) {
            val post_total = mView.findViewById<TextView>(R.id.mTxt_total)
            post_total.text = total
        }
    }

    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }
}