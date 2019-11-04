package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.myapplication.Model.ConnectionControl
import com.example.myapplication.Responce.Responce
import com.example.myapplication.Retrofit.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("SetTextI18n")
class SettingFragment : DialogFragment(){
    var connectionResult:Boolean=false
    lateinit var ipaddress:EditText
    lateinit var checkResult:TextView
    lateinit var saveSharedPreferences: SaveSharedPreferences
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("TAG","onCreateView")
        return LayoutInflater.from(activity).inflate(R.layout.setting_fragment,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG","onViewCreated")
        ipaddress=view.findViewById(R.id.ipaddress)
        checkResult=view.findViewById(R.id.check)
        view.findViewById<Button>(R.id.save).setOnClickListener {
            val ipaddress:String=ipaddress.text.toString()

            Retrofit().getClient(ipaddress).create(Responce::class.java).ConnectionControl().enqueue(object : Callback<ConnectionControl>{
                override fun onFailure(call: Call<ConnectionControl>, t: Throwable) {
                    checkResult.text="Connection Waiting"
                    Log.d("TAG FAIL",t.message+" > "+call.request())
                    checkResult.setTextColor(resources.getColor(R.color.colorFail))
                    checkResult.text="Connection Failed"
                }

                override fun onResponse(call: Call<ConnectionControl>, response: Response<ConnectionControl>) {
                    Log.d("TAG OK",""+response.body()+" > "+call.request())
                    connectionResult=response.body()!!.success.toString().toBoolean()
                    if (connectionResult){
                        checkResult.setTextColor(resources.getColor(R.color.colorSuccessfully))
                        checkResult.text="Connection Successfully"
                        saveSharedPreferences.submittedInformation(ipaddress)
                        Handler().postDelayed(
                            {
                                dismiss() //close dialog activity
                            },
                            2000 // value in milliseconds
                        )
                    }
                }

            })

        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        saveSharedPreferences = context as SaveSharedPreferences
    }

}